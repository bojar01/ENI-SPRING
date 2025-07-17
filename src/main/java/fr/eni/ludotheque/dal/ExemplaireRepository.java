package fr.eni.ludotheque.dal;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.eni.ludotheque.bo.Exemplaire;

import java.util.List;

public interface ExemplaireRepository extends MongoRepository<Exemplaire, String> {

	// Version qui retourne directement un int
	@Aggregation(pipeline = {
			// 1. Match les exemplaires du jeu spécifié et louables
			"{ $match: { " +
					"   jeuId: '?0', " +
					"   louable: true " +
					"} }",

			// 2. LEFT JOIN avec locations pour vérifier la disponibilité
			"{ $lookup: { " +
					"   from: 'location', " +
					"   let: { exemplaireId: '$_id' }, " +
					"   pipeline: [ " +
					"     { $match: { " +
					"       $expr: { " +
					"         $and: [ " +
					"           { $eq: ['$exemplaireId', '$$exemplaireId'] }, " +
					"           { $eq: ['$dateRetour', null] } " +
					"         ] " +
					"       } " +
					"     } } " +
					"   ], " +
					"   as: 'locationsActives' " +
					"} }",

			// 3. Filtrer les exemplaires qui ne sont PAS en location
			"{ $match: { " +
					"   locationsActives: { $size: 0 } " +
					"} }",

			// 4. Compter le nombre total
			"{ $count: 'total' }"
	})
	int nbExemplairesDisponibleByNoJeu(@Param("noJeu") String noJeu);

	// Votre méthode existante
	Exemplaire findByCodebarre(String codebarre);

	// Méthodes utilitaires supplémentaires
	@Query("{ 'jeuId': ?0 }")
	List<Exemplaire> findByJeuId(String jeuId);

	@Query("{ 'jeuId': ?0, 'louable': true }")
	long countByJeuIdAndLouableTrue(String jeuId);
}