package fr.eni.ludotheque.rest;

import fr.eni.ludotheque.bll.ClientService;
import fr.eni.ludotheque.bll.ExemplaireService;
import fr.eni.ludotheque.bo.Exemplaire;
import fr.eni.ludotheque.exceptions.ExemplaireNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/exemplaires")
public class ExemplaireRestController {
//    private final ClientService clientService;
    private final ExemplaireService exemplaireService;

    public ExemplaireRestController(ClientService clientService, ExemplaireService exemplaireService) {
//        this.clientService = clientService;
        this.exemplaireService = exemplaireService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Exemplaire>>> getAllExemplaires() {
        List<Exemplaire> exemplaires = exemplaireService.getAllExemplaire();
        return ResponseEntity.ok(new ApiResponse<>(true, "Liste des exemplaires", exemplaires));
    }

    //recupérer un exemplaire
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Exemplaire>> getExemplaireById(@PathVariable String id) {
        try {
            Exemplaire exemplaire = exemplaireService.getExemplaire(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Exemplaire trouvé", exemplaire));
        } catch (ExemplaireNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, ex.getMessage(), null));
        }
    }
    // ajouter un exemplaire
    @PostMapping
    public ResponseEntity<ApiResponse<Exemplaire>> createExemplaire(@RequestBody Exemplaire exemplaire) {
        if (exemplaire.getCodebarre() == null || exemplaire.getCodebarre().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, "Le code-barres de l'exemplaire est obligatoire", null));
        }

        exemplaireService.createExemplaire(exemplaire);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", "/api/v2/exemplaires/" + exemplaire.getNoExemplaire())
                .body(new ApiResponse<>(true, "Exemplaire créé avec succès", exemplaire));
    }
    // supprimer un exemplaire
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteExemplaire(@PathVariable String id) {
        try {
            exemplaireService.deleteExemplaire(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Exemplaire supprimé", null));
        } catch (ExemplaireNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, ex.getMessage(), null));
        }
    }
}
