package reseau.client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import reseau.interfaces.Client;

/**
 * Implémentation de l'interface client
 *@author Fabien
 *@version 0.1
 **/
@SuppressWarnings("serial")
public class ImplementationClient extends UnicastRemoteObject implements Client {
	
	/**
	 * Constructeur de la classe
	 * @throws RemoteException
	 **/
	protected ImplementationClient() throws RemoteException {
		super();
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
		
	}

	/**
	 * Mise à jour de la position du dealer
	 * @param position Position du nouveau dealer
	 * @throws RemoteException
	 */
	@Override
	public void setDealer(int position) throws RemoteException {
		
	}

	/**
	 * Ajouter des cartes
	 * @param uid UID du joueur, si -1 les cartes sont ajoutées à la table
	 * @param object Cartes, soit du joueur soit de la table
	 * @throws RemoteException
	 */
	@Override
	public void ajouterCartes(int uid, Object[] object) throws RemoteException {
		
	}

	/**
	 * Informer le joueur courant que c'est a lui de jouer
	 * @param uid UID du joueur qui doit jouer
	 * @throws RemoteException
	 */
	@Override
	public void setJoueur(int uid) throws RemoteException {
		
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
	 * @param montant Valeur de la mise ou du tapis
	 * @throws RemoteException
	 */
	@Override
	public void setAction(int uid, int type, int montant) throws RemoteException {
		
	}

	/**
	 * Fin de partie, on donne les vainqueurs
	 * @param montant Valeur du pot a partager entre les vainqueurs
	 * @param joueurs Liste des uid des joueurs qui doivent se partager le pot de table
	 * @throws RemoteException
	 */
	@Override
	public void setVainqueurs(int montant, int[] joueurs) throws RemoteException {
		
	}

	/**
	 * Signal du lancement de la partie
	 * @throws RemoteException
	 */
	@Override
	public void start() throws RemoteException {
		
	}
}
