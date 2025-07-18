package fr.eni.ludotheque.dal;


import fr.eni.ludotheque.bo.Genre;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface GenreRepository extends MongoRepository<Genre, String> {

}
