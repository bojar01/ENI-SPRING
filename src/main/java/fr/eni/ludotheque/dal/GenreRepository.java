package fr.eni.ludotheque.dal;


import fr.eni.ludotheque.bo.Genre;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GenreRepository extends MongoRepository<Genre, Integer> {

}
