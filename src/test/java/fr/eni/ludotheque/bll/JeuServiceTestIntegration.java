package fr.eni.ludotheque.bll;

import fr.eni.ludotheque.bo.Genre;
import fr.eni.ludotheque.bo.Jeu;
import fr.eni.ludotheque.dal.JeuRepository;
import fr.eni.ludotheque.exceptions.DataNotFound;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
@Slf4j
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class JeuServiceTestIntegration {

	@Autowired
	private JeuService jeuService;

	@Autowired
	private JeuRepository jeuRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	@BeforeEach
	void cleanDatabase() {
		mongoTemplate.getDb().drop();
	}


	@Test
	@DisplayName("Test ajout jeu")
	public void testAjoutJeu() {
		// Arrange
		Jeu jeu = new Jeu("50 missions", "refMissions", 10.2f);
		jeu.setDescription("Description de 50 missions");
		jeu.setDuree(20);
		jeu.setAgeMin(8);
		//jeu.addGenre(new Genre(2, ""));
		//jeu.addGenre(new Genre(4, ""));

		// Act
		jeuService.ajouterJeu(jeu);

		// Assert
		Optional<Jeu> 	jeuDB = jeuRepository.findById(jeu.get_id());
		if(jeuDB.isEmpty()){
			fail();
			return;
		}
		
		assertThat(jeuDB.get()).isEqualTo(jeu);
		assertThat(jeuDB.get().getTitre()).isEqualTo(jeu.getTitre());
		assertThat(jeuDB.get().getDescription()).isEqualTo(jeu.getDescription());
		assertThat(jeuDB.get().getAgeMin()).isEqualTo(jeu.getAgeMin());
		assertThat(jeuDB.get().getDuree()).isEqualTo(jeu.getDuree());
		assertThat(jeuDB.get().getTarifJour()).isEqualTo(jeu.getTarifJour());
		//assertThat(jeuDB.get().getGenres().size()).isEqualTo(jeu.getGenres().size());
	}


	@Test
	@DisplayName("Trouver un jeu par son numéro de jeu")
	public void testTrouverJeuParNoJeu() {
		//Arrange
		Genre hackAndSlash = new Genre("GE01","hash and slash");
		Genre plateforme = new Genre("GE02","plateforme");

		Jeu jeu = new Jeu("50 missions", "refWelcome", 10.2f);
		jeu.setDescription("Description de 50 missions");
		jeu.setDuree(20);
		jeu.setAgeMin(8);

		jeu.addGenre(hackAndSlash);
		jeu.addGenre(plateforme);

		// Act
		jeuService.ajouterJeu(jeu);

		//Act
		Jeu welcome = jeuRepository.findByReference("refWelcome");
		Jeu jeuDB = null;
		try {
			jeuDB = jeuService.trouverJeuParJeu_id(welcome.get_id());
		}catch(DataNotFound dnf) {
			fail();
			return;
		}
		log.info(jeuDB.toString());
		assertThat(jeuDB).isEqualTo(welcome);
		assertThat(jeuDB.getTitre()).isEqualTo(welcome.getTitre());
		assertThat(jeuDB.getDescription()).isEqualTo(welcome.getDescription());


	}

	@Test
	@DisplayName("Test trouver les jeux et le nb d'exemplaires disponible")
	public void testTrouverJeuxDisponibles() {
		
		List<Jeu> jeux = jeuService.listeJeuxCatalogue("TOUS");
		System.out.println("------------------------------------------------------------------");
		jeux.forEach(System.out::println);
		System.out.println("------------------------------------------------------------------");
		log.info(jeux.toString());

		for (Jeu jeu : jeux) {
			assertNotNull(jeu.getReference());
		}
	}


}
