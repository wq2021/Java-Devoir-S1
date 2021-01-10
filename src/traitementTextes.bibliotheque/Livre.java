package traitementTextes.bibliotheque;

import java.io.Serializable;
import java.util.Comparator;
import java.util.function.BooleanSupplier;

public class Livre implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Auteur auteur;
	private String titre;
	private String resume;
	private int anneePublication;
	private int nbTomes;
	private String langue;
	private String theme;
	
	public Livre(Auteur auteur,String titre) {
		this.auteur=auteur;
		this.titre=titre;
	}
	
	@Override
	public boolean equals(Object livre) {
		if (this == livre) {
			return true;
		}
		if (livre instanceof Livre) {
			return ((this.getAuteur().equals(((Livre) livre).getAuteur()))
					&& (this.titre.equals(((Livre) livre).getTitre())));
		}
		return false;

	}
	
	public int hashCode() {
		return titre.hashCode();
	}
	
	public Auteur getAuteur() {
		return auteur;
	}

	public int getAnneePublication() {
		return anneePublication;
	}

	public void setAnneePublication(int anneePublication) {
		this.anneePublication = anneePublication;
	}

	public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public int getNbTomes() {
		return nbTomes;
	}

	public void setNbTomes(int nbTomes) {
		this.nbTomes = nbTomes;
	}
	
	public String getLangue() {
		return langue;
	}

	public void setLangue(String langue) {
		this.langue = langue;
	}
	
	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}
}

class CompareLivre implements Comparator<Livre>{
	public int compare(Livre livre1, Livre livre2) {
		return livre1.getTitre().compareTo(livre2.getTitre());
	}
}