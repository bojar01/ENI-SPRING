package fr.eni.ludotheque.rest;

import fr.eni.ludotheque.bll.ClientService;
import fr.eni.ludotheque.bo.Client;
import fr.eni.ludotheque.dto.ClientDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/clients")
public class ClientRestController {

    private final ClientService clientService;

    public ClientRestController(ClientService clientService) {
        this.clientService = clientService;
    }

    //liste des clients
    @GetMapping
    public ResponseEntity<ApiResponse<List<Client>>> getAllClients() {
        List<Client> clients = clientService.getAllClients();
        return ResponseEntity.ok(new ApiResponse<>(true, "Liste des clients", clients));
    }

    //un seul client
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Client>> getClientById(@PathVariable String id) {
        return clientService.trouverClientParId(id)
                .map(client -> ResponseEntity.ok(new ApiResponse<>(true, "Client trouvé", client)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Client non trouvé avec id: " + id, null)));
    }

    //ajouter un client
    @PostMapping
    public ResponseEntity<ApiResponse<Client>> createClient(@RequestBody ClientDTO clientDto) {
        if (clientDto.getNom() == null || clientDto.getNom().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, "Nom du client obligatoire", null));
        }

        Client createdClient = clientService.ajouterClient(clientDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", "/api/v2/clients/" + createdClient.getNoClient())
                .body(new ApiResponse<>(true, "Client est bien crée", createdClient));
    }


}
