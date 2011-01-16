package reseau.client;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.ConnectException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import reseau.Global;
import reseau.interfaces.Client;
import reseau.interfaces.Serveur;

/**
 * Ensemble de fonction nécessaires pour ce connecter au serveur
 *@author Fabien
 *@version 0.1
 **/
public class ConnexionAuServeur {
	/**
	 * Adresse IP du serveur
	 */
	private String IPServeur;
	
	/**
	 * Constructeur
	 * @param IPServeur Adresse IP du serveur
	 */
	public ConnexionAuServeur (String IPServeur) {
		this.IPServeur = IPServeur;
	}

	/**
	 * Connexion au serveur
	 * @throws RemoteException 
	 * @throws MalformedURLException 
	 * @throws NotBoundException 
	 * @throws UnknownHostException 
	 */
	public void connexion () throws RemoteException, MalformedURLException, NotBoundException, UnknownHostException {
		// On instancie sont interface client
		Client client = new ImplementationClient();
			
		// On enregistre son interface pour signaler nos services
		try {
			// Si le registre n'est pas crée, une erreur est renvoyée
			// Du coup on le crée
			LocateRegistry.getRegistry().list();
		} catch (ConnectException e) {
			LocateRegistry.createRegistry(1099);
		}
		Naming.rebind("Client", client);
		System.out.println("Interface client enregistrée");
			
		// On récupère l'interface serveur
		Global.serveur = (Serveur) Naming.lookup("rmi://" + IPServeur + "/Serveur");
		System.out.println("Interface serveur trouvée");
			
		// On signale au serveur qu'on regarde la partie
		String IPClient = InetAddress.getLocalHost().getHostAddress();
		Global.UID = Global.serveur.regarder(IPClient);

		System.out.println("Mon adresse IP : " + IPClient);
		System.out.println("Mon UID : " + Global.UID);
	}
}
