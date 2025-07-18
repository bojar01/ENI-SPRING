package fr.eni.ludotheque.bll;

import fr.eni.ludotheque.bo.Jeu;

import java.util.List;

public interface JeuService {
	
	void ajouterJeu(Jeu jeu);
	
	Jeu trouverJeuParJeu_id(String noJeu);
	
	List<Jeu> listeJeuxCatalogue(String filtreTitre);

	Jeu update(String id, Jeu jeu);
	void delete(String id);
		
}
