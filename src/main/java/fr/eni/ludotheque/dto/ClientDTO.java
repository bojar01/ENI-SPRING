package fr.eni.ludotheque.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {
    private String nom;
    private String prenom;
    private String email;
    private String noTelephone;

    private String rue;
    private String codePostal;
    private String ville;
}
