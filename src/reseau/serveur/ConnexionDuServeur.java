package reseau.serveur;

import java.net.MalformedURLException;
import java.rmi.ConnectException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import reseau.interfaces.Serveur;

/**
 * Ensemble de fonction pour diffuser l'intarface serveur
 *@author Fabien
 *@version 0.1
 **/
public class ConnexionDuServeur {	
	/**
	 * Enregistrement de l'interface serveur pour que les clients puissent y acc�der
	 */
	public void sEnregister () {
		try {
			// On instancie sont interface serveur
			Serveur serveur = new ImplementationServeur();
			
			// On enregistre son interface pour signaler nos services
			try {
				// Si le registre n'est pas cr�e, une erreur est renvoy�e
				// Du coup on le cr�e
				LocateRegistry.getRegistry().list();
			} catch (ConnectException e) {
				LocateRegistry.createRegistry(1099);
			}
			Naming.rebind("Serveur", serveur);
			System.out.println("Interface serveur enregistr�e");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
}
