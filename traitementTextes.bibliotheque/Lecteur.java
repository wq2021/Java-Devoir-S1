package traitementTextes.bibliotheque;

public class Lecteur {
	
	private String nom;
	private String prenom;
	private int age;
	private Amende amende;
	private boolean livreEnRetard;

	public Lecteur(String nom, String prenom ) {
		this.nom = nom;
		this.prenom = prenom;
	}
	
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
