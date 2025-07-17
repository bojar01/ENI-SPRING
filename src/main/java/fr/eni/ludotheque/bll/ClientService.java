package fr.eni.ludotheque.bll;

import fr.eni.ludotheque.bo.Client;
import fr.eni.ludotheque.dto.AdresseDTO;
import fr.eni.ludotheque.dto.ClientDTO;

import java.util.List;

public interface ClientService {

	public Client ajouterClient(ClientDTO clientDto);

	public List<Client> trouverClientsParNom(String nom);

	public Client modifierClient(String noClient, ClientDTO clientDto);

	public Client trouverClientParId(String id);

	public Client modifierAdresse(String noClient, AdresseDTO adresseDto) ;
			void supprimerClient(Integer id);

	public void supprimerClient(String id);

}
