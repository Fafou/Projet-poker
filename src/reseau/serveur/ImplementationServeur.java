package reseau.serveur;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import reseau.interfaces.Serveur;

/**
 * Implementation de l'interface serveur
 *@author Fabien
 *@version 0.1
 **/
@SuppressWarnings("serial")
public class ImplementationServeur extends UnicastRemoteObject implements Serveur  {

	/**
	 * Constructeur de la classe
	 * @throws RemoteException
	 **/
	protected ImplementationServeur() throws RemoteException {
		super();
	}

	/**
	 * Ajouter un joueur
	 * @param pseudo Nom du joueur
	 * @param position Position du joueur au tour de la	table
	 * @return UID du joueur ajouté
	 * @throws RemoteException
	 **/
	@Override
	public int miseAJourTable(String pseudo, int position) throws RemoteException {
		return 0;
	}
	
	/**
	 * Quitter la partie
	 * @param uid UID du joueur qui quitte la partie
	 * @throws RemoteException
	 **/
	@Override
	public void quitter(int uid) throws RemoteException {
		
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
	 **/
	@Override
	public void setAction(int uid, int type, int montant) throws RemoteException {
		
	}
	
	/**
	 * Signal du lancement de la partie
	 * @throws RemoteException
	 **/
	@Override
	public void start() throws RemoteException {
		
	}
}
