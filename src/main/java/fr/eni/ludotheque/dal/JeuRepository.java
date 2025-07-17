package fr.eni.ludotheque.dal;

import fr.eni.ludotheque.bo.Jeu;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JeuRepository extends MongoRepository<Jeu, String> {


    @Aggregation(pipeline = {
            "{ $lookup: { from: 'exemplaire', localField: '_id', foreignField: 'jeuId', as: 'exemplaires' } }",
            "{ $addFields: { nbExemplaires: { $size: '$exemplaires' } } }",
            "{ $project: { exemplaires: 0 } }"
    })
    List<Jeu> findAllJeuxAvecNbExemplaires();


    @Query(value = "{ 'nom': ?0 }", fields = "{ 'tarifJour': 1 }")
    Float findTarifJour(String nom);

    Jeu findByReference(String reference);
}