package edu.alumno.videogames.service.imp;

import java.util.List;

import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import edu.alumno.videogames.exception.EntityIllegalArgumentException;
import edu.alumno.videogames.exception.EntityNotFoundException;
import edu.alumno.videogames.exception.FiltroException;
import edu.alumno.videogames.exception.responseHelpers.MultipartProcessingException;
import edu.alumno.videogames.filters.model.PaginaResponse;
import edu.alumno.videogames.filters.model.PeticionListadoFiltrado;
import edu.alumno.videogames.filters.specification.FiltroBusquedaSpecification;
import edu.alumno.videogames.filters.utils.PaginationFactory;
import edu.alumno.videogames.filters.utils.PeticionListadoFiltradoConverter;
import edu.alumno.videogames.model.db.DocAlumnoDb;
import edu.alumno.videogames.model.db.DocAlumnoEditDb;
import edu.alumno.videogames.model.dto.DocAlumnoEdit;
import edu.alumno.videogames.model.dto.DocAlumnoList;
import edu.alumno.videogames.model.dto.DocAlumnoResponse;
import edu.alumno.videogames.repository.DocAlumnoCrudRepository;
import edu.alumno.videogames.repository.DocAlumnoRepository;
import edu.alumno.videogames.service.DocAlumnoService;
import edu.alumno.videogames.service.FileDownloadService;
import edu.alumno.videogames.service.mappers.DocAlumnoMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocAlumnoServiceImpl implements DocAlumnoService {

    private final DocAlumnoCrudRepository docAlumnoCrudRepository;
    private final DocAlumnoRepository docAlumnoRepository;
    private final FileDownloadService fileDownloadService;
    private final PaginationFactory paginationFactory;
    private final PeticionListadoFiltradoConverter peticionConverter;

    @Override
    public DocAlumnoResponse create(DocAlumnoEdit docAlumnoEdit) {
        // Validar que el id y multipart no sean nulos
        if (docAlumnoEdit.getMultipart() == null || docAlumnoEdit.getMultipart().isEmpty()) {
            throw new MultipartProcessingException("BAD_MULTIPART", "El archivo no puede estar vacío");
        }
        if (docAlumnoEdit.getId() != null) { // Como el id lo crea la BD no debemos pasarle un valor inicial
            throw new EntityIllegalArgumentException("STUDENT_DOC_ID_MISMATCH",
                    "El ID debe ser nulo al crear un nuevo documento.");
        }

        // Mapear y guardar
        DocAlumnoEditDb entity = DocAlumnoMapper.INSTANCE.docAlumnoEditToDocAlumnoEditDb(docAlumnoEdit);
        DocAlumnoEditDb savedEntity = docAlumnoCrudRepository.save(entity);

        // Devolver el DTO mapeado
        return DocAlumnoMapper.INSTANCE.docAlumnoEditDbToDocAlumnoResponse(savedEntity);
    }

    @Override
    public DocAlumnoResponse read(Long id) {
        DocAlumnoEditDb entity = docAlumnoCrudRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("STUDENT_DOC_NOT_FOUND",
                        "No se encontró el documento con ID " + id));
        return DocAlumnoMapper.INSTANCE.docAlumnoEditDbToDocAlumnoResponse(entity);
    }

    @Override
    public ResponseEntity<byte[]> getDocumentForPreview(Long id) {
        DocAlumnoEditDb doc = docAlumnoCrudRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("STUDENT_DOC_NOT_FOUND",
                        "No se encontró el documento con ID " + id));

        return fileDownloadService.prepareDownloadResponse(
                doc.getBase64Documento(),
                doc.getContentTypeDocumento(),
                doc.getNombreFichero());
    }

    @Override
    public DocAlumnoResponse update(Long id, DocAlumnoEdit docAlumnoEdit) {
        DocAlumnoEditDb existingEntity = docAlumnoCrudRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("STUDENT_DOC_NOT_FOUND",
                        "No se puede actualizar. El documento con ID " + id + " no existe."));

        DocAlumnoMapper.INSTANCE.updateDocAlumnoEditDbFromDocAlumnoEdit(docAlumnoEdit, existingEntity);
        return DocAlumnoMapper.INSTANCE
                .docAlumnoEditDbToDocAlumnoResponse(docAlumnoCrudRepository.save(existingEntity));
    }

    @Override
    public void delete(Long id) {
        if (docAlumnoCrudRepository.existsById(id)) {
            docAlumnoCrudRepository.deleteById(id);
        }
    }

    /**
     * LISTADOS
     */

    @Override
    public PaginaResponse<DocAlumnoList> findAll(List<String> filter, int page, int size, List<String> sort)
            throws FiltroException {
        /**
         * 'peticionConverter' está en el constructor del service porque utilizando una
         * buena arquitectura
         * toda clase externa al Service que contenga un método a ejecutar debería ser
         * testeable de manera
         * independiente y para ello debe de estar en el constructor para poderse
         * mockear.
         **/
        PeticionListadoFiltrado peticion = peticionConverter.convertFromParams(filter, page, size, sort);
        return findAll(peticion);
    }

    @SuppressWarnings("null")
    @Override
    public PaginaResponse<DocAlumnoList> findAll(PeticionListadoFiltrado peticionListadoFiltrado)
            throws FiltroException {
        /**
         * 'paginationFactory' está en el constructor del service porque utilizando una
         * buena arquitectura
         * toda clase externa al Service que contenga un método a ejecutar debería ser
         * testeable de manera
         * independiente y para ello debe de estar en el constructor para poderse
         * mockear.
         **/
        try {
            Pageable pageable = paginationFactory.createPageable(peticionListadoFiltrado);
            // Configurar criterio de filtrado con Specification
            @SuppressWarnings("Convert2Diamond")
            Specification<DocAlumnoDb> filtrosBusquedaSpecification = new FiltroBusquedaSpecification<DocAlumnoDb>(
                    peticionListadoFiltrado.getListaFiltros());
            // Filtrar y ordenar: puede producir cualquier de los errores controlados en el
            // catch
            Page<DocAlumnoDb> page = docAlumnoRepository.findAll(filtrosBusquedaSpecification, pageable);
            // Devolver respuesta
            return DocAlumnoMapper.pageToPaginaResponseAlumnoList(
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

}