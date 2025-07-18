package fr.eni.ludotheque.rest;

import fr.eni.ludotheque.bo.Jeu;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface BaseController<T> {
    ApiResponse<List<T>> getAll();
    ApiResponse<T> getById(String id);
    ApiResponse<T> create(T entity);
    ApiResponse<T> update(String id, T entity);
    ApiResponse<Void> delete(String id);
}
