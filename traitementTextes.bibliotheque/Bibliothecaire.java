package traitementTextes.bibliotheque;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Objects;

public class Bibliothecaire {
	
	private int tresorerie = 0;
	private HashMap<Auteur, ArrayList<Livre>> catalogue;
	private HashSet<LivreEmprunte> livresEmpruntes;
	
	public Bibliothecaire(HashMap<Auteur, ArrayList<Livre>> catalogue) {
		this.catalogue=catalogue;
		livresEmpruntes = new HashSet<LivreEmprunte>();
	}
	
	public int getTresorerie() {
		return tresorerie;
	}
	
	public void ajouterLivre(Livre nouveauLivre) {
		if (Objects.nonNull(getCatalogue().get(nouveauLivre.getAuteur()))) {
			getCatalogue().get(nouveauLivre.getAuteur()).add(nouveauLivre);
			tresorerie -= 10;
		} else {
			ArrayList<Livre> livres = new ArrayList<>();
			livres.add(nouveauLivre);
			getCatalogue().put(nouveauLivre.getAuteur(), livres);
			tresorerie -= 10;
		}

	}

	public String listerOeuvresAuteur(Auteur auteur) {
		ArrayList<Livre> livres= catalogue.get(auteur);
		String titreDesLivres="";
		for (Livre livre : livres) {
			titreDesLivres+=livre.getTitre()+ "\n";
		}
		return "L'auteur "+auteur.getNom()+" a ecrit "+livres.size() +" livres:\n"+ titreDesLivres;
	}
	
	public void enleverLivre(Livre ancienLivre) {
		if ((Objects.nonNull(getCatalogue().get(ancienLivre.getAuteur()))) ) {
			getCatalogue().get(ancienLivre.getAuteur()).remove(ancienLivre);
		}
	}
	
	public void preterLivre(Livre livre, Lecteur lecteur) {
		// on vérifie que le livre n'a pas été déjà emprunté 
		if (getLivresEmpruntes().contains(livre.getTitre()))
		{
			// throw exception
		}
		
		// on vérifie que le lecteur a déjà emprunté un livre 
		if (Objects.isNull(retourneLivreEmprunte(lecteur)))
		{
			// throw exception
		}
		
		LivreEmprunte livreEmprunte = buildLivreEmprunte(livre);
		livreEmprunte.setNbJourEmprunt(getNbJourEmprunt());
		livreEmprunte.setLecteur(lecteur);
		livreEmprunte.setDateEmprunt(LocalDate.now());
		getLivresEmpruntes().add(livreEmprunte);
	}
	
	public void RelancerEmprunteurEnRetard() {
		for(LivreEmprunte livreEmprunte : getLivresEmpruntes())
		{
			LocalDate dateEmprunt = livreEmprunte.getDateEmprunt();
			Period p = Period.between(dateEmprunt, LocalDate.now());
			if (p.getDays() >= getNbJourEmprunt())
			{
				Lecteur lecteur = livreEmprunte.getLecteur();
				lecteur.setLivreEnRetard(true);
			}
		}
	}
	
	public ArrayList<Lecteur> ListerPersonnesAyantEmpruntesUnLivre() {
		ArrayList<Lecteur> listeLecteur = new ArrayList<Lecteur>();
		for(LivreEmprunte livreEmprunte : getLivresEmpruntes())
		{
			listeLecteur.add((livreEmprunte.getLecteur()));
		}
			
		return listeLecteur;
	}
	
	
	public ArrayList<Etudiant> ListerLivresEmpruntesParEtudiant() {
		ArrayList<Etudiant> listeEtudiant= new ArrayList<Etudiant>();
		for(LivreEmprunte livreEmprunte : getLivresEmpruntes())
		{
			Lecteur lecteur = livreEmprunte.getLecteur();
			if (lecteur instanceof Etudiant) {
				listeEtudiant.add((Etudiant)lecteur);				
			}
		}
			
		return listeEtudiant;
	}
	
	public ArrayList<Livre> listerLivresEmpruntes() {
        ArrayList<Livre> listeLivres = new ArrayList<Livre>();
        for(LivreEmprunte livreEmprunte: getLivresEmpruntes()) {
            listeLivres.add(livreEmprunte);
        }
        return listeLivres;
    }

