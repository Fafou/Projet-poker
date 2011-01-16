package reseau.serveur;

import graphique.carte.Carte;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.HashSet;

import reseau.interfaces.Client;
import reseau.interfaces.Serveur;

/**
 * Implementation de l'interface serveur
 *@author Fabien
 *@version 0.1
 **/
@SuppressWarnings("serial")
public class ImplementationServeur extends UnicastRemoteObject implements Serveur  {
	/**
	 * Valeur de l'UID du client qui nous est rataché
	 */
	private int notreUID;
	
	/**
	 * Valeur de la banque par défaut
	 */
	private static int BANQUEdeDEPART = 1000;
	
	/**
	 * Valeur de la blende par défaut
	 */
	private static int BLENDE = 5;
	
	/**
	 * Signale si la partie est commencée ou non
	 */
	private boolean commencee;
	
	/**
	 * Joueurs présents sur la table
	 * La clef est l'UID du joueur
	 * L'objet stoqué est le joueur
	 */
	private HashMap<Integer, Joueur> joueurs;
	
	/**
	 * Ensemble de personnes qui doivent recevoir les informations sur une partie.
	 * Cet ensemble contient les joueurs et les spectateurs
	 */
	private HashMap<Integer, Client> spectateurs;
	
	/**
	 * Ensemble de personnes qui veulent rejoindre la table et qui doivent attendre la fin de la donne.
	 */
	private HashMap<Integer, Joueur> attente;
	
	/**
	 * Table de jeux
	 */
	private Table table;
	
	/**
	 * Cartes distribuée lors de cette donne
	 */
	private HashSet<Carte> cartes;
	
	/**
	 * Signale si dans le tour de table quelqu'un a relancé
	 */
	private boolean relance;
	
	/**
	 * Signale que le dealer n'est défini, pour le lancement d'une partie
	 * avec plusieurs client sur le meme poste, il ne peux y en avoir qqu'un qui lance la partie
	 */
	private boolean sansDealer;

	/**
	 * Constructeur de la classe
	 * @throws RemoteException
	 **/
	public ImplementationServeur() throws RemoteException {
		super();
		joueurs = new HashMap<Integer, Joueur>();
		spectateurs = new HashMap<Integer, Client>();
		commencee = false;
		table = new Table();
		attente = new HashMap<Integer, Joueur>();
		cartes = new HashSet<Carte>();
		relance = false;
		//TODO supprimer la ligne de dessous
		relance = (relance && true);
		sansDealer = true;
	}


