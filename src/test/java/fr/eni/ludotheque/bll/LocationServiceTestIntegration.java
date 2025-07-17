package fr.eni.ludotheque.bll;

import fr.eni.ludotheque.bo.*;
import fr.eni.ludotheque.dal.ClientRepository;
import fr.eni.ludotheque.dal.ExemplaireRepository;
import fr.eni.ludotheque.dal.JeuRepository;
import fr.eni.ludotheque.dto.LocationDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class LocationServiceTestIntegration {

    @Autowired
    private LocationService locationService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private ExemplaireRepository exemplaireRepository;
    @Autowired
    private JeuRepository jeuRepository;


    @Test
    public void testAjoutLocation() {
        //Arrange
        String codeBarre = "6666666666666";
        Client client = clientRepository.findByNoTelephone("0678901234");
        Exemplaire exemplaire = exemplaireRepository.findByCodebarre(codeBarre);
        Optional<Jeu> optJeu = jeuRepository.findById(exemplaire.getJeu().get_id());

        LocationDTO locationDTO = new LocationDTO(client.getNoClient(), codeBarre);

        //Act
        Location location = locationService.ajouterLocation(locationDTO);

        //Assert
        assertThat(location).isNotNull();
        assertThat(location.getDateDebut()).isNotNull();
        assertThat(location.getDateRetour()).isNull();
        assertThat(location.getTarifJour()).isEqualTo(9.3f);
    }


    @Test
    @DisplayName("Test du retour d'exemplaire et creation de la facture")
    public void testRetourExemplairesEtCreationFacture() {
        //Arrange
        Client client = clientRepository.findByNoTelephone("0678901234");
        LocationDTO locationDTO1 = new LocationDTO(client.getNoClient(), "6666666666666");
        Location loc1 = locationService.ajouterLocation(locationDTO1);
        LocationDTO locationDTO2 = new LocationDTO(client.getNoClient(), "1111111111111");
        Location loc2 = locationService.ajouterLocation(locationDTO2);

        List<String> codebarres = List.of("1111111111111", "6666666666666");

        //act
        Facture facture = locationService.retourExemplaires(codebarres);

        //Assert
        assertThat(facture).isNotNull();
        assertThat(facture.getPrix()).isEqualTo(21.8f);
        assertThat(facture.getLocations()).hasSize(2);
    }


    @Test
    @DisplayName("Test payer facture")
    public void testPayerFacture() {
        //Arrange
        Client client = clientRepository.findByNoTelephone("0678901234");
        LocationDTO locationDTO1 = new LocationDTO(client.getNoClient(), "6666666666666");
        Location loc1 = locationService.ajouterLocation(locationDTO1);
        LocationDTO locationDTO2 = new LocationDTO(client.getNoClient(), "1111111111111");
        Location loc2 = locationService.ajouterLocation(locationDTO2);
        List<String> codebarres = List.of("1111111111111", "6666666666666");
        Facture facture = locationService.retourExemplaires(codebarres);

        //act
        Facture facture2 = locationService.payerFacture(facture.getNoFacture());

        //Assert
        assertThat(facture2).isNotNull();
        assertThat(facture2.getDatePaiement()).isNotNull();
    }

}
