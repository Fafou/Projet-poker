package jeux;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;

import reseau.Global;

/**
 * Adapter pour écouter la fermeture de la fenètre principale
 * @author Fabien
 * @version 0.1
 */
public class AdapterFermeture extends WindowAdapter {
	/**
	 * Lors de la fermeture de la fenètre, on signale au serveur qu'on quitte
	 */
	public void windowClosing (WindowEvent e) {
		try {
			Global.serveur.quitter(Global.UID);
		} catch (RemoteException e1) {
	
		}
		System.exit(0);
	}
}
