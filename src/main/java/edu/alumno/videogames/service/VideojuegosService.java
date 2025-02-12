package edu.alumno.videogames.service;

import edu.alumno.videogames.model.dto.VideojuegoEdit;
import edu.alumno.videogames.model.dto.VideojuegoInfo;

public interface VideojuegosService {
    VideojuegoEdit create(VideojuegoEdit entity);

    VideojuegoInfo read(Long id);

    VideojuegoEdit update(Long id, VideojuegoEdit entity);

    void delete(Long id);
}
