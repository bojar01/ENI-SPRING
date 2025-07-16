package fr.eni.ludotheque.dal;

import fr.eni.ludotheque.bo.Jeu;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JeuRepository extends MongoRepository<Jeu, String> {

    // Version qui correspond à votre requête SQL avec filtres
    @Aggregation(pipeline = {
            "{ $lookup: { from: 'exemplaire', localField: '_id', foreignField: 'jeuId', as: 'exemplaires' } }",
            "{ $addFields: { " +
                    "   exemplairesLouables: { " +
                    "     $filter: { " +
                    "       input: '$exemplaires', " +
                    "       cond: { $eq: ['$$this.louable', true] } " +
                    "     } " +
                    "   } " +
                    "} }",
            "{ $match: { " +
                    "   $or: [ " +
                    "     { $expr: { $eq: ['?0', 'TOUS'] } }, " +
                    "     { titre: { $regex: '?0', $options: 'i' } } " +
                    "   ] " +
                    "} }",
            "{ $addFields: { " +
                    "   nbExemplaires: { $size: '$exemplairesLouables' } " +
                    "} }",
            "{ $project: { exemplaires: 0, exemplairesLouables: 0 } }"
    })
    List<Jeu> findAllJeuxAvecNbExemplaires(@Param("titre") String titre);

    // Version simple sans filtres (celle que vous avez déjà)
    @Aggregation(pipeline = {
            "{ $lookup: { from: 'exemplaire', localField: '_id', foreignField: 'jeuId', as: 'exemplaires' } }",
            "{ $addFields: { nbExemplaires: { $size: '$exemplaires' } } }",
            "{ $project: { exemplaires: 0 } }"
    })
    List<Jeu> findAllJeuxAvecNbExemplaires();

    // Votre méthode pour récupérer le tarif
    @Query(value = "{ 'nom': ?0 }", fields = "{ 'tarifJour': 1 }")
    Float findTarifJour(String nom);

    // Votre méthode de recherche par référence
    Jeu findByReference(String reference);
}