package fr.eni.ludotheque.bll;

import fr.eni.ludotheque.bo.Client;
import fr.eni.ludotheque.dto.AdresseDTO;
import fr.eni.ludotheque.dto.ClientDTO;

import java.util.List;
import java.util.Optional;

public interface ClientService {

	public Client ajouterClient(ClientDTO clientDto);

	public List<Client> trouverClientsParNom(String nom);

	public Client modifierClient(String noClient, ClientDTO clientDto);

	public Optional<Client> trouverClientParId(String id);

	public Client modifierAdresse(String noClient, AdresseDTO adresseDto) ;
			void supprimerClient(Integer id);
			List<Client> getAllClients();


}
