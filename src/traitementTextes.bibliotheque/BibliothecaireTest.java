package traitementTextes.bibliotheque;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

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
		Auteur auteur=new Auteur("nomAuteur");
		ArrayList<Livre> livres=new ArrayList<>();
		String titre = "un titre presomptueux";
		Livre premierLivre=new Livre(auteur, titre);
		livres.add(premierLivre);
		//bibliothecaire.supprimeTout(); 
		bibliothecaire.getCatalogue().put(auteur, livres);
		
		//WHEN
		String listeOeuvres = bibliothecaire.listerOeuvresAuteur(auteur);
		
		//THEN
		assertNotNull(listeOeuvres);
		assertTrue(listeOeuvres.contains(titre));
		System.out.println(listeOeuvres);
	}
	
	
	@Test
	void testAjouterLivre() {
		//GIVEN
		Auteur auteur = new Auteur("nomAuteur");
		//ArrayList<Livre> livres = new ArrayList<>();
		String titre = "Un titre";
		Livre nouveauLivre = new Livre(auteur, titre);
		//livres.add(nouveauLivre);
		int ancienMontant = bibliothecaire.getTresorerie();
		//bibliothecaire.supprimeTout(); 
		
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
		Auteur auteur = new Auteur("nomAuteur");
		String titre = "Un titre";
		Livre ancienLivre = new Livre(auteur, titre);
		//bibliothecaire.supprimeTout(); 
		bibliothecaire.ajouterLivre(ancienLivre);
		
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
		Auteur auteur = new Auteur("nomAuteur");
		String titre = "Un titre";
		Lecteur lecteur = new Lecteur("prenom", "nom");
		Livre ancienLivre = new  Livre(auteur, titre);
		//bibliothecaire.supprimeTout(); //WHYY
		
		//WHEN
		bibliothecaire.preterLivre(ancienLivre, lecteur);
		
		//THEN
		// Il faudrait quand même tester les dates nan ?
		//assertTrue(lecteur.getLivreEmprunte().equals(ancienLivre));
		//assertTrue(lecteur.getRetourPrevu() == 15);
		//assertTrue(lecteur.getDateEmprunt().equals("aujourd'hui"));
		Livre livre = (Livre) bibliothecaire.retourneLivreEmprunte(lecteur);
		assertTrue(livre.equals(ancienLivre));
	}
	
	@Test
	void testRelancerEmprunteurEnRetard() {
		// premier scenario : pas de retard
				//GIVEN
				Lecteur lecteur = new Lecteur("prenom", "nom");	
				
				Auteur auteur = new Auteur("nomAuteur");
				String titre = "Un titre";
				Livre livre = new Livre(auteur, titre);
				//bibliothecaire.supprimeTout();
				bibliothecaire.preterLivre(livre, lecteur);

				//WHEN		
				bibliothecaire.RelancerEmprunteurEnRetard();
				
				//THEN
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
		fail("Not yet implemented");
	}
	
	@Test
	void testListerLivresEmpruntesParEtudiant() {
		fail("Not yet implemented");
	}
	
	@Test
    void testListerLivresEmpruntes() {
        //GIVEN
        // est-ce que le given n'est plus utile si on utilise directement getLivresEmpruntes()?
        Auteur auteurA = new Auteur("nomAuteur1");
        String titreA = "Un titre1";
        Auteur auteurB = new Auteur("nomAuteur2");
        String titreB = "Un titre2";
        Livre livreUn = new Livre(auteurA, titreA);
        Livre livreDeux = new Livre(auteurB, titreB);

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
        Auteur auteurA = new Auteur("James Joyce");
        String titreA = "Ulysses";
        Livre livreA = new Livre(auteurA, titreA);
        livreA.setLangue("en");

        String titreA1 = "Un livre non anglais de Joyce";
        Livre livreA1 = new Livre(auteurA, titreA1);
        livreA1.setLangue("es");

        Auteur auteurB = new Auteur("Gustav Flaubert");
        String titreB = "Madame Bovary";
        Livre livreB = new Livre(auteurB, titreB);
        livreB.setLangue("fr");

        Auteur auteurC = new Auteur("George Orwell");
        String titreC = "Animal Farm";
        Livre livreC = new Livre(auteurC, titreC);
        livreC.setLangue("en");

        // reutilisation de l'existant
        bibliothecaire.ajouterLivre(livreA);
        bibliothecaire.ajouterLivre(livreA1);
        bibliothecaire.ajouterLivre(livreB);
        bibliothecaire.ajouterLivre(livreC);

        //WHEN
        String listeLivresAnglais = bibliothecaire.listerLivresAnglais();

        //THEN
        assertNotNull(listeLivresAnglais);
        System.out.println(listeLivresAnglais);

    }
	
	@Test
    void testListerNbLivresEmpruntesPourUnAuteur() {
        //GIVEN
        Auteur auteur = new Auteur("Un auteur a chercher");

        //WHEN
        ArrayList<Livre> listeLivresPourUnAuteur = bibliothecaire.listerNbLivresEmpruntesPourUnAuteur(auteur);

        //THEN
        assertNotNull(listeLivresPourUnAuteur);
        System.out.println(listeLivresPourUnAuteur);
    }
	
	@Test
	void testTrouverLivreSurUnTheme() {
		//GIVEN
		String theme = "Dragons";
		
		Auteur auteur1 = new Auteur("Pas celui-là");
		String titre1 = "La rencontre près du lac";
		Livre pasCeLivre = new  Livre(auteur1, titre1);
		pasCeLivre.setTheme("Amour");
		
		Auteur auteur2 = new Auteur("auteur2");
		String titre2 = "L'enlèvement de la princesse";
		Livre ceLivre = new  Livre(auteur2, titre2);
		ceLivre.setTheme(theme);
		
		//Auteur auteur3 = new Auteur("auteur3");
		String titre3 = "Les dragons du château";
		Livre etCeLivreAussi = new  Livre(auteur2, titre3);
		etCeLivreAussi.setTheme(theme);
		
		bibliothecaire.ajouterLivre(pasCeLivre);
		bibliothecaire.ajouterLivre(ceLivre);
		bibliothecaire.ajouterLivre(etCeLivreAussi);
		
		//bibliothecaire.supprimeTout(); 
		
		//WHEN
		String listeLivres = bibliothecaire.TrouverLivreSurUnTheme(theme);
		
		//THEN
		assertNotNull(listeLivres);
		assertTrue(listeLivres.contains((CharSequence) titre2));
		assertTrue(listeLivres.contains((CharSequence) titre3));
		System.out.println(listeLivres);
	}
	
	@Test
	void testEnvoyerAmendeRetardaire() {
		//GIVEN
		Lecteur lecteur = new Lecteur("prenom", "nom");
		Auteur auteur = new Auteur("nomAuteur");
		String titre = "Un titre pour ce test";
		Livre livre = new  Livre(auteur, titre);
		bibliothecaire.preterLivre(livre, lecteur);
		
		LivreEmprunte livreEmprunte = bibliothecaire.retourneLivreEmprunte(lecteur);
		LocalDate localDate = livreEmprunte.getDateEmprunt().minusDays(16);
		livreEmprunte.setDateEmprunt(localDate);
		bibliothecaire.RelancerEmprunteurEnRetard();
		
		//WHEN
		bibliothecaire.EnvoyerAmendeRetardaire();
		
		//THEN
		assertTrue(lecteur.aLivreEnRetard());
		assertNotNull(lecteur.getAmende());
		assertTrue((lecteur.getAmende().getAmende()) == new Amende(Period.between(localDate, LocalDate.now()).getDays()).getAmende());
	}
	
	@Test
	void testEncaisserAmendeRetardaire() {
		//GIVEN
		int ancienMontant = bibliothecaire.getTresorerie();
		Lecteur lecteur = new Lecteur("prenom", "nom");
		Auteur auteur = new Auteur("nomAuteur");
		String titre = "Un titre";
		Livre livre = new  Livre(auteur, titre);
		bibliothecaire.preterLivre(livre, lecteur);
		
		LivreEmprunte livreEmprunte = bibliothecaire.retourneLivreEmprunte(lecteur);
		LocalDate localDate = livreEmprunte.getDateEmprunt().minusDays(16);
		livreEmprunte.setDateEmprunt(localDate);
		bibliothecaire.RelancerEmprunteurEnRetard();
		
		bibliothecaire.EnvoyerAmendeRetardaire();
		
		//WHEN
		bibliothecaire.EncaisserAmendeRetardaire(lecteur);
		
		//THEN
		assertNull(lecteur.getAmende());
		assertTrue(bibliothecaire.getTresorerie() > ancienMontant);
	}

}
