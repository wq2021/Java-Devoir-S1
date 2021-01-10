package traitementTextes.bibliotheque;

import java.time.LocalDate;
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

	public ArrayList<Livre> listerOeuvresAuteur(Auteur auteur) {
		return catalogue.get(auteur);
	}
	
	public void enleverLivre(Livre ancienLivre) {
		if ((Objects.nonNull(getCatalogue().get(ancienLivre.getAuteur()))) ) {
			getCatalogue().get(ancienLivre.getAuteur()).remove(ancienLivre);
		}
	}
	
	public void preterLivre(Livre livre, Lecteur lecteur) throws Exception {
		BibliothecaireVerificateur.VerifierSiLivreExisteDansCatalogue(getCatalogue(),livre);
		LivreEmprunte livreEmprunteParLecteur = retourneLivreEmprunte(lecteur);
		BibliothecaireVerificateur.VerifierSiLecteurADejaEmprunteLivre(lecteur, livreEmprunteParLecteur);
		BibliothecaireVerificateur.VerifierSiLivreDejaEmprunte(getLivresEmpruntes(), livre);
		
		LivreEmprunte livreEmprunte = new LivreEmprunte();
		livreEmprunte.setNbJourEmprunt(getNbJourEmprunt());
		livreEmprunte.setLecteur(lecteur);
		livreEmprunte.setDateEmprunt(LocalDate.now());
		livreEmprunte.setLivre(livre);
		getLivresEmpruntes().add(livreEmprunte);
	}
	
	public void RelancerEmprunteurEnRetard() {
		for(LivreEmprunte livreEmprunte : getLivresEmpruntes()){
			LocalDate dateEmprunt = livreEmprunte.getDateEmprunt();
			Period p = Period.between(dateEmprunt, LocalDate.now());
			if (p.getDays() >= getNbJourEmprunt()){
				Lecteur lecteur = livreEmprunte.getLecteur();
				lecteur.setLivreEnRetard(true);
			}
		}
	}
	
	public ArrayList<Lecteur> ListerPersonnesAyantEmpruntesUnLivre() {
		ArrayList<Lecteur> listeLecteur = new ArrayList<Lecteur>();
		for(LivreEmprunte livreEmprunte : getLivresEmpruntes()){
			listeLecteur.add((livreEmprunte.getLecteur()));
		}
			
		return listeLecteur;
	}
	
	public ArrayList<Livre> ListerLivresEmpruntesParEtudiant() {
        ArrayList<Livre> listeLivres = new ArrayList<Livre>();
        for(LivreEmprunte livreEmprunte : getLivresEmpruntes()){
            Lecteur lecteur = livreEmprunte.getLecteur();
            if (lecteur.GetCategorie() == CategorieSocioProfessionelle.Etudiant) {
                listeLivres.add(livreEmprunte.getLivre());
            }
        }
        return listeLivres;
    }
	
	public ArrayList<Livre> listerLivresEmpruntes() {
        ArrayList<Livre> listeLivres = new ArrayList<Livre>();
        for(LivreEmprunte livreEmprunte: getLivresEmpruntes()) {
        	listeLivres.add(livreEmprunte.getLivre());
        }
        return listeLivres;
    }

	public ArrayList<Livre>  listerLivresAnglais() {
		ArrayList<Livre> titresDesLivresAnglais = new ArrayList<Livre>();        
        for (Entry<Auteur, ArrayList<Livre>> paire : catalogue.entrySet()) {
            for (Livre livre: paire.getValue()) {
                if(livre.getLangue() == "en") {
                	titresDesLivresAnglais.add(livre);
                }
            }
        }
        return titresDesLivresAnglais;
    }

	public int listerNbLivresEmpruntesPourUnAuteur(Auteur auteur) {
        int n=0;
        for(LivreEmprunte livreEmprunte: getLivresEmpruntes()) {
        	Livre livre = livreEmprunte.getLivre();
        	if (livre.getAuteur().equals(auteur)) {
                n++;
            }
        }
        return n;
    }

	public ArrayList<Livre> TrouverLivreSurUnTheme(String theme) {
		ArrayList<Livre> listeLivres=new ArrayList<Livre>();
		for ( Entry<Auteur, ArrayList<Livre>> paire : catalogue.entrySet()) {
    		for (Livre livre : paire.getValue()) {
    			if (livre.getTheme() == theme) {
    				listeLivres.add(livre);
    			}
    		}
		}
		return listeLivres;
	}

	public void EnvoyerAmendeRetardaire() throws Exception {
		for(LivreEmprunte livreEmprunte : getLivresEmpruntes())
		{
			Lecteur lecteur = livreEmprunte.getLecteur();
			BibliothecaireVerificateur.VerifierLecteurARetard(lecteur);
								
			LocalDate dateEmprunt = livreEmprunte.getDateEmprunt();
			Period p = Period.between(dateEmprunt, LocalDate.now());
			Amende amende = new Amende(p.getDays());
			lecteur.setAmende(amende);
		}
	}

	public void EncaisserAmendeRetardaire(Lecteur lecteur) throws Exception {
		BibliothecaireVerificateur.VerifierLecteurAAmende(lecteur);
		Amende amende = lecteur.getAmende(); 
		double amendePayee = lecteur.paieAmende(amende);
		BibliothecaireVerificateur.VerifierLecteurAPayeBonMontantAmende(lecteur, amende, amendePayee);

		tresorerie += amendePayee;
		lecteur.setAmende(null);
		lecteur.setLivreEnRetard(false);
	}

	private HashMap<Auteur, ArrayList<Livre>> getCatalogue() {
		return catalogue;
	}
	
	private HashSet<LivreEmprunte> getLivresEmpruntes() {
		return livresEmpruntes;
	}	

	public void initialiserCatalogue(HashMap<Auteur, ArrayList<Livre>> catalogue) {
		this.catalogue = catalogue;
	}
	
	public int getNbJourEmprunt() {
		return 15;
	}
	
	public LivreEmprunte retourneLivreEmprunte(Lecteur lecteur){
		for(LivreEmprunte livreEmprunte : getLivresEmpruntes()){
			Lecteur lecteurCourant = livreEmprunte.getLecteur();
			if ( lecteurCourant.equals(lecteur) ) {
				return livreEmprunte;
			}
		}		
		return null;
	}
	
	//pour initialiser le catalogue et tout tester individuellement
	public void supprimeTout() {
		getCatalogue().clear();
		getLivresEmpruntes().clear();
	}
}
