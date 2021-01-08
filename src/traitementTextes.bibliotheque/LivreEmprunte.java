package traitementTextes.bibliotheque;

import java.time.LocalDate;

public class LivreEmprunte {
	
	private LocalDate dateEmprunt;
	private int nbJourEmprunt;
	private Lecteur lecteur;
	private Livre livre;
	
	public LivreEmprunte() {
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