	public String listerLivresAnglais() {
        String titresDesLivresAnglais = "";        
        for (Entry<Auteur, ArrayList<Livre>> paire : catalogue.entrySet()) {
            ArrayList<Livre> listeLivres = paire.getValue();
            for (Livre livre: listeLivres) {
                if(livre.getLangue() == "en") {
                    titresDesLivresAnglais += "Titre : " + livre.getTitre() + ", auteur : " + livre.getAuteur().getNom() + "\n";
                    }
            }
        }
        return "Les livres anglais sont: \n" + titresDesLivresAnglais;
    }

	public ArrayList<Livre> listerNbLivresEmpruntesPourUnAuteur(Auteur auteur) {
        ArrayList<Livre> listeLivresPourUnAuteur = new ArrayList<Livre>();
        for(LivreEmprunte livreEmprunte: getLivresEmpruntes()) {
            if (livreEmprunte.getAuteur() == auteur) {
                listeLivresPourUnAuteur.add(livreEmprunte);
            }
        }
        return listeLivresPourUnAuteur;
    }

	public String TrouverLivreSurUnTheme(String theme) {
		String listeLivres="";
		int n = 0;
		for ( Entry<Auteur, ArrayList<Livre>> paire : catalogue.entrySet()) {
            ArrayList<Livre> livres = paire.getValue();
    		for (Livre livre : livres) {
    			if (livre.getTheme() == theme) {
    			listeLivres+=livre.getTitre()+ "\n";
    			n+=1;
    			}
    		}
		}
		return "Il y a "+n+" livres sur le theme \""+ theme +"\". Les livres sont : \n"+ listeLivres;
	}


	public void EnvoyerAmendeRetardaire() {
		for(LivreEmprunte livreEmprunte : getLivresEmpruntes())
		{
			Lecteur lecteur = livreEmprunte.getLecteur();
			if (lecteur.aLivreEnRetard())
			{
				LocalDate dateEmprunt = livreEmprunte.getDateEmprunt();
				Period p = Period.between(dateEmprunt, LocalDate.now());
				Amende amende = new Amende(p.getDays());
				lecteur.setAmende(amende);
			}
		}
	}

	public void EncaisserAmendeRetardaire(Lecteur lecteur) {
		Amende amende = lecteur.getAmende();
		if (Objects.isNull(amende))
		{
			// Throw exception
		}
		
		double amendePayee = lecteur.paieAmende(amende);
		if (amendePayee != amende.getAmende())
		{
			// Throw exception
		}
		
		tresorerie += amendePayee;
		lecteur.setAmende(null);
		lecteur.setLivreEnRetard(false);
	}
	

	public HashMap<Auteur, ArrayList<Livre>> getCatalogue() {
		return catalogue;
	}
	
	public HashSet<LivreEmprunte> getLivresEmpruntes() {
		return livresEmpruntes;
	}
	

	public void initialiserCatalogue(HashMap<Auteur, ArrayList<Livre>> catalogue) {
		this.catalogue = catalogue;
	}
	
	public int getNbJourEmprunt() {
		return 15;
	}
	
	public LivreEmprunte retourneLivreEmprunte(Lecteur lecteur)
	{
		for(LivreEmprunte livreEmprunte : getLivresEmpruntes())
		{
			Lecteur lecteurCourant = livreEmprunte.getLecteur();
			if ( lecteurCourant.equals(lecteur) ) {
				return livreEmprunte;
			}
		}
			
		return null;
	}
		
	public void supprimeTout()
	{
		getCatalogue().clear();
		getLivresEmpruntes().clear();
	}
		
	private LivreEmprunte buildLivreEmprunte( Livre livre) {
		LivreEmprunte livreEmprunte = new LivreEmprunte(livre.getAuteur(), livre.getTitre());
		livreEmprunte.setAnneePublication(livre.getAnneePublication());
		livreEmprunte.setResume(livre.getResume());
		livreEmprunte.setNbTomes(livre.getNbTomes());
		livreEmprunte.setLangue(livre.getLangue());
		livreEmprunte.setTheme(livre.getTheme());
		
		return livreEmprunte;
	}
}
