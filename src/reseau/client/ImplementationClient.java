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
		if (type == 0) {
			if (((Integer)object[0]) == Global.UID) {
				Global.banque = (Integer) object[2];
				Global.boutons.setQuitter();
			}
			Global.jTable.ajoutJoueur((String) object[1], (Integer) object[2], (Integer) object[3]);
			joueurs.put((Integer)object[0],(Integer)object[3]);
		} else if (type == 1) {
			if (((Integer)object[0]) == Global.UID) {
				System.out.println("Au revoir.");
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
	 * 		9 -> Tapis
	 * @throws RemoteException
	 **/
	public void setJoueur (int uid, int[] boutons) throws RemoteException {
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
		
		// Mise à jour de notre banque si c'est nous
		if (uid == Global.UID) {
			switch (type) {
			case 2:
			case 3:
			case 4:
			case 6: Global.banque = Global.banque - montant;
				break;
			}
		} 
	}

	/**
	 * Fin de partie, on donne les vainqueurs
	 * @param vainqueurs HashMap de vainqueurs avec en clef l'uid du joueur
	 * 			et en valeur le montant du gain
	 * @throws RemoteException
	 */
	public void setVainqueurs (HashMap<Integer, Integer> vainqueurs) throws RemoteException {
		Global.jTable.vainqueurs(uidTOposition(vainqueurs));
	}
	
	/**
	 * Fait la corespondance entre les uids des vainqueurs et leur position sur la table
	 * @param vainqueurs HashMap de vainqueurs avec en clef l'uid du joueur
	 * 			et en valeur le montant du gain
	 * @return Liste des positions des vainqueurs
	 */
	private HashMap<Integer, Integer> uidTOposition (HashMap<Integer, Integer> vainqueurs) {
		HashMap<Integer, Integer> positionsV = new HashMap<Integer, Integer>();
		for (Integer uid : vainqueurs.keySet()) {
			positionsV.put(joueurs.get(uid), vainqueurs.get(uid));
		}
		return positionsV;
	}

	/**
	 * Signal du lancement de la partie
	 * @throws RemoteException
	 */
	@Override
	public void start() throws RemoteException {
		nbCartesTable = 0;
		Global.jTable.start();
	}
	
	/**
	 * Initialise les pots de table lorsque l'on rejoind la partie
	 * @param pots Les pots de table
	 * @throws RemoteException
	 */
	public void setPots (int[] pots) throws RemoteException {
		Global.jTable.setPots(pots);
	}
}
