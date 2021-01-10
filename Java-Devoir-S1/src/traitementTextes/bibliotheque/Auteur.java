package traitementTextes.bibliotheque;

/**
* 
* @author Sandrine Cariteau
*/

import java.io.Serializable;

public class Auteur implements Serializable {

	
	private static final long serialVersionUID = 1L;
	private String nom;
	
	/**
	 * Constructeur de la classe Auteur
	 * @param nom
	 */
	public Auteur(String nom) {
		this.setNom(nom);
	}
	
	/**
	 * Méthode equals pour les objets de la classe Auteur. 
	 * Elle vérifie que les noms de deux objets de la classe Auteur sont égaux.
	 * @param anObject
	 */
	@Override
	public boolean equals(Object anObject) {
		if (this == anObject) {
			return true;
		}
		if (anObject instanceof Auteur) {
			return nom.equals(((Auteur) anObject).getNom());
		}
		return false;

	}
	
	/**
	 * 
	 */
	public int hashCode() {
		return nom.hashCode();
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

}
