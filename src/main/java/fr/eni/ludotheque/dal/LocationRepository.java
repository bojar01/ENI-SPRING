package fr.eni.ludotheque.dal;

import fr.eni.ludotheque.bo.Location;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LocationRepository extends MongoRepository<Location, Integer> {
    @Aggregation(pipeline = {
            "{ $lookup: { from: 'exemplaires', localField: 'exemplaire.$id', foreignField: '_id', as: 'exemplaireDetails' } }",
            "{ $unwind: '$exemplaireDetails' }",
            "{ $match: { 'exemplaireDetails.code_barre': ?0 } }"
    })
    Location findLocationByCodebarreWithJeu(String codebarre);


}
