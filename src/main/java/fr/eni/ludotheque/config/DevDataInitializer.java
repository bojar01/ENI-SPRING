package fr.eni.ludotheque.config;

import fr.eni.ludotheque.bo.Exemplaire;
import fr.eni.ludotheque.bo.Genre;
import fr.eni.ludotheque.bo.Jeu;
import fr.eni.ludotheque.dal.ClientRepository;
import fr.eni.ludotheque.dal.ExemplaireRepository;
import fr.eni.ludotheque.dal.GenreRepository;
import fr.eni.ludotheque.dal.JeuRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Profile("dev")
public class DevDataInitializer implements CommandLineRunner {

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private JeuRepository jeuRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ExemplaireRepository exemplaireRepository;

    @Override
    public void run(String... args) throws Exception {
        if (jeuRepository.count() == 0) {
            initData();
        }
    }

    @PostConstruct
    public void initData() {
        // Vider les collections
        genreRepository.deleteAll();
        jeuRepository.deleteAll();
        clientRepository.deleteAll();
        exemplaireRepository.deleteAll();

        // Insérer les genres
        List<Genre> genres = Arrays.asList(
                new Genre(1, "Jeu de plateau"),
                new Genre(2, "Jeu de cartes"),
                new Genre(3, "Jeu de stratégie"),
                new Genre(4, "Coopératif"),
                new Genre(5, "Jeu de dé"),
                new Genre(6, "Jeu d'enquete")
        );
        genreRepository.saveAll(genres);

        // Insérer les jeux
        Jeu pandemic = new Jeu();
        pandemic.setTitre("Pandemic");
        pandemic.setDescription("Descr pandemic");
        pandemic.setReference("refPandemic");
        pandemic.setDuree(30);
        pandemic.setAgeMin(10);
        pandemic.setTarifJour(12.5f);
        pandemic.setGenres(Arrays.asList(
                genres.get(2), // Stratégie
                genres.get(0)  // Plateau
        ));

        Jeu welcome = new Jeu();
        welcome.setTitre("Welcome");
        welcome.setDescription("Descr welcome");
        welcome.setReference("refWelcome");
        welcome.setDuree(30);
        welcome.setAgeMin(10);
        welcome.setTarifJour(9.3f);
        welcome.setGenres(Arrays.asList(
                genres.get(1), // Cartes
                genres.get(2)  // Stratégie
        ));

        List<Jeu> jeux = jeuRepository.saveAll(Arrays.asList(pandemic, welcome));
        Jeu savedPandemic = jeux.get(0);
        Jeu savedWelcome = jeux.get(1);


        List<Exemplaire> exemplaires = Arrays.asList(

                new Exemplaire("1111111111111", savedPandemic),
                new Exemplaire("2222222222222", savedPandemic),
                new Exemplaire("3333333333333", savedPandemic),

                new Exemplaire("4444444444444", savedWelcome),
                new Exemplaire("5555555555555", savedWelcome),
                new Exemplaire("6666666666666", savedWelcome)
        );

        // Définir le statut louable
        exemplaires.get(1).setLouable(false); // 2222222222222
        exemplaires.get(4).setLouable(false); // 5555555555555

        exemplaireRepository.saveAll(exemplaires);

        System.out.println("Données de test initialisées !");
    }
}
