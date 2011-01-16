package reseau.interfaces;

import graphique.carte.Carte;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface client
 *@author Fabien
 *@version 0.1
 **/
public interface Client extends Remote{
	
	/**
	 * Mise à jour de la table
	 * @param type 	0 -> Ajoute un joueur, object : uid, pseudo, banque, position
	 * 		 		1 -> Effacer un joueur, object : uid
	 * @param object Voir avec le paramètre type
	 * @throws RemoteException
	 **/
	void miseAJourTable (int type, Object[] object) throws RemoteException;
	
	/**
	 * Mise à jour de la position du dealer
	 * @param uids Tableau d'UID des joueurs qui deviennent Dealer, grosse Blende et petite blende
	 * @throws RemoteException
	 **/
	void setDealer (int[] uids) throws RemoteException;
	
	/**
	 * Ajouter des cartes
	 * @param uid UID du joueur, si -1 les cartes sont ajoutées à la table
	 * @param cartes Cartes, soit du joueur soit de la table
	 * @throws RemoteException
	 **/
	void ajouterCartes (int uid, Carte[] cartes) throws RemoteException;
	
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
	void setJoueur (int uid, int[] boutons) throws RemoteException;
	
	/**
	 * Informe des valeurs à dépanser pour suivre, ou relancer
	 * @param valeurs 1 -> Valeur de la somme pour pouvoir suivre
	 * 				  2 -> Valeur de la somme pour relancer
	 * @throws RemoteException
	 */
	void setValeurSuivRel (int[] valeurs) throws RemoteException;
	
	/**
	 * Information sur une action joueur
	 * @param uid UID du joueur qui réalise l'action
	 * @param type type de l'action
	 * 				1 -> check
	 * 				2 -> suivre
	 * 				3 -> relancer
	 * 				4 -> tapis
	 * 				5 -> seCoucher
	 * @param montant Valeur de la mise ou du tapis
	 * @throws RemoteException
	 **/
	void setAction (int uid, int type, int montant) throws RemoteException;
	
	/**
	 * Fin de partie, on donne les vainqueurs
	 * @param pots Valeurs des pots a partager entre les différents vainqueurs
	 * 		Il peut y avoir plusieurs pots si des joueurs ont fait tapis
	 * @param vainqueurs Liste de liste d'UID de joueurs gagnants, une liste par pots
	 * @throws RemoteException
	 */
	void setVainqueurs (int[] pots, int[][] vainqueurs) throws RemoteException;
	
	/**
	 * Signal du lancement de la partie
	 * @throws RemoteException
	 **/
	void start () throws RemoteException;
	
	/**
	 * Initialise les pots de table lorsque l'on rejoind la partie
	 * @param pots Les pots de table
	 * @throws RemoteException
	 */
	void setPots (int[] pots) throws RemoteException;
}
