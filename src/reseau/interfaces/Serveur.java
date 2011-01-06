package reseau.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface serveur
 *@author Fabien
 *@version 0.1
 **/
public interface Serveur extends Remote{
	/**
	 * Ajouter un joueur
	 * @param pseudo Nom du joueur
	 * @param position Position du joueur au tour de la	table
	 * @return UID du joueur ajouté
	 * @throws RemoteException
	 **/
	int miseAJourTable (String pseudo, int position) throws RemoteException;
	
	/**
	 * Quitter la partie
	 * @param uid UID du joueur qui quitte la partie
	 * @throws RemoteException
	 **/
	void quitter (int uid) throws RemoteException;
	
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
	 * Signal du lancement de la partie
	 * @throws RemoteException
	 **/
	void start () throws RemoteException;
}
