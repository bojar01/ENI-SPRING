package fr.eni.ludotheque.rest;

import fr.eni.ludotheque.bll.GenreService;
import fr.eni.ludotheque.bo.Genre;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
public class GenreController implements BaseController<Genre>{

    GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    @Override
    public ApiResponse<List<Genre>> getAll() {
        List<Genre> genres = genreService.getAllGenres();
        return new ApiResponse<>(true, "Genres retrieved successfully", genres);
    }

    @GetMapping("/{id}")
    @Override
    public ApiResponse<Genre> getById(@PathVariable String id) {
        Genre genre = genreService.getGenreById(id);
        return new ApiResponse<Genre>(true, "Genre", genre);
    }

    @PostMapping
    @Override
    public ApiResponse<Genre> create(@RequestBody Genre entity) {
        Genre createdGenre = genreService.createGenre(entity);
        return new ApiResponse<>(true, "Genre created successfully", createdGenre);
    }

    @PutMapping("/{id}")
    @Override
    public ApiResponse<Genre> update(@PathVariable String id, @RequestBody Genre genre) {
        Genre updatedGenre = genreService.updateGenre(id, genre);
        return new ApiResponse<>(true, "Genre updated successfully", updatedGenre);
    }

    @DeleteMapping("/{id}")
    @Override
    public ApiResponse<Void> delete(@PathVariable String id) {
        genreService.deleteGenre(id);
        return new ApiResponse<>(true, "Genre deleted successfully", null);
    }
}
