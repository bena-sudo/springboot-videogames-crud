package edu.alumno.videogames.service;

public interface CrudService<T, R> {
    T create(T entity);

    R read(Long id);

    T update(Long id, T entity);

    void delete(Long id);
}
