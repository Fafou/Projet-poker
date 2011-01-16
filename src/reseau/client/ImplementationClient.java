package reseau.client;

import graphique.carte.Carte;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

import reseau.Global;
import reseau.interfaces.Client;

/**
 * Implémentation de l'interface client
 *@author Fabien
 *@version 0.1
 **/
@SuppressWarnings("serial")
public class ImplementationClient extends UnicastRemoteObject implements Client {
	/**
	 * Ensemble de joueur présent au tour de la table
	 * la clef est L'UID du joueur
	 * la valeur est sa position sur la table
	 */
	private HashMap<Integer, Integer> joueurs;
	
	/**
	 * Nombre de cartes déja présentes sur la table
	 */
	private int nbCartesTable;
	
	/**
	 * Constructeur de la classe
	 * @throws RemoteException 
	 */
	public ImplementationClient() throws RemoteException {
		super();
		joueurs = new HashMap<Integer, Integer>();
	}

	/**
	 * Mise à jour de la table
	 * @param type 	0 -> Ajoute un joueur, object : uid, pseudo, banque, position
	 * 		 		1 -> Effacer un joueur, object : uid
	 * @param object Voir avec le paramètre type
	 * @throws RemoteException
	 */
	@Override
	public void miseAJourTable(int type, Object[] object)throws RemoteException {
		System.out.println("Client : miseAJourTable");
		if (type == 0) {
			if (((Integer)object[0]) == Global.UID) {
				Global.boutons.setQuitter();
			}
			Global.jTable.ajoutJoueur((String) object[1], (Integer) object[2], (Integer) object[3]);
			joueurs.put((Integer)object[0],(Integer)object[3]);
		} else if (type == 1) {
			if (((Integer)object[0]) == Global.UID) {
				//TODO Quitter proprement en fermant les interfaces si nécessaire
				System.exit(0);
			}
			Global.jTable.suppressionJoueur(joueurs.get((Integer)object[0]));
			joueurs.remove((Integer)object[0]);
		} else {
			System.err.println("Erreur ImplementationClient : miseAJourTable () -> type invalide !");
			System.exit(1);
		}
	}

	/**
	 * Mise à jour de la position du dealer, de la grosse blende et de la petite blende
	 * @param uids Tableau d'UID des joueurs qui deviennent Dealer, grosse Blende et petite blende
	 * @throws RemoteException
	 */
	@Override
	public void setDealer(int[] uids) throws RemoteException {
		System.out.println("Client : setDealer");
		switch (uids.length) {
		case 3 : Global.jTable.setPBlend(joueurs.get(uids[2]));
		case 2 : Global.jTable.setGBlend(joueurs.get(uids[1]));
		case 1 : Global.jTable.setDealer(joueurs.get(uids[0]));
			Global.jTable.setJCourant(joueurs.get(uids[0]));
			break;
		default : System.err.println("Erreur ImplementationClient : setClient () -> nb UIDs invalide !");
			System.exit(1);
		}

	}
	
	/**
	 * Ajouter des cartes
	 * @param uid UID du joueur, si -1 les cartes sont ajoutées à la table
	 * @param cartes Cartes, soit du joueur soit de la table
	 * @throws RemoteException
	 */
	@Override
	public void ajouterCartes(int uid, Carte[] cartes) throws RemoteException {
		System.out.println("Client : ajouterCartes");
		switch (uid) {
		case -1 : for (int i = 0; i < cartes.length; i++) {
				Global.jTable.ajoutCarte(cartes[i], nbCartesTable);
				Global.jTable.retournerCarte(0, nbCartesTable);
				nbCartesTable++;
			}
			break;
		default : Global.jTable.ajoutCartesJoueur(joueurs.get(uid), cartes[0], cartes[1]);
			Global.jTable.retournerCarte(1, joueurs.get(uid));
		}
	}

	/**
	 * Informe quel joueur doit jouer
	 * @param uid UID du joueur qui doit jouer
	 * @param boutons Actions que le joueur peut réaliser
	 * 		1 -> Se coucher
	 * 		2 -> Suivre
	 * 		3 -> Relancer
	 * 		4 -> Fin de l'enchère
	 * 		5 -> Parole
	 * 		6 -> Ouvrir
	 * 		7 -> pourVoir
	 * 		8 -> lancer partie
	 * @throws RemoteException
	 **/
	public void setJoueur (int uid, int[] boutons) throws RemoteException {
		System.out.println("Client : setJoueur");
		Global.jTable.setJCourant(joueurs.get(uid));
		
		if (uid == Global.UID) {
			Global.boutons.afficherBoutons(boutons);
		} 
	}
	
	/**
	 * Informe des valeurs à dépanser pour suivre, ou relancer
	 * @param valeurs 1 -> Valeur de la somme pour pouvoir suivre
	 * 				  2 -> Valeur de la somme pour relancer
	 * @throws RemoteException
	 */
	public void setValeurSuivRel (int[] valeurs) throws RemoteException {
		System.out.println("Client : setValeurSuivRel");
		Global.valSuivre = valeurs[0];
		Global.valRelancer = valeurs[1];
		Global.boutons.majMise();
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
	 */
	@Override
	public void setAction(int uid, int type, int montant) throws RemoteException {
		System.out.println("Client : setAction");
		switch (type) {
		case 1 : Global.jTable.checker(joueurs.get(uid));
			break;
		case 2 : 
		case 3 : Global.jTable.miser(joueurs.get(uid), montant);
			break;
		case 4 : Global.jTable.tapis(joueurs.get(uid), montant);
			break;
		case 5 : Global.jTable.seCoucher(joueurs.get(uid));
			break;
		case 6 : Global.jTable.miser(joueurs.get(uid), montant);
			break;
		}
	}

	/**
	 * Fin de partie, on donne les vainqueurs
	 * @param pots Valeurs des pots a partager entre les différents vainqueurs
	 * 		Il peut y avoir plusieurs pots si des joueurs ont fait tapis
	 * @param vainqueurs Liste de liste d'UID de joueurs gagnants, une liste par pots
	 * @throws RemoteException
	 */
	public void setVainqueurs (int[] pots, int[][] vainqueurs) throws RemoteException {
		System.out.println("Client : setVainqueurs");
		Global.jTable.vainqueurs(pots, uidTOposition(vainqueurs));
	}
	
	/**
	 * Fait la corespondance entre les uids des vainqueurs et leur position sur la table
	 * @param vainqueurs Liste des uids des vainqueurs
	 * @return Liste des positions des vainqueurs
	 */
	private int[][] uidTOposition (int[][] vainqueurs) {
		int [][] positionsV = vainqueurs;
		for (int i = 0; i < vainqueurs.length; i++) {
			for (int j = 0; j < vainqueurs[i].length; j++) {
				positionsV[i][j] = joueurs.get(vainqueurs[i][j]);
			}
		}
		return positionsV;
	}

	/**
	 * Signal du lancement de la partie
	 * @throws RemoteException
	 */
	@Override
	public void start() throws RemoteException {
		System.out.println("Client : start");
		nbCartesTable = 0;
		Global.jTable.start();
	}
	
	/**
	 * Initialise les pots de table lorsque l'on rejoind la partie
	 * @param pots Les pots de table
	 * @throws RemoteException
	 */
	public void setPots (int[] pots) throws RemoteException {
		System.out.println("Client : setPots");
		Global.jTable.setPots(pots);
	}
}
