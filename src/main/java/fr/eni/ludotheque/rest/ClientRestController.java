package fr.eni.ludotheque.rest;

import fr.eni.ludotheque.bll.ClientService;
import fr.eni.ludotheque.bo.Client;
import fr.eni.ludotheque.dto.ClientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientRestController {
    @Autowired
    private ClientService clientService;

    // ajout d'un client
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Client ajouterClient(@RequestBody ClientDTO clientDto){
        return clientService.ajouterClient(clientDto);
    }

    // supprimer un client
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void supprimerClient(@PathVariable Integer id) {
        clientService.supprimerClient(id);
    }


    // modifier un client
    @PutMapping("/update/{id}")
    public Client modifierClient(@PathVariable String noClient, @RequestBody ClientDTO clientDto){
        return clientService.modifierClient(noClient, clientDto);
    }
}
