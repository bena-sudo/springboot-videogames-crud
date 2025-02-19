package edu.alumno.videogames.service;

import java.util.List;

import edu.alumno.videogames.exception.FiltroException;
import edu.alumno.videogames.filters.model.PaginaResponse;
import edu.alumno.videogames.filters.model.PeticionListadoFiltrado;
import edu.alumno.videogames.model.dto.VideojuegoEdit;
import edu.alumno.videogames.model.dto.VideojuegoInfo;
import edu.alumno.videogames.model.dto.VideojuegoList;

public interface VideojuegosService {
    VideojuegoEdit create(VideojuegoEdit entity);

    VideojuegoInfo read(Long id);

    VideojuegoEdit update(Long id, VideojuegoEdit entity);

    void delete(Long id);

    PaginaResponse<VideojuegoList> findAll(PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException;

    PaginaResponse<VideojuegoList> findAll(List<String> filter, int page, int size, List<String> sort)
            throws FiltroException;
}
