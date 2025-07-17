package fr.eni.ludotheque.dal;

import fr.eni.ludotheque.bo.Jeu;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JeuRepository extends MongoRepository<Jeu, String> {



    @Aggregation({
            "{ '$lookup': { " +
                    "'from': 'exemplaires', " +
                    "'localField': '_id', " +
                    "'foreignField': 'jeu._id', " +
                    "'as': 'exemplaires_jeu' " +
                    "}}",
            "{ '$addFields': { " +
                    "'nbExemplairesDisponibles': { " +
                    "'$size': { " +
                    "'$filter': { " +
                    "'input': '$exemplaires_jeu', " +
                    "'cond': { '$eq': ['$$this.louable', true] } " +
                    "} " +
                    "} " +
                    "} " +
                    "}}",
            "{ '$project': { " +
                    "'_id': 1, " +
                    "'exemplaires_jeu': 0, " +
                    "}}"
    })
    List<Jeu> findAllJeuxAvecNbExemplaires();


    @Aggregation(pipeline = {
            "{ '$match': { '_id': ?0 } }",
            "{ '$project': { 'tarifJour': 1, '_id': 0 } }"
    })
    Float findTarifJour(String id);

    Jeu findByReference(String reference);
}