	/**
	 * Se mettre au tour de la table pour regarder la partie
	 * @param addr Adresse réseau du spectateur
	 * @return UID du spectateur ajouté
	 * @throws RemoteException
	 */
	public int regarder (String addr) throws RemoteException {
		System.out.println("Serveur : regarder");
		// Calcul de l'uid du spectateur
		int UID = (int) (Math.random() * 10000);
		
		// On ce connecte a l'interface client
		Client client;
		try {
			client = (Client) Naming.lookup("rmi://" + addr + "/Client");
			spectateurs.put(UID, client);
			// Ajout des joueurs à la table du nouveau spectateur
			for (Joueur j : joueurs.values()) {
				client.miseAJourTable(0, new Object[]{j.getUID(), j.getPseudo(), j.getBanque(), j.getPosition()});
				if (j.getCartes() != null) {
					client.ajouterCartes(j.getUID(), j.getCartes());
				}
			}
			
			// Si la partie est commancée, on ajoute le pot de table, les cartes de la table
			if (commencee) {
				if (!table.getCartes().equals(null)) {
					client.ajouterCartes(-1, table.getCartes());
				}
				client.setPots(table.getBanque());
				client.setDealer(table.getDealer());
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}

		// On teste si on est sur la même machine que ce client
		// Pour pouvoir sauvegarder l'UID du client qui nous est rataché
		try {
			if (addr.equalsIgnoreCase(InetAddress.getLocalHost().getHostAddress()) && sansDealer) {
				notreUID = UID;
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return UID;
	}
	
	/**
	 * Le spectateur veut devenir joueur
	 * @param uid UID du joueur qui rejoind la partie
	 * @param pseudo Nom du joueur
	 * @param position Position du joueur au tour de la	table
	 * @return Retourne si l'ajout est possible ou non
	 * @throws RemoteException
	 **/
	public boolean rejoindre (int uid, String pseudo, int position) throws RemoteException {
		System.out.println("Serveur : rejoindre");
		if ((joueurs.size() + attente.size()) > 9) {
			// La table est pleine
			return false;
		}
		
		if (commencee) {
			// La donne est commencée, on attend la fin pour ajouter les joueur 
			attente.put(uid, new Joueur(pseudo, position, uid, BANQUEdeDEPART));
		} else {
			// La donne n'a pas commencée, on ajoute le joueur
			joueurs.put(uid, new Joueur(pseudo, position, uid, BANQUEdeDEPART));
			this.ajoutJoueur(uid);
		}
		
		// Si le client est sur la meme machine il doit pouvoir lancer la partie
		// Mais il ne peux y en avoir juste que un, dans le cas ou on lance plusieurs
		// clients sur un meme poste
		if ((uid == notreUID) && sansDealer) {
			sansDealer = false;
			spectateurs.get(uid).setJoueur(uid, new int[] {8});
		}
		return true;
	}
	
	/**
	 * Informe tous les spectateurs et joueurs de l'ajout d'un joueur à la table
	 * @param uid UID du joueur qui vient d'etre ajouté
	 */
	private void ajoutJoueur (int uid) {
		for (Client client : spectateurs.values()) {
			try {
				client.miseAJourTable(0, new Object[]{joueurs.get(uid).getUID(), joueurs.get(uid).getPseudo(), joueurs.get(uid).getBanque(), joueurs.get(uid).getPosition()});
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Quitter la partie
	 * @param uid UID du joueur qui quitte la partie
	 * @throws RemoteException
	 **/
	@Override
	public void quitter(int uid) throws RemoteException {
		System.out.println("Serveur : quitter");
		if (commencee) {
			if (joueurs.containsKey(uid)) {
				joueurs.get(uid).changerStatus();
			} else {
				this.supprimerJoueur(uid);
			}
		} else {
			this.supprimerJoueur(uid);
		}
	}
	
	/**
	 * Supprime un client
	 * @param uid UID du joueur qui quitte la partie
	 */
	private void supprimerJoueur (int uid) {
		if (joueurs.containsKey(uid)) {
			// S'il est au tour de la table, on informe tous le monde qu'il quitte la table
			for (Client client : spectateurs.values()) {
				try {
					client.miseAJourTable(1, new Object[]{uid});
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
			joueurs.remove(uid);
		} else if (attente.containsKey(uid)) {
			attente.remove(uid);
		} 
		spectateurs.remove(uid);
	}
	
	/**
	 * Information sur une action joueur
	 * @param uid UID du joueur qui réalise l'action
	 * @param type type de l'action
	 * 				1 -> check
	 * 				2 -> suivre
	 * 				3 -> relancer
	 * 				4 -> tapis
	 * 				5 -> seCoucher
	 * 				6 -> ouvrir
	 * @param montant Valeur de la mise ou du tapis
	 * @throws RemoteException
	 **/
	@Override
	public void setAction(int uid, int type, int montant) throws RemoteException {
		System.out.println("Serveur : setAction");
	}
	
	/**
	 * Signal du lancement de la partie
	 * @throws RemoteException
	 **/
	@Override
	public void start() throws RemoteException {
		System.out.println("Serveur : start");
		// Si on est tous seul, on peux pas jouer
		if (joueurs.size() > 1) {
			commencee = true;
			table.setEnchere(1);
			table.setNbTour(0);
			relance = false;
			
			// On signale le lancement de la partie
			this.diffuserStart();
			
			// Mise a jour du dealer, petite blende et grosse blende
			if (table.getDealer()[0] == -1) {
				table.setDealer(notreUID);
			}
			this.chercherPGBlende();
			
			// Diffusion du dealer, petite blende et grosse blende sur le réseau
			this.diffuserDealer();
			
			// Distribution et diffusion des cartes au joueurs
			cartes.clear();
			this.donnerCartes();
			
			// La petite blende joue
			this.diffuserAction(table.getDealer()[1], 2, BLENDE);
			
			// La grosse blende joue
			this.diffuserAction(table.getDealer()[2], 2, BLENDE * 2);
			
			// On cherche le joueur suivant pour lui dire de jouer
			int uidSuivant = this.getJSuivant(joueurs.get(table.getDealer()[2]).getPosition() + 1);
			
			// On doit diffuser la valeur pour pouvoir suivre et relancer
			// On doit vérifier qu'il ne sont pas que deux
			if (joueurs.size() == 2) {
				this.diffuserValSR(BLENDE, BLENDE * 3);
				table.setNbTour(1);
			} else {
				this.diffuserValSR(BLENDE * 2, BLENDE * 4);
			}
			
			// On diffuse l'uid du joueur courant et les actions qu'il peux réaliser
			this.diffuserJCourant(uidSuivant, new int[] {1, 2, 3});
		}
	}
	
	/**
	 * Cherche La petite et la grosse blende
	 */
	private void chercherPGBlende () {
		int dealer = table.getDealer()[0];
		if (joueurs.size() == 2) {
			table.setPetiteBlende(dealer);
			table.setGrosseBlende(getJSuivant(joueurs.get(dealer).getPosition() + 1));
		} else {
			table.setPetiteBlende(getJSuivant(joueurs.get(dealer).getPosition() + 1));
			table.setGrosseBlende(getJSuivant(joueurs.get(table.getDealer()[1]).getPosition() + 1));
		}
	}
	
	/**
	 * Cherche le joueur placé en position donnée
	 * @param position Position de référance
	 * @return UID du joueur suivant
	 */
	private int getJSuivant (int position) {
		int indice = position;
		if (indice > 9) {
			indice = 0;
		}
		
		for (Joueur j : joueurs.values()) {
			if (j.getPosition() == indice) {
				return j.getUID();
			}
		}
		
		// Le joueur suivant est peut etre à la position suivante
		return getJSuivant(indice + 1);
	}
	
	/**
	 * Donne une carte à tous les joueurs
	 */
	private void donnerCartes () {
		// On crée les cartes
		int nb = joueurs.size()*2 + 3;
		while (cartes.size() < nb) {
			cartes.add(new Carte((int)(Math.random()*3 + 1), (int)(Math.random()*12 + 1)));
		}
		
		// On Ajoute les cartes au joueurs
		Carte[] c = (Carte[]) cartes.toArray();
		int i = 0;
		for (Joueur j : joueurs.values()) {
			j.setCartes(c[i*2], c[i*2 + 1]);
			i++;
		}
		
		// On ajoute les cartes à la table
		table.setCarte(1, c[i*2]);
		table.setCarte(2, c[i*2 + 1]);
		table.setCarte(3, c[i*2 + 2]);
		
		// On distribue les cartes sur le réseau
		for (Integer key : spectateurs.keySet()) {
			Joueur j = joueurs.get(key);
			if (j == null) {
				for (Joueur joueur : joueurs.values()) {
					try {
						spectateurs.get(key).ajouterCartes(joueur.getUID(), joueur.getCartes());
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
			} else {
				try {
					spectateurs.get(key).ajouterCartes(j.getUID(), j.getCartes());
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Diffusion sur le réseau du dealer, petite blenbe et grosse blende
	 */
	private void diffuserDealer () {
		int[] uids = table.getDealer();
		for (Client client: spectateurs.values()) {
			try {
				client.setDealer(uids);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Diffuse sur le réseau une action réalisée par un joueur
	 * @param uid UID du joueur qui réalise l'action
	 * @param action Action réalisée par le joueur
	 * 				1 -> check
	 * 				2 -> suivre
	 * 				3 -> relancer
	 * 				4 -> tapis
	 * 				5 -> seCoucher
	 * @param montant Valeur de la mise ou du tapis
	 */
	private void diffuserAction (int uid, int action, int montant) {
		for (Client client : spectateurs.values()) {
			try {
				client.setAction(uid, action, montant);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * On diffuse le lancement de la partie
	 */
	private void diffuserStart () {
		for (Client client : spectateurs.values()) {
			try {
				client.start();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * On diffuse la valeur de relance et de suivit
	 * @param suivre Valeur pour suivre
	 * @param relance Valeur pour relancer
	 */
	private void diffuserValSR (int suivre, int relance) {
		for (Client client : spectateurs.values()) {
			try {
				client.setValeurSuivRel(new int[] {suivre, relance});
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * On diffuse le joueur courant et les actions qu'il peux réaliser
	 * @param uid UID du joueur qui doit jouer
	 * @param boutons Actions qu'il peut réaliser
	 */
	private void diffuserJCourant (int uid, int[] boutons) {
		for (Client client : spectateurs.values()) {
			try {
				client.setJoueur(uid, boutons);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
}
