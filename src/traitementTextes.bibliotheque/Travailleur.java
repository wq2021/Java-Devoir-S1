package traitementTextes.bibliotheque;

public class Travailleur extends Lecteur {

	public Travailleur(String nom, String prenom) {
		super(nom, prenom);
	}

	@Override
	public CategorieSocioProfessionelle GetCategorie() {
		return CategorieSocioProfessionelle.Travailleur;
	}

}
