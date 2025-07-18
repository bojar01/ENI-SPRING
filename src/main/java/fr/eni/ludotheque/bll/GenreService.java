package fr.eni.ludotheque.bll;

import fr.eni.ludotheque.bo.Genre;
import org.springframework.stereotype.Service;

import java.util.List;

public interface GenreService {

    List<Genre> getAllGenres();
    Genre getGenreById(String id);
    Genre createGenre(Genre genre);
    Genre updateGenre(String id, Genre genre);
    void deleteGenre(String id);
}
