package fr.eni.ludotheque.dal;

import fr.eni.ludotheque.bo.Jeu;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JeuRepository extends MongoRepository<Jeu, String> {


    // Ou pour récupérer directement le tarif
    @Query(value = "{ 'nom': ?0 }", fields = "{ 'tarifJour': 1 }")
    Float findTarifJour(String nom);



    Jeu findByReference(String reference);
}
