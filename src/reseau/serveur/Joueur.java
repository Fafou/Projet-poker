package reseau.serveur;

import graphique.carte.Carte;

/**
 * Repr�sente un joueur, avec toutes les informations sur lui
 *@author Fabien
 *@version 0.1
 **/
public class Joueur {
	/**
	 * Nom du joueur
	 */
	private String pseudo;
	
	/**
	 * Position au tour de la table du joueur
	 */
	private int position;
	
	/**
	 * UID du joueur
	 */
	private int uid;
	
	/**
	 * Banque du joueur
	 */
	private int banque;
	
	/**
	 * Premi�re carte du joueur
	 */
	private Carte carte1;
	
	/**
	 * Seconde carte du joueur
	 */
	private Carte carte2;
	
	/**
	 * Status du joueur, s'il d�cide de partir
	 * il faut le faire se coucher quand c'est a lui de jouer 
	 * et le faire quitter � la fin de la donne
	 */
	private boolean present;
	
	/**
	 * Status du joueur, s'il est couch� ou non
	 */
	private boolean status;

	/**
	 * Initialise un joueur
	 * @param pseudo Nom du joueur
	 * @param position Position sur la table du joueur
	 * @param uid UID du joueur
	 * @param banque Banque de d�part du joueur
	 */
	public Joueur (String pseudo, int position, int uid, int banque) {
		this.pseudo = pseudo;
		this.position = position;
		this.uid = uid;
		this.banque = banque;
		present = true;
		status = false;
	}
	
	/**
	 * Retourne la banque du joueur
	 * @return Banque du joueur
	 */
	public int getBanque() {
		return banque;
	}

	/**
	 * Modifie la banque du joueur
	 * @param banque Nouvelle valeur de la banque
	 */
	public void setBanque(int banque) {
		this.banque = banque;
	}
	/**
	 * Retourne les cartes du joueurs ou null s'il les a pas encorre
	 * @return Banque du joueur
	 */
	public Carte[] getCartes () {
		if (carte1 == null) {
			return null;
		}
		
		return new Carte[]{carte1, carte2};
	}

	/**
	 * Modifie les cartes du joueur
	 * @param c1 Premi�re carte du joueur
	 * @param c2 Seconde carte du joueur
 	 */
	public void setCartes (Carte c1, Carte c2) {
		this.carte1 = c1;
		this.carte2 = c2;
	}
	
	/**
	 * Retourne le pseudo du joueur
	 */
	public String getPseudo () {
		return pseudo;
	}
	
	/**
	 * Retourne la position du joueur sur la table
	 */
	public int getPosition () {
		return position;
	}
	
	/**
	 * Retourne l'UID du joueur
	 */
	public int getUID () {
		return uid;
	}
	
	/**
	 * Retourne le status du joueur, s'il a quitt� ou non
	 * @return Status du joueur
	 */
	public boolean isPresent () {
		return present;
	}
	
	/**
	 * Change le status du joueur, car il a quitter
	 */
	public void changerStatus () {
		present = false;
	}

	/**
	 * Change le status du joueur
	 * @param status Le nouveaux status
	 */
	public void setCouche(boolean status) {
		this.status = status;
	}
	
	/**
	 * R�cup�re le status du joueur
	 * @return
	 */
	public boolean isCouche () {
		return status;
	}
}
