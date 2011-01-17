package reseau;

import reseau.interfaces.Serveur;
import graphique.boutons.JBoutons;
import graphique.table.JTable;

/**
 * Ensemble d'objet qui doivent etre accessible de plusieurs endroits
 *@author Fabien
 *@version 0.1
 **/
public class Global {
	/**
	 * Panel représentant la table de jeux
	 */
	public static JTable jTable;
	
	/**
	 * Panel représentant les boutons des actions possibles
	 */
	public static JBoutons boutons;

	/**
	 * Valeur de mon UID
	 */
	public static int UID;
	
	/**
	 * Interface du serveur
	 */
	public static Serveur serveur;
	
	/**
	 * Valeur necessaire pour pouvoir suivre
	 */
	public static int valSuivre;
	
	/**
	 * Valeur necessaire pour pouvoir relancer
	 */
	public static int valRelancer;
	
	/**
	 * Adresse IP du serveur
	 */
	public static String IPServeur;
	
	/**
	 * Signale si on est le serveur ou non
	 */
	public static boolean isServeur;
	
	/**
	 * Nom du joueur
	 */
	public static String pseudo;
	
	/**
	 * Signale si on joue ou si on regarde
	 */
	public static boolean isGaming;
	
	/**
	 * Valeur de notre banque
	 */
	public static int banque;
}
