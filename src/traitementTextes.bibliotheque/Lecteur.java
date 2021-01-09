package traitementTextes.bibliotheque;

import java.util.Comparator;

enum CategorieSocioProfessionelle
{
	Travailleur,
	Etudiant
};

public abstract class Lecteur {
	
	private String nom;
	private String prenom;
	private int age;
	private Amende amende;
	private boolean livreEnRetard;

	public Lecteur(String nom, String prenom ) {
		this.nom = nom;
		this.prenom = prenom;
	}
	
	@Override
	public boolean equals(Object lecteur) {
		if (this == lecteur) {
			return true;
		}
		if (lecteur instanceof Lecteur) {
			return this.getNom().equals(((Lecteur) lecteur).getNom());
		}
		return false;

	}
	
	abstract public CategorieSocioProfessionelle GetCategorie();
	
	public String getNom() {
		return nom;
	}
	
	public String getPrenom() {
		return prenom;
	}
	
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	public Amende getAmende() {
		return amende;
	}

	public void setAmende(Amende amende) {
		this.amende = amende;
	}
	
	public boolean aLivreEnRetard() {
		return livreEnRetard;
	}

	public void setLivreEnRetard(boolean livreEnRetard) {
		this.livreEnRetard = livreEnRetard;
	}
	
	public double paieAmende(Amende amende)
	{
		return amende.getAmende();
	}
}

class CompareLecteur implements Comparator<Lecteur>{
	public int compare(Lecteur lecteur1, Lecteur lecteur2) {
		return lecteur1.getNom().compareTo(lecteur2.getNom());
	}
}
