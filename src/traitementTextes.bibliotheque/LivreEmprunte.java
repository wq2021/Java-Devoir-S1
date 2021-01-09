package traitementTextes.bibliotheque;

import java.time.LocalDate;

public class LivreEmprunte {
	
	private LocalDate dateEmprunt;
	private int nbJourEmprunt;
	private Lecteur lecteur;
	private Livre livre;
	
	public LivreEmprunte() {
	}
	
	@Override
	public boolean equals(Object livreEmprunte) {
		if (this == livreEmprunte) {
			return true;
		}
		if (livreEmprunte instanceof LivreEmprunte) {
			return ((this.getLivre().equals(((LivreEmprunte) livreEmprunte).getLivre())));
		}
		return false;

	}
	
	public int hashCode() {
		return livre.hashCode() + lecteur.hashCode();
	}
	
	public LocalDate getDateEmprunt() {
		return dateEmprunt;
	}

	public void setDateEmprunt(LocalDate dateEmprunt) {
		this.dateEmprunt = dateEmprunt;
	}
	
	public int getNbJourEmprunt() {
		return nbJourEmprunt;
	}

	public void setNbJourEmprunt(int nbJourEmprunt) {
		this.nbJourEmprunt = nbJourEmprunt ;
	}
	
	public Lecteur getLecteur() {
		return lecteur;
	}

	public void setLecteur(Lecteur lecteur) {
		this.lecteur = lecteur;
	}
	
	public Livre getLivre() {
		return livre;
	}

	public void setLivre(Livre livre) {
		this.livre = livre;
	}	
}
