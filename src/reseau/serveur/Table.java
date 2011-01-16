package reseau.serveur;

import graphique.carte.Carte;

/**
 * Table de jeux, contient les cartes et les banques
 * @author Fabien
 * @version 0.1
 */
public class Table {
	/**
	 * Première carte de la table
	 */
	private Carte carte1;

	/**
	 * Seconde carte de la table
	 */
	private Carte carte2;

	/**
	 * Troisième carte de la table
	 */
	private Carte carte3;

	/**
	 * Quatrième carte de la table
	 */
	private Carte carte4;

	/**
	 * Cinquième carte de la table
	 */
	private Carte carte5;
	
	/**
	 * Banque de la table
	 */
	private int[] banque;
	
	/**
	 * UID du dealer de la donne
	 */
	private int dealer;
	
	/**
	 * UID de la petite blende de la donne 
	 */
	private int petiteBlende;

	/**
	 * UID de la grosse blende de la donne
	 */
	private int grosseBlende;
	
	/**
	 * Numéro de l enchère, 
	 * il y en à quatres au total
	 */
	private int enchere;
	
	/**
	 * Nombre de tour de table,
	 * pour une enchère donnée
	 */
	private int nbTour;
	
	/**
	 * Constructeur
	 * Initialise les différentes variables
	 */
	public Table () {
		carte1 = null;
		carte2 = null;
		carte3 = null;
		carte4 = null;
		carte5 = null;
		banque = new int[]{0};
		dealer = -1;
		petiteBlende = -1;
		grosseBlende = -1;
		enchere = 0;
		nbTour = 0;
	}
	
	/**
	 * Retourne les cartes posées sur la table
	 * @return Retourne les cartes, null s'il y en a pas
	 */
	public Carte[] getCartes () {
		if (carte1 == null) {
			return null;
		} else if (carte4 == null) {
			return new Carte[]{carte1, carte2, carte3};
		} else if (carte5 == null) {
			return new Carte[]{carte4};
		} else {
			return new Carte[]{carte5};
		}
	}
	
	/**
	 * Ajouter une carte sur la table
	 * @param indice Numéro de la carte a poser
	 * @param carte Carte à poser
	 */
	public void setCarte (int indice, Carte carte) {
		switch (indice) {
		case 1 :
			carte1 = carte;
			break;
		case 2 :
			carte2 = carte;
			break;
		case 3 :
			carte3 = carte;
			break;
		case 4 :
			carte4 = carte;
			break;
		case 5 :
			carte5 = carte;
			break;
		}
	}
	
	/**
	 * Retourne les pots de table
	 * @return Les pots de table
	 */
	public int[] getBanque () {
		return banque;
	}
	
	/**
	 * Retourne l'uid du dealer, de la petite blende et de la grosse blende
	 * @return Un tableau d'uid
	 */
	public int[] getDealer() {
		if (dealer != -1) {
			return new int[]{dealer, petiteBlende, grosseBlende};
		}
		return null;
	}

	/**
	 * Change le dealer de la table
	 * @param dealer Nouveau dealer de la table
	 */
	public void setDealer(int dealer) {
		this.dealer = dealer;
	}
	
	/**
	 * Change la petite blende de la table
	 * @param petiteBlende Nouvelle petite blende de la table
	 */
	public void setPetiteBlende(int petiteBlende) {
		this.petiteBlende = petiteBlende;
	}
	
	/**
	 * Change la grosse blende de la table
	 * @param grosseBlende Nouvelle grosse blende de la table
	 */
	public void setGrosseBlende(int grosseBlende) {
		this.grosseBlende = grosseBlende;
	}

	/**
	 * Récupérer le numéro de l'anchère en cour
	 * @return Numero de l'anchère
	 */
	public int getEnchere() {
		return enchere;
	}

	/**
	 * On finit l'anchère et on passe a la suivant
	 * @param enchere Nouveau numéro de l'anchère
	 */
	public void setEnchere(int enchere) {
		this.enchere = enchere;
	}

	/**
	 * Nombre de tours effectué lors de l'anchère courante
	 * @return Nombre de tour
	 */
	public int getNbTour() {
		return nbTour;
	}

	/**
	 * On change la valeur du nombre de tour dans l'anchère courante
	 * @param nbTour Nouvelle valeur du nombre de tour
	 */
	public void setNbTour(int nbTour) {
		this.nbTour = nbTour;
	}
}
