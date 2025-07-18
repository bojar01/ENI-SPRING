package fr.eni.ludotheque.config;

import fr.eni.ludotheque.bo.*;
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
                new Genre("GE01", "Jeu de plateau"),
                new Genre("GE02", "Jeu de cartes"),
                new Genre("GE03", "Jeu de stratégie"),
                new Genre("GEO4", "Coopératif"),
                new Genre("GEO5", "Jeu de dé"),
                new Genre("GEO6", "Jeu d'enquete")
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


        // Insérer les clients
        List<Client> clients = Arrays.asList(
                new Client("Martin", "Jean", "jean.martin@email.com",
                        new Adresse("123 rue de la Paix", "75001", "Paris")),

                new Client("Durand", "Marie", "marie.durand@email.com",
                        new Adresse("456 avenue des Champs", "69001", "Lyon")),

                new Client("Lemoine", "Pierre", "pierre.lemoine@email.com",
                        new Adresse("789 boulevard Saint-Michel", "13001", "Marseille")),

                new Client("Bernard", "Sophie", "sophie.bernard@email.com",
                        new Adresse("321 rue du Commerce", "33000", "Bordeaux")),

                new Client("Rousseau", "Antoine", "antoine.rousseau@email.com",
                        new Adresse("654 place de la République", "44000", "Nantes"))
        );

        // Ajouter des numéros de téléphone à certains clients
        clients.get(0).setNoTelephone("0123456789");
        clients.get(2).setNoTelephone("0678901234");
        clients.get(4).setNoTelephone("0145678901");

        clientRepository.saveAll(clients);


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
