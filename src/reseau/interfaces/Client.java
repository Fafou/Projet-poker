package reseau.interfaces;

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
	 * @param position Position du nouveau dealer
	 * @throws RemoteException
	 **/
	void setDealer (int position) throws RemoteException;
	
	/**
	 * Ajouter des cartes
	 * @param uid UID du joueur, si -1 les cartes sont ajoutées à la table
	 * @param object Cartes, soit du joueur soit de la table
	 * @throws RemoteException
	 **/
	void ajouterCartes (int uid, Object[] object) throws RemoteException;
	
	/**
	 * Informer le joueur courant que c'est a lui de jouer
	 * @param uid UID du joueur qui doit jouer
	 * @throws RemoteException
	 **/
	void setJoueur (int uid) throws RemoteException;
	
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
	 * @param montant Valeur du pot a partager entre les vainqueurs
	 * @param joueurs Liste des uid des joueurs qui doivent se partager le pot de table
	 * @throws RemoteException
	 */
	void setVainqueurs (int montant, int[] joueurs) throws RemoteException;
	
	/**
	 * Signal du lancement de la partie
	 * @throws RemoteException
	 **/
	void start () throws RemoteException;
}
