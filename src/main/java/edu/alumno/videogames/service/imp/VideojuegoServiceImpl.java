package edu.alumno.videogames.service.imp;

import java.util.List;

import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import edu.alumno.videogames.exception.EntityIllegalArgumentException;
import edu.alumno.videogames.exception.EntityNotFoundException;
import edu.alumno.videogames.exception.FiltroException;
import edu.alumno.videogames.filters.model.PaginaResponse;
import edu.alumno.videogames.filters.model.PeticionListadoFiltrado;
import edu.alumno.videogames.filters.specification.FiltroBusquedaSpecification;
import edu.alumno.videogames.filters.utils.PaginationFactory;
import edu.alumno.videogames.filters.utils.PeticionListadoFiltradoConverter;
import edu.alumno.videogames.model.db.Videojuego;
import edu.alumno.videogames.model.dto.VideojuegoEdit;
import edu.alumno.videogames.model.dto.VideojuegoInfo;
import edu.alumno.videogames.model.dto.VideojuegoList;
import edu.alumno.videogames.repository.VideojuegoRepository;
import edu.alumno.videogames.service.VideojuegosService;
import edu.alumno.videogames.service.mappers.VideojuegoMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VideojuegoServiceImpl implements VideojuegosService {

    private final VideojuegoRepository videojuegoRepository;
    private final PaginationFactory paginationFactory;
    private final PeticionListadoFiltradoConverter peticionConverter;

    @Override
    public VideojuegoEdit create(VideojuegoEdit videojuegoEdit) {
        Videojuego entity = VideojuegoMapper.INSTANCE.videojuegoEditToVideojuego(videojuegoEdit);
        return VideojuegoMapper.INSTANCE.videojuegoToVideojuegoEdit(videojuegoRepository.save(entity));
    }

    @Override
    public VideojuegoInfo read(Long id) {
        Videojuego entity = videojuegoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("VIDEOJUEGO_NOT_FOUND",
                        "El videojuego con ID " + id + " no existe."));
        return VideojuegoMapper.INSTANCE.videojuegoToVideojuegoInfo(entity);
    }

    @Override
    public VideojuegoEdit update(Long id, VideojuegoEdit videojuegoEdit) {
        if (!id.equals(videojuegoEdit.getId())) {
            throw new EntityIllegalArgumentException("VIDEOJUEGO_ID_MISMATCH",
                    "El ID proporcionado no coincide con el ID del videojuego.");
        }
        Videojuego existingEntity = videojuegoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("VIDEOJUEGO_NOT_FOUND_FOR_UPDATE",
                        "No se puede actualizar. El juego con ID " + id + " no existe."));
        VideojuegoMapper.INSTANCE.updateVideojuegoFromVideojuegoEdit(videojuegoEdit, existingEntity);
        return VideojuegoMapper.INSTANCE.videojuegoToVideojuegoEdit(videojuegoRepository.save(existingEntity));
    }

    @Override
    public void delete(Long id) {
        if (videojuegoRepository.existsById(id)) {
            videojuegoRepository.deleteById(id);
        }
    }

    @SuppressWarnings("null")
    @Override
    public PaginaResponse<VideojuegoList> findAll(PeticionListadoFiltrado peticionListadoFiltrado)
            throws FiltroException {
        try {
            Pageable pageable = paginationFactory.createPageable(peticionListadoFiltrado);
            @SuppressWarnings("Convert2Diamond")
            Specification<Videojuego> filtrosBusquedaSpecification = new FiltroBusquedaSpecification<Videojuego>(
                    peticionListadoFiltrado.getListaFiltros());
            Page<Videojuego> page = videojuegoRepository.findAll(filtrosBusquedaSpecification, pageable);
            return VideojuegoMapper.pageToPaginaResponseEtiquetaList(
                    page,
                    peticionListadoFiltrado.getListaFiltros(),
                    peticionListadoFiltrado.getSort());
        } catch (JpaSystemException e) {
            String cause = "";
            if (e.getRootCause() != null) {
                if (e.getCause().getMessage() != null)
                    cause = e.getRootCause().getMessage();
            }
            throw new FiltroException("BAD_OPERATOR_FILTER",
                    "Error: No se puede realizar esa operación sobre el atributo por el tipo de dato",
                    e.getMessage() + ":" + cause);
        } catch (PropertyReferenceException e) {
            throw new FiltroException("BAD_ATTRIBUTE_ORDER",
                    "Error: No existe el nombre del atributo de ordenación en la tabla", e.getMessage());
        } catch (InvalidDataAccessApiUsageException e) {
            throw new FiltroException("BAD_ATTRIBUTE_FILTER", "Error: Posiblemente no existe el atributo en la tabla",
                    e.getMessage());
        }
    }

    @Override
    public PaginaResponse<VideojuegoList> findAll(List<String> filter, int page, int size, List<String> sort)
            throws FiltroException {
        PeticionListadoFiltrado peticion = peticionConverter.convertFromParams(filter, page, size, sort);
        return findAll(peticion);
    }

}