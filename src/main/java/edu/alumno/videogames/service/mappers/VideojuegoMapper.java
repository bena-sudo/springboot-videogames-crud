package edu.alumno.videogames.service.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import edu.alumno.videogames.filters.model.FiltroBusqueda;
import edu.alumno.videogames.filters.model.PaginaResponse;
import edu.alumno.videogames.model.db.Videojuego;
import edu.alumno.videogames.model.dto.VideojuegoEdit;
import edu.alumno.videogames.model.dto.VideojuegoInfo;
import edu.alumno.videogames.model.dto.VideojuegoList;

@Mapper
public interface VideojuegoMapper {
    VideojuegoMapper INSTANCE = Mappers.getMapper(VideojuegoMapper.class);

    VideojuegoEdit videojuegoToVideojuegoEdit(Videojuego videojuego);

    Videojuego videojuegoEditToVideojuego(VideojuegoEdit videojuegoEdit);

    VideojuegoInfo videojuegoToVideojuegoInfo(Videojuego videojuego);

    void updateVideojuegoFromVideojuegoEdit(VideojuegoEdit videojuegoEdit, @MappingTarget Videojuego videojuego);

    List<VideojuegoList> videojuegosToVidejuegosList(List<Videojuego> videojuegos);

    static PaginaResponse<VideojuegoList> pageToPaginaResponseEtiquetaList(
            Page<Videojuego> page,
            List<FiltroBusqueda> filtros,
            List<String> ordenaciones) {
        return new PaginaResponse<>(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                INSTANCE.videojuegosToVidejuegosList(page.getContent()),
                filtros,
                ordenaciones);
    }
}