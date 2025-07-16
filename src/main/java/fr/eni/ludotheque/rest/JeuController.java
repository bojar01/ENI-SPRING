package fr.eni.ludotheque.rest;

import fr.eni.ludotheque.bll.JeuService;
import fr.eni.ludotheque.bll.JeuServiceImpl;
import fr.eni.ludotheque.bo.Jeu;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/jeux")
public class JeuController {

    private final JeuService jeuService;

    public JeuController(JeuService jeuService) {
        this.jeuService = jeuService;
    }

    @GetMapping
    public ApiResponse<List<Jeu>> getJeux() {
        // This is a placeholder implementation. Replace with actual logic to fetch games.
        return new ApiResponse<>(true, "Liste des jeux récupérée avec succès", jeuService.listeJeuxCatalogue(""));
    }

    @PostMapping
    public ApiResponse<Jeu> ajouterJeu(Jeu jeu) {
        // This is a placeholder implementation. Replace with actual logic to add a game.
        jeuService.ajouterJeu(jeu);
        return new ApiResponse<>(true, "Jeu ajouté avec succès", jeu);
    }
}
