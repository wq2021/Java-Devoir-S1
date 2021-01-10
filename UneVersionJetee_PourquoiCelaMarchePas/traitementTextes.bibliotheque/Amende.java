package traitementTextes.bibliotheque;

public class Amende {
	
	private int nbJourEnRetard;
	private double amende;
	private static final int prixParJour = 1 ;

	public Amende(int nbJourEnRetard) {
		this.nbJourEnRetard = nbJourEnRetard;
		this.amende = calculer(nbJourEnRetard);
	}
	
	public int calculer(int nbJourEnRetard) {
		return prixParJour * nbJourEnRetard;
		}
	
	public double getAmende() {
		return amende;
	}

}
