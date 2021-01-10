package traitementTextes.bibliotheque;

public class Etudiant extends Lecteur {
	
	private String etablissement;
	private int numeroEtudiant;

	public Etudiant(String nom, String prenom) {
		super(nom, prenom);
		// TODO Auto-generated constructor stub
	}
	
	public String getEtablissement() {
		return etablissement;
	}
	public void setEtablissement(String etablissement) {
		this.etablissement = etablissement;
	}
	
	public int getNumeroEtudiant() {
		return numeroEtudiant;
	}
	
	public void setNumeroEtudiant(int numeroEtudiant) {
		this.numeroEtudiant = numeroEtudiant;
	}

	@Override
	public CategorieSocioProfessionelle GetCategorie() {
		return CategorieSocioProfessionelle.Etudiant;
	}
}
