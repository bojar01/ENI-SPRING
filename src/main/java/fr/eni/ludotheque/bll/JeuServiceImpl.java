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
	public Jeu trouverJeuParJeu_id(String jeu_id) {
		Optional<Jeu> optJeu = jeuRepository.findById(jeu_id);
		
		if(optJeu.isEmpty()) {
			throw new DataNotFound("Jeu", jeu_id);
		}
		return optJeu.get();
		
	}

	@Override
	public List<Jeu> listeJeuxCatalogue(String filtreTitre) {
		List<Jeu> jeux = jeuRepository.findAllJeuxAvecNbExemplaires();
		System.out.println(jeux);

		if(jeux.isEmpty()) {
			throw new DataNotFound("Jeu", filtreTitre);
		}
		
		return jeux;
	}

	@Override
	public Jeu update(String id, Jeu jeu) {
		jeuRepository.findById(id).orElseThrow(() -> new DataNotFound("Jeu", id));
		jeu.setTitre(jeu.getTitre().trim());
		jeu.setDescription(jeu.getDescription() != null ? jeu.getDescription().trim() : null);
		jeu.setGenres(jeu.getGenres() != null ? jeu.getGenres() : List.of());
		jeu.setDuree(jeu.getDuree() > 0 ? jeu.getDuree() : 0);
		jeu.setTarifJour(jeu.getTarifJour() != null ? jeu.getTarifJour() : 0f);
		jeu.setAgeMin(jeu.getAgeMin() >= 0 ? jeu.getAgeMin() : 0);
		jeu.setDuree(jeu.getDuree() > 0 ? jeu.getDuree() : 0);
		jeu.setNbExemplairesDisponibles(2); // this is a placeholder
		return jeuRepository.save(jeu);

	}

	@Override
	public void delete(String id) {
		if (!jeuRepository.existsById(id)) {
			throw new DataNotFound("Jeu", id);
		}
		jeuRepository.deleteById(id);
	}
}
