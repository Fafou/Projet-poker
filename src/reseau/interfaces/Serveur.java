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
	 * Se mettre au tour de la table pour regarder la partie
	 * @param addr Adresse réseau du spectateur
	 * @return UID du spectateur ajouté
	 * @throws RemoteException
	 */
	int regarder (String addr) throws RemoteException;
	
	/**
	 * Le spectateur veut devenir joueur
	 * @param uid UID du joueur qui rejoind la partie
	 * @param pseudo Nom du joueur
	 * @param position Position du joueur au tour de la	table
	 * @return Retourne si l'ajout est possible ou non
	 * @throws RemoteException
	 **/
	boolean rejoindre (int uid, String pseudo, int position) throws RemoteException;
	
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
	 * 				6 -> ouvrir
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
//TODO Gérer lors de l'ajout si le joueur est à la fois dealer et petite blende
//TODO Gérer le changement de serveur quand la personne qui part est le serveur
