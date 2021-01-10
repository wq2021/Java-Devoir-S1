package etudiants;

public  class EtudiantTAL {

	private Integer age;
	public String nom;
	protected long numeroEtudiant;
	long numero;
	public final String pays="France";
	public static String ville;
	private Boolean fille;
	private String[] matieres= new String[5];
	
	EtudiantTAL(String nom){
		age=(Integer)44;
		age=Integer.valueOf(44);
		this.nom=nom;
		fille=(Boolean)true;
	}
	
	EtudiantTAL(String[] matieres){
		this.matieres=matieres;
	}
	
	public EtudiantTAL(String nom, long numeroEtudiant){
		this.age=55;
		this.nom=nom;
		this.numeroEtudiant=numeroEtudiant;
	}
	
	EtudiantTAL(String nom, long numeroEtudiant, int age){
		this.nom=nom;
		this.numeroEtudiant=numeroEtudiant;
		this.age=age;
	}
	
	public String toString() {
		return "Methode ToString de la classe EtudiantTAL. \n" + "Nom =" + nom + "\n" + "age =" + age + "\n"
				+ "numeroEtudiant =" + numeroEtudiant + "\n";
	}
	 String imprimerID(String nom, long numeroEtudiant) {
		return "Je m'appelle "+nom+"�, mon num�ro d'�tudiant est "+numeroEtudiant+" ";
	}

	public int getAge() {
		return age -2;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public static String getVille() {
		return ville;
	}

	public static void setVille(String ville) {
		EtudiantTAL.ville = ville;
	}


}
