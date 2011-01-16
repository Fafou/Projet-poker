package graphique.carte;


/**
 * Une carte de jeu :
 * couleur : 1-Coeur | 2-Carreau | 3-Trèfle | 4-Pique
 * Valeur : 1-Deux ... 13-As
 * @author Benjamin
 * @version 0.1
 **/
public class Carte implements Comparable<Carte>{
	
	private int couleur;
	private int valeur;
	
	/**
	 * Constructeur
	 * @param int couleur(1-4)
	 * @param int valeur(1-13)
	 */
	public Carte(int couleur, int valeur) {
		this.couleur=couleur;
		this.valeur=valeur;
	}

	
	/**
	 * @param int couleur
	 */
	public void setCouleur(int couleur) {
		this.couleur = couleur;
	}


	/**
	 * @param int valeur
	 */
	public void setValeur(int valeur) {
		this.valeur = valeur;
	}

	/**
	 * @return int couleur
	 */
	public int getCouleur() {
		return couleur;
	}

	/**
	 * @return int valeur
	 */
	public int getValeur() {
		return valeur;
	}


	public int compareTo(Carte o1) {
		if(o1.getValeur()>this.getValeur()) return -1;
		else if(o1.getValeur()<this.getValeur()) return 1;
		else return 0;

	}

	/**
	 * On surcharge l'opperateur equals
	 * @param o Object avec le quel on veux comparer
	 * @return Retourne si les deux object sont égaux
	 */
	public boolean equals(Object obj) {
		if (obj instanceof Carte) {
			Carte c = (Carte) obj;
			if (this.couleur == c.getCouleur()) {
				if (this.valeur == c.getValeur()) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * On surcharge la méthode hashCode
	 * @return Un entier caractérisant la carte
	 */
	public int hashCode () {
		return couleur*100 + valeur;
	}
}

