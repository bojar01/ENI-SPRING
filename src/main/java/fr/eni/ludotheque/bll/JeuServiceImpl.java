package fr.eni.ludotheque.bll;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import fr.eni.ludotheque.bo.Jeu;
import fr.eni.ludotheque.dal.ExemplaireRepository;
import fr.eni.ludotheque.dal.JeuRepository;
import fr.eni.ludotheque.exceptions.DataNotFound;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class JeuServiceImpl implements JeuService{
	@NonNull
	private JeuRepository jeuRepository;
	
	@NonNull
	private ExemplaireRepository exemplaireRepository;
	
	@Override
	public void ajouterJeu(Jeu jeu) {
		
		jeuRepository.save(jeu);
		
		
	}


	@Override
	public Jeu trouverJeuParNoJeu(String noJeu) {
		Optional<Jeu> optJeu = jeuRepository.findById(noJeu);
		
		if(optJeu.isEmpty()) {
			throw new DataNotFound("Jeu", noJeu);
		}
		return optJeu.get();
		
	}
/*

	@Override
	public List<Jeu> listeJeuxCatalogue(String filtreTitre) {
		List<Jeu> jeux = jeuRepository.findAllJeuxAvecNbExemplaires(filtreTitre);
		
		for(Jeu jeu : jeux) {
			int nbExemplairesDisponibles = exemplaireRepository.nbExemplairesDisponibleByNoJeu(jeu.getNoJeu());
			jeu.setNbExemplairesDisponibles(nbExemplairesDisponibles);
		}
		
		return jeux;
	}
*/
	

}
