package edu.alumno.videogames.service.imp;

import java.util.List;

import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

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
    public VideojuegoEdit create(VideojuegoEdit entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public VideojuegoInfo read(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'read'");
    }

    @Override
    public VideojuegoEdit update(Long id, VideojuegoEdit entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @SuppressWarnings("null")
    @Override
    public PaginaResponse<VideojuegoList> findAll(PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException {
        try {
            Pageable pageable = paginationFactory.createPageable(peticionListadoFiltrado);
            // Configurar criterio de filtrado con Specification
            @SuppressWarnings("Convert2Diamond")
            Specification<Videojuego> filtrosBusquedaSpecification = new FiltroBusquedaSpecification<Videojuego>(
                    peticionListadoFiltrado.getListaFiltros());
            // Filtrar y ordenar: puede producir cualquier de los errores controlados en el
            // catch
            Page<Videojuego> page = videojuegoRepository.findAll(filtrosBusquedaSpecification, pageable);
            // Devolver respuesta
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
    public PaginaResponse<VideojuegoList> findAll(List<String> filter, int page, int size, List<String> sort) throws FiltroException {
        PeticionListadoFiltrado peticion = peticionConverter.convertFromParams(filter, page, size, sort);
        return findAll(peticion);
    }

}