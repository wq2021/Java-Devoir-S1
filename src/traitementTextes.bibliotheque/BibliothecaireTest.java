package traitementTextes.bibliotheque;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BibliothecaireTest {
	
	private Bibliothecaire bibliothecaire;

	@BeforeEach
	void setUp() throws Exception {
		
		HashMap<Auteur, ArrayList<Livre>> catalogue = new HashMap<>();		
		bibliothecaire=new Bibliothecaire(catalogue);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	
	@Test
	void testAfficherOeuvresAuteur() {
		//GIVEN
		bibliothecaire.supprimeTout();
		Livre livre = AjouteLivreAuCatalogue("nomAuteur", "Un titre présomptueux");
		
		//WHEN
		String listeOeuvres = bibliothecaire.listerOeuvresAuteur(livre.getAuteur());
		
		//THEN
		assertNotNull(listeOeuvres);
		assertTrue(listeOeuvres.contains(livre.getTitre()));
		System.out.println(listeOeuvres);
	}
	
	
	@Test
	void testAjouterLivre() {
		//GIVEN
		bibliothecaire.supprimeTout();
		Livre nouveauLivre = AjouteLivreAuCatalogue("nomAuteur", "Un titre");
		int ancienMontant = bibliothecaire.getTresorerie();
		
		//WHEN
		bibliothecaire.ajouterLivre(nouveauLivre);
		
		//THEN
		assertNotNull(nouveauLivre.getAuteur());
		ArrayList<Livre> livresDidier = bibliothecaire.getCatalogue().get(auteur);
		assertNotNull(livresDidier);
		assertTrue(livresDidier.contains(nouveauLivre));
		assertTrue(bibliothecaire.getTresorerie() < ancienMontant);
	}

	@Test
	void testEnleverLivre() {
		//GIVEN
		bibliothecaire.supprimeTout();
		Livre ancienLivre = AjouteLivreAuCatalogue("nomAuteur", "Un titre");
		
		//WHEN
		bibliothecaire.enleverLivre(ancienLivre);
		
		//THEN
		assertFalse(bibliothecaire.getCatalogue().get(ancienLivre.getAuteur()).contains(ancienLivre));
		assertEquals(bibliothecaire.getCatalogue().get(ancienLivre.getAuteur()).size(), 0);
	}

	/*
	 * 
	 * Partie concernee par le devoir
	 * Voici le d�compte des notes:
	 * 1pts par test OK==>10pts
	 * 2 pour pour la mis en place de l'heritage
	 * 1pt pour la javadoc
	 * 1pt pour le polymorphisme et la surchage
	 * 1pt pour la reutilisation de l'existant
	 * 1pt pour la gestion des exceptions
	 * 1pt pour la creation d'exceptions
	 * 1pt: utilisation de l'encapsulation
	 * 1pt: utilisation de git
	 * 1pt: lisibilit� du code
	 * -1pt: m�thode avec plus de 3 arguments
	 * -1pt: classe de plus de 200 lignes
	 * -1pt: plus de 2 boucles for
	 * -1pt: trop d'utilisation de if
	 * 
	 */
	
	@Test
	void testPreterUnLivre() {
		//GIVEN	
		bibliothecaire.supprimeTout();
		Lecteur lecteur = new Travailleur("prenom", "nom");
		Lecteur lecteur2 = new Travailleur("prenom2", "nom2");
		Livre ancienLivre = AjouteLivreAuCatalogue("nomAuteur", "Un titre");
		Livre ancienLivre2 = AjouteLivreAuCatalogue("nomAuteur2", "Un titre2");
		Livre ancienLivre3 = AjouteLivreAuCatalogue("nomAuteur3", "Un titre3");
		Livre ancienLivre4 = creerLivre("nomAuteur4", "Un titre4");

		// WHEN
		boolean exceptionLeve = preterLivreTest(ancienLivre, lecteur);
		
		//THEN
		assertFalse(exceptionLeve);
		LivreEmprunte livreEmprunte =  bibliothecaire.retourneLivreEmprunte(lecteur);
		assertTrue(Objects.nonNull(livreEmprunte));
		Livre livreBiblio = livreEmprunte.getLivre();
		assertEquals(livreBiblio,ancienLivre);
		assertEquals(livreBiblio.getAuteur(), "nomAuteur");
		assertEquals(livreEmprunte.getDateEmprunt(),LocalDate.now());
		assertEquals(livreEmprunte.getNbJourEmprunt(), bibliothecaire.getNbJourEmprunt());
		assertEquals(livreEmprunte.getLecteur(),lecteur);
			
		// test si le livre a déjà été emprunté
		// WHEN 
		boolean exceptionLeveLivreDejaEmp = preterLivreTest(ancienLivre, lecteur2);
		// THEN	
		assertTrue(exceptionLeveLivreDejaEmp);
		
		// test si le lecteur a déjà emprunté un livre 
		//WHEN
		boolean exceptionLeveLecteurDejaEmpr = preterLivreTest(ancienLivre2, lecteur);
		//THEN
		assertTrue(exceptionLeveLecteurDejaEmpr);
		
		// test si le livre existe bien dans le catalogue
		//WHEN
		boolean exceptionLeveLivreDansCat = preterLivreTest(ancienLivre4, lecteur2) ;
		//THEN
		assertTrue(exceptionLeveLivreDansCat);
	
	}
	
	@Test
	void testRelancerEmprunteurEnRetard() {
		// premier scenario : pas de retard
		//GIVEN
		bibliothecaire.supprimeTout();
		Livre livre = AjouteLivreAuCatalogue("nomAuteur", "Un titre");
		Lecteur lecteur = new Travailleur("prenom", "nom");		
		boolean exceptionLeve = preterLivreTest(livre, lecteur);

		//WHEN		
		bibliothecaire.RelancerEmprunteurEnRetard();
		
		//THEN
		assertFalse(exceptionLeve);
		assertFalse(lecteur.aLivreEnRetard());
		
		// deuxième scenario : retard	
		//GIVEN
		LivreEmprunte livreEmprunte = bibliothecaire.retourneLivreEmprunte(lecteur);
		LocalDate localDate = livreEmprunte.getDateEmprunt();
		localDate= localDate.minusDays(16);
		livreEmprunte.setDateEmprunt(localDate);
		
		//WHEN		
		bibliothecaire.RelancerEmprunteurEnRetard();
		
		//THEN
		assertTrue(lecteur.aLivreEnRetard());		
	}
	
	@Test
	void testListerPersonnesAyantEmpruntesUnLivre() {
		//GIVEN
		bibliothecaire.supprimeTout();
		Livre premierLivre = AjouteLivreAuCatalogue("nomAuteur", "Un titre présomptueux");
		Livre deuxiemeLivre = AjouteLivreAuCatalogue("nomAuteur2", "Un titre présomptueux2");
		
		Lecteur lecteur = new Travailleur("nom", "prenom");
		Lecteur lecteur2 = new Travailleur("nom", "prenom");
		ArrayList<Lecteur> listeLecteurs = new ArrayList<Lecteur>();
		listeLecteurs.add(lecteur);
		listeLecteurs.add(lecteur2);
		boolean exceptionLevee1 = preterLivreTest(premierLivre, lecteur);
		boolean exceptionLevee2 = preterLivreTest(deuxiemeLivre, lecteur2);
		
		//WHEN
		ArrayList<Lecteur> listeLecteurRetour = bibliothecaire.ListerPersonnesAyantEmpruntesUnLivre();
		
		//THEN
		assertFalse(exceptionLevee1);
		assertFalse(exceptionLevee2);
		assertNotNull(listeLecteurs);
		Collections.sort(listeLecteurs, new CompareLecteur());
		Collections.sort(listeLecteurRetour, new CompareLecteur());
		assertEquals(listeLecteurs,listeLecteurRetour);
		System.out.println(listeLecteurs);
	}
	
	@Test
	void testListerLivresEmpruntesParEtudiant() {
		//GIVEN
		bibliothecaire.supprimeTout();
		Livre premierLivre = AjouteLivreAuCatalogue("nomAuteur", "Un titre présomptueux");
		Livre deuxiemeLivre = AjouteLivreAuCatalogue("nomAuteur2", "Un titre présomptueux2");
		Livre troisiemeLivre = AjouteLivreAuCatalogue("nomAuteur3", "Un titre présomptueux3");
		Etudiant etudiant = new Etudiant("nom", "prenom");
		Etudiant etudiant2 = new Etudiant("nom", "prenom");
		Lecteur lecteur = new Travailleur("nom", "prenom");
		ArrayList<Etudiant> listeLecteursEtudiants = new ArrayList<Etudiant>();
		listeLecteursEtudiants.add(etudiant);
		listeLecteursEtudiants.add(etudiant2);
		boolean exceptionLevee1 = preterLivreTest(premierLivre, etudiant);
		boolean exceptionLevee2 = preterLivreTest(deuxiemeLivre, lecteur);
		boolean exceptionLevee3 = preterLivreTest(troisiemeLivre, etudiant2);
		
		//WHEN
		ArrayList<Etudiant> listeEtudiantRetour = bibliothecaire.ListerLivresEmpruntesParEtudiant();
		
		//THEN
		assertFalse(exceptionLevee1);
		assertFalse(exceptionLevee2);
		assertFalse(exceptionLevee3);
		assertNotNull(listeEtudiantRetour);
		Collections.sort(listeLecteursEtudiants, new CompareLecteur());
		Collections.sort(listeEtudiantRetour, new CompareLecteur());
		assertEquals(listeLecteursEtudiants,listeEtudiantRetour);
		System.out.println(listeLecteursEtudiants);
}
	
	@Test
    void testListerLivresEmpruntes() {
        //GIVEN
		//!! pour tester il faut preter les livres mais d abord les ajouter dans le catalogue
        // est-ce que le given n'est plus utile si on utilise directement getLivresEmpruntes()?
        bibliothecaire.supprimeTout();
		Livre livreUn = AjouteLivreAuCatalogue("auteurA", "titreA");
		Livre livreDeux = AjouteLivreAuCatalogue("auteurB", "titreB");

        //WHEN
        ArrayList<Livre> listeLivres = bibliothecaire.listerLivresEmpruntes();

        //THEN
        //J'ai mis "assetFalse" au debut, mais apres je m'apercois qu'il n'y a pas de livres dans getLivresEmpruntes()
        assertTrue(listeLivres.isEmpty());
        assertNotNull(listeLivres);
        System.out.println(listeLivres);

    }
	
	@Test
    void testListerLivresAnglais() {

        //GIVEN
		bibliothecaire.supprimeTout();
		Livre livreA = AjouteLivreAuCatalogue("James Joyce", "Ulysses");
        livreA.setLangue("en");
        Livre livreA1 = AjouteLivreAuCatalogue("James Joyce", "Un livre non anglais de Joyce");
        livreA1.setLangue("es");
        Livre livreB = AjouteLivreAuCatalogue("Gustav Flaubert", "Madame Bovary");
        livreB.setLangue("fr");
        Livre livreC = AjouteLivreAuCatalogue("George Orwell", "Animal Farm");
        livreC.setLangue("en");
        ArrayList<Livre> listeLivreAnglaisRef = new ArrayList<Livre>();
        listeLivreAnglaisRef.add(livreA);
        listeLivreAnglaisRef.add(livreC);

        //WHEN
       	ArrayList<Livre> listeLivreAnglais = bibliothecaire.listerLivresAnglais();
 
        //THEN
       	assertNotNull(listeLivreAnglais);
		Collections.sort(listeLivreAnglaisRef, new CompareLivre());
		Collections.sort(listeLivreAnglais, new CompareLivre());
		assertEquals(listeLivreAnglaisRef, listeLivreAnglais);
		System.out.println(listeLivreAnglais);
    }
	
	@Test
    void testListerNbLivresEmpruntesPourUnAuteur() {
        //GIVEN
		bibliothecaire.supprimeTout();
		Livre livre = AjouteLivreAuCatalogue("Léon Tolsloï", "Anna Karénine");
		Livre livre2 = AjouteLivreAuCatalogue("Léon Tolsloï", "Guerre et Paix");
		//Livre livre3 = AjouteLivreAuCatalogue("Victor Hugo", "Les Misérables");
		Auteur auteurAchercher = new Auteur("Léon Tolsloï");
		ArrayList<Livre> listeLivresTolstoi = new ArrayList<Livre>();
		listeLivresTolstoi.add(livre);
		listeLivresTolstoi.add(livre2);
		
        //WHEN
        ArrayList<Livre> listeLivresPourUnAuteur = bibliothecaire.listerNbLivresEmpruntesPourUnAuteur(auteurAchercher);

        //THEN
        assertNotNull(listeLivresPourUnAuteur);
		Collections.sort(listeLivresPourUnAuteur, new CompareLivre());
		Collections.sort(listeLivresTolstoi, new CompareLivre());
		assertEquals(listeLivresPourUnAuteur, listeLivresTolstoi);
		System.out.println(listeLivresPourUnAuteur);
    }
	
	@Test
	void testTrouverLivreSurUnTheme() {
		//GIVEN	
		bibliothecaire.supprimeTout();
		String theme = "amour";
		Livre livreA = AjouteLivreAuCatalogue("Alexandre Pouchkine", "La fille du capitaine");
        livreA.setTheme(theme);
        Livre livreA1 = AjouteLivreAuCatalogue("BlahBlah", "Apprendre à coder en JAVA");
        livreA1.setLangue("programmation");
        Livre livreB = AjouteLivreAuCatalogue("Gustav Flaubert", "Madame Bovary");
        livreB.setTheme(theme);
        Livre livreC = AjouteLivreAuCatalogue("Jean-Claude Carrière", "Le Mahabharata");
        livreC.setLangue("vie");
        ArrayList<Livre> listeLivreUnThemeRef = new ArrayList<Livre>();
        listeLivreUnThemeRef.add(livreA);
        listeLivreUnThemeRef.add(livreB);
		
		//WHEN
		ArrayList<Livre> listeLivreUnThemeRetour = bibliothecaire.TrouverLivreSurUnTheme(theme);
		
		//THEN
		assertNotNull(listeLivreUnThemeRetour);
		Collections.sort(listeLivreUnThemeRef, new CompareLivre());
		Collections.sort(listeLivreUnThemeRetour, new CompareLivre());
		assertEquals(listeLivreUnThemeRef, listeLivreUnThemeRetour);
		System.out.println(listeLivreUnThemeRetour);
	}
	
	@Test
	void testEnvoyerAmendeRetardaire() {
		//GIVEN
		bibliothecaire.supprimeTout();
		Livre livre = AjouteLivreAuCatalogue("nomAuteur", "Un titre pour ce test");
		Lecteur lecteur = new Travailleur("prenom", "nom");
		boolean exceptionLevee1 = preterLivreTest(livre, lecteur);
		
		LivreEmprunte livreEmprunte = bibliothecaire.retourneLivreEmprunte(lecteur);
		LocalDate localDate = livreEmprunte.getDateEmprunt().minusDays(16);
		livreEmprunte.setDateEmprunt(localDate);
		bibliothecaire.RelancerEmprunteurEnRetard();
		
		//WHEN
		boolean exceptionLeveeSurEnvoyerAmendeRetardaire = false;
		try 
		{
			bibliothecaire.EnvoyerAmendeRetardaire();
		}
		catch(Exception ex)
		{
			exceptionLeveeSurEnvoyerAmendeRetardaire = true;
		}
		
		//THEN
		assertFalse(exceptionLevee1);
		assertFalse(exceptionLeveeSurEnvoyerAmendeRetardaire);
		assertTrue(lecteur.aLivreEnRetard());
		assertNotNull(lecteur.getAmende());
		assertTrue((lecteur.getAmende().getAmende()) == new Amende(Period.between(localDate, LocalDate.now()).getDays()).getAmende());
	}
	
	@Test
	void testEncaisserAmendeRetardaire() {
		//GIVEN
		bibliothecaire.supprimeTout();
		Livre livre = AjouteLivreAuCatalogue("nomAuteur", "Un titre");
		int ancienMontant = bibliothecaire.getTresorerie();
		Lecteur lecteur = new Travailleur("prenom", "nom");
		boolean exceptionLevee1 = preterLivreTest(livre, lecteur);
		
		LivreEmprunte livreEmprunte = bibliothecaire.retourneLivreEmprunte(lecteur);
		LocalDate localDate = livreEmprunte.getDateEmprunt().minusDays(16);
		livreEmprunte.setDateEmprunt(localDate);
		bibliothecaire.RelancerEmprunteurEnRetard();
		
		boolean exceptionLeveeSurEnvoyerAmendeRetardaire = false;
		try
		{
			bibliothecaire.EnvoyerAmendeRetardaire();
		}
		catch(Exception e)
		{
			exceptionLeveeSurEnvoyerAmendeRetardaire = true;
		}
		
		//WHEN
		boolean exceptionLeveeSurEncaisserAmendeRetardaire = false;
		try
		{
		bibliothecaire.EncaisserAmendeRetardaire(lecteur);
		}
		catch(Exception e)
		{
			exceptionLeveeSurEncaisserAmendeRetardaire = true;
		}
		
		//THEN
		assertFalse(exceptionLevee1);
		assertFalse(exceptionLeveeSurEncaisserAmendeRetardaire);
		assertFalse(exceptionLeveeSurEnvoyerAmendeRetardaire);
		assertNull(lecteur.getAmende());
		assertTrue(bibliothecaire.getTresorerie() > ancienMontant);
	}

	private boolean preterLivreTest(Livre ancienLivre, Lecteur lecteur) {
		
		boolean exceptionLeve=false;
		try
		{
			//WHEN
			bibliothecaire.preterLivre(ancienLivre, lecteur);			
		}
		catch(Exception e)
		{
			exceptionLeve=true;
		}
		
		return exceptionLeve;
	}
	
	private Livre creerLivre(String nomAuteur, String titreLivre){
		Auteur auteur=new Auteur(nomAuteur);
		return new Livre(auteur, titreLivre);
	}
	
	private Livre AjouteLivreAuCatalogue(String nomAuteur, String titreLivre){
		Livre livre = creerLivre(nomAuteur, titreLivre);
		bibliothecaire.ajouterLivre(livre);
		return livre;
	}
}
