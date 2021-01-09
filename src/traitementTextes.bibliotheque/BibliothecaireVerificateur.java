package traitementTextes.bibliotheque;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

public class BibliothecaireVerificateur {
	
	static public void VerifierSiLivreDejaEmprunte(HashSet<LivreEmprunte> livresEmpruntes, Livre livre) throws Exception {
		for(LivreEmprunte livreEmprunte : livresEmpruntes){
			// on vérifie que le livre n'a pas été déjà emprunté
			Livre livreCourant = livreEmprunte.getLivre();
			if (livreCourant.equals(livre)){
				throw new Exception("Le livre '" + livre.getTitre() +"' a déjà été emprunté."); 
			}
		}
	}

	static public void VerifierSiLecteurADejaEmprunteLivre(Lecteur lecteur, LivreEmprunte livreEmprunte) throws Exception {
		// on vérifie que le lecteur a déjà emprunté un livre 
		if (Objects.nonNull(livreEmprunte)){
			throw new Exception("Le lecteur '" + lecteur.getNom() +"' a déjà emprunté un livre.");
		}		
	}

	static public void VerifierSiLivreExisteDansCatalogue(HashMap<Auteur, ArrayList<Livre>> catalogue, Livre livre) throws Exception {
		// on vérifie si le livre existe bien dans le catalogue
		ArrayList<Livre> listeLivre = catalogue.get(livre.getAuteur());
		if (!listeLivre.contains(livre)){
			throw new Exception("Le livre '" + livre.getTitre() +"' n'est pas dans le catalogue.");
		}		
	}
	
	static public void VerifierLecteurARetard(Lecteur lecteur) throws Exception
	{
		if (!lecteur.aLivreEnRetard()) {
			throw new Exception("Le lecteur '" + lecteur.getNom() +"' n'est pas en retard.");
		}
	}
	
	static public void VerifierLecteurAAmende(Lecteur lecteur) throws Exception
	{
		Amende amende = lecteur.getAmende();
		if (Objects.isNull(amende)){
			throw new Exception("Le lecteur '" + lecteur.getNom() +"' n'a pas d'amende à payer.");
		}
	}
	
	static public void VerifierLecteurAPayeBonMontantAmende(Lecteur lecteur, Amende amende, double amendePayee) throws Exception
	{
		if (amendePayee != amende.getAmende()){
			throw new Exception("Le lecteur '" + lecteur.getNom() +"' n'a pas payé le montant de l'amende. Montant dû : " + amende.getAmende() + ", montant payé : " + amendePayee);
		}		
	}
}
