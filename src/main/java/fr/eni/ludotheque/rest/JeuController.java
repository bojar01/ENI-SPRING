package fr.eni.ludotheque.rest;

import fr.eni.ludotheque.bll.JeuService;
import fr.eni.ludotheque.bo.Jeu;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jeux")
public class JeuController implements BaseController<Jeu> {

    private final JeuService jeuService;

    public JeuController(JeuService jeuService) {
        this.jeuService = jeuService;
    }

    @GetMapping
    public ApiResponse<List<Jeu>> getJeux() {
        // This is a placeholder implementation. Replace with actual logic to fetch games.
        return new ApiResponse<>(true, "Liste des jeux récupérée avec succès", jeuService.listeJeuxCatalogue("TOUS"));
    }

/*    @PostMapping
    public ApiResponse<Jeu> ajouterJeu(@RequestBody Jeu jeu) {
        System.out.println("Try to add this game : " + jeu);
        // This is a placeholder implementation. Replace with actual logic to add a game.
        jeuService.ajouterJeu(jeu);
        return new ApiResponse<>(true, "Jeu ajouté avec succès", jeu);
    }*/

    @Override
    public ApiResponse<List<Jeu>> getAll() {
        List<Jeu> jeux = jeuService.listeJeuxCatalogue("TOUS");
        return new ApiResponse<>(true, "Liste des jeux récupérée avec succès", jeux);
    }

    @GetMapping("/{id}")
    @Override
    public ApiResponse<Jeu> getById(@PathVariable String id) {
        Jeu jeu = jeuService.trouverJeuParJeu_id(id);
        return new ApiResponse<>(true, "Jeu trouvé", jeu);
    }

    @PostMapping
    @Override
    public ApiResponse<Jeu> create(@RequestBody Jeu jeu) {
        jeuService.ajouterJeu(jeu);
        return new ApiResponse<>(true, "Jeu créé avec succès", jeu);
    }

    @PutMapping("/{id}")
    @Override
    public ApiResponse<Jeu> update(@PathVariable String id, @RequestBody Jeu jeu) {
        Jeu updatedJeu = jeuService.update(id, jeu);
        return new ApiResponse<>(true, "Jeu mis à jour avec succès", updatedJeu);
    }

    @DeleteMapping("/{id}")
    @Override
    public ApiResponse<Void> delete(@PathVariable String id) {
        jeuService.delete(id);
        return new ApiResponse<>(true, "Jeu supprimé avec succès", null);
    }
}
