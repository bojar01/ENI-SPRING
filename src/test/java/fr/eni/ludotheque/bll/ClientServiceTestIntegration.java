package fr.eni.ludotheque.bll;

//import static org.mockito.Mockito.doAnswer;

import fr.eni.ludotheque.bo.Adresse;
import fr.eni.ludotheque.bo.Client;
import fr.eni.ludotheque.dal.AdresseRepository;
import fr.eni.ludotheque.dto.AdresseDTO;
import fr.eni.ludotheque.dto.ClientDTO;
import fr.eni.ludotheque.exceptions.DataNotFound;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ClientServiceTestIntegration {

	@Autowired
	private ClientService clientService;

	@Autowired
	private AdresseRepository adresseRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	@BeforeEach
	void cleanDatabase() {
		mongoTemplate.getDb().drop();
	}


	@Test
	@DisplayName("Trouver les clients dont le nom commence par")
	public void testAjouterClient() {
		//Arrange
		String nom = "DUP";
		ClientDTO client= new ClientDTO("DUPIEUX", "Quentin", "e1",  "tel1","rue des Cormorans",  "44860", "Saint Aignan Grand Lieu");

		//Act
		Client clientDB = clientService.ajouterClient(client);

		//Assert
		assertThat(clientDB.getEmail()).isEqualTo(client.getEmail());

	}

	@Test
	@DisplayName("Trouver les clients dont le nom commence par")
	public void testTrouverClientsDontLeNomCommencePar() {
		//Arrange
		String nom = "DUP";
		ClientDTO client= new ClientDTO("DUPIEUX", "Quentin", "e1",  "tel1","rue des Cormorans",  "44860", "Saint Aignan Grand Lieu");
		ClientDTO client2 = new ClientDTO("DUPONT", "Jacques", "e2", "tel2", "rue 2", "44860", "Saint Aignan Grand Lieu");
		clientService.ajouterClient(client);
		clientService.ajouterClient(client2);

		//Act
		List<Client> clients = clientService.trouverClientsParNom(nom);

		//Assert
		assertThat(clients).hasSize(2);

	}



	@Test
	@DisplayName("Test modification complète client")
	public void testModifierClientEtAdresseCasPositif() {
		// Arrange
		ClientDTO clientDto = new ClientDTO("nX", "pX", "eX", "telX","rue des Cormorans", "44860", "Saint Aignan Grand Lieu");
		Client newClient = clientService.ajouterClient(clientDto);

		clientDto.setEmail("bob@free.fr");
		clientDto.setNom("nXX");
		clientDto.setPrenom("pXX");
		clientDto.setRue("rue Franklin");
		clientDto.setCodePostal("44800");
		clientDto.setVille("Saint Herblain");
		
		// Act
		clientService.modifierClient(newClient.getNoClient(),clientDto);

		// Assert
		Optional<Client> optClient2 = clientService.trouverClientParId(newClient.getNoClient());
		if(optClient2.isEmpty()){
			throw new DataNotFound(" ", optClient2);
		}
		Client client2 = optClient2.get();
		assertThat(client2.getEmail()).isEqualTo(clientDto.getEmail());
		assertThat(client2.getNom()).isEqualTo(clientDto.getNom());
		assertThat(client2.getPrenom()).isEqualTo(clientDto.getPrenom());
		assertThat(client2.getAdresse().getRue()).isEqualTo(clientDto.getRue());
		assertThat(client2.getAdresse().getCodePostal()).isEqualTo(clientDto.getCodePostal());
		assertThat(client2.getAdresse().getVille()).isEqualTo(clientDto.getVille());

	}






}
