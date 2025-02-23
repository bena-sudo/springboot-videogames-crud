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
import edu.alumno.videogames.model.db.DocumentoVideojuegoDB;
import edu.alumno.videogames.model.db.DocumentoVideojuegoEditDB;
import edu.alumno.videogames.model.dto.DocumentoVideojuegoEdit;
import edu.alumno.videogames.model.dto.DocumentoVideojuegoList;
import edu.alumno.videogames.model.dto.DocumentoVideojuegoResponse;
import edu.alumno.videogames.repository.DocumentoVideojuegoCrudRepository;
import edu.alumno.videogames.repository.DocumentoVideojuegoRepository;
import edu.alumno.videogames.service.DocumentoVideojuegoService;
import edu.alumno.videogames.service.FileDownloadService;
import edu.alumno.videogames.service.mappers.DocumentoVideojuegoMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocumentoVideojuegoServiceImpl implements DocumentoVideojuegoService {

    private final DocumentoVideojuegoCrudRepository documentoVideojuegoCrudRepository;
    private final DocumentoVideojuegoRepository documentoVideojuegoRepository;
    private final FileDownloadService fileDownloadService;
    private final PaginationFactory paginationFactory;
    private final PeticionListadoFiltradoConverter peticionConverter;

    @Override
    public DocumentoVideojuegoResponse create(DocumentoVideojuegoEdit documentoVideojuegoEdit) {
        // Validar que el id y multipart no sean nulos
        if (documentoVideojuegoEdit.getMultipart() == null || documentoVideojuegoEdit.getMultipart().isEmpty()) {
            throw new MultipartProcessingException("BAD_MULTIPART", "El archivo no puede estar vacío");
        }
        if (documentoVideojuegoEdit.getId() != null) { // Como el id lo crea la BD no debemos pasarle un valor inicial
            throw new EntityIllegalArgumentException("VIDEOJUEGO_DOC_ID_MISMATCH",
                    "El ID debe ser nulo al crear un nuevo documento.");
        }

        // Mapear y guardar
        DocumentoVideojuegoEditDB entity = DocumentoVideojuegoMapper.INSTANCE
                .docVideojuegoEditToDocVideojuegoEditDb(documentoVideojuegoEdit);
        DocumentoVideojuegoEditDB savedEntity = documentoVideojuegoCrudRepository.save(entity);

        // Devolver el DTO mapeado
        return DocumentoVideojuegoMapper.INSTANCE.docVideojuegoEditDbToDocVideojuegoResponse(savedEntity);
    }

    @Override
    public DocumentoVideojuegoResponse read(Long id) {
        DocumentoVideojuegoEditDB entity = documentoVideojuegoCrudRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("VIDEOJUEGO_DOC_NOT_FOUND",
                        "No se encontró el documento con ID " + id));
        return DocumentoVideojuegoMapper.INSTANCE.docVideojuegoEditDbToDocVideojuegoResponse(entity);
    }

    @Override
    public ResponseEntity<byte[]> getDocumentForPreview(Long id) {
        DocumentoVideojuegoEditDB doc = documentoVideojuegoCrudRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("VIDEOJUEGO_DOC_NOT_FOUND",
                        "No se encontró el documento con ID " + id));

        return fileDownloadService.prepareDownloadResponse(
                doc.getBase64Documento(),
                doc.getContentTypeDocumento(),
                doc.getNombreFichero());
    }

    @Override
    public DocumentoVideojuegoResponse update(Long id, DocumentoVideojuegoEdit documentoVideojuegoEdit) {
        DocumentoVideojuegoEditDB existingEntity = documentoVideojuegoCrudRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("VIDEOJUEGO_DOC_NOT_FOUND",
                        "No se puede actualizar. El documento con ID " + id + " no existe."));

        DocumentoVideojuegoMapper.INSTANCE.updateDocVideojuegoEditDbFromDocVideojuegoEdit(documentoVideojuegoEdit,
                existingEntity);
        return DocumentoVideojuegoMapper.INSTANCE
                .docVideojuegoEditDbToDocVideojuegoResponse(documentoVideojuegoCrudRepository.save(existingEntity));
    }

    @Override
    public void delete(Long id) {
        if (documentoVideojuegoCrudRepository.existsById(id)) {
            documentoVideojuegoCrudRepository.deleteById(id);
        }
    }

    /**
     * LISTADOS
     */

    @Override
    public PaginaResponse<DocumentoVideojuegoList> findAll(List<String> filter, int page, int size, List<String> sort)
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
    public PaginaResponse<DocumentoVideojuegoList> findAll(PeticionListadoFiltrado peticionListadoFiltrado)
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
            Specification<DocumentoVideojuegoDB> filtrosBusquedaSpecification = new FiltroBusquedaSpecification<DocumentoVideojuegoDB>(
                    peticionListadoFiltrado.getListaFiltros());
            // Filtrar y ordenar: puede producir cualquier de los errores controlados en el
            // catch
            Page<DocumentoVideojuegoDB> page = documentoVideojuegoRepository.findAll(filtrosBusquedaSpecification,
                    pageable);
            // Devolver respuesta
            return DocumentoVideojuegoMapper.pageToPaginaResponseVideojuegoList(
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