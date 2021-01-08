package traitementTextes.bibliotheque;

import java.time.LocalDate;

public class LivreEmprunte extends Livre {
	
	private LocalDate dateEmprunt;
	private int nbJourEmprunt;
	private Lecteur lecteur;
	
	public LivreEmprunte(Auteur auteur, String titre) {
		super(auteur, titre);
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
}
