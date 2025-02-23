package edu.alumno.videogames.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.alumno.videogames.enums.IdEntityLong;
import edu.alumno.videogames.exception.FiltroException;
import edu.alumno.videogames.filters.model.PaginaResponse;
import edu.alumno.videogames.filters.model.PeticionListadoFiltrado;
import edu.alumno.videogames.helper.BindingResultHelper;
import edu.alumno.videogames.model.dto.DocumentoVideojuegoEdit;
import edu.alumno.videogames.model.dto.DocumentoVideojuegoList;
import edu.alumno.videogames.model.dto.DocumentoVideojuegoResponse;
import edu.alumno.videogames.service.DocumentoVideojuegoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/docs")
@CrossOrigin
public class DocumentoVideojuegoController {

        private final DocumentoVideojuegoService documentoVideojuegoService;

        /**
         * Crea un nuevo documento asociado a un videojuego en el sistema.
         *
         * Este endpoint recibe datos en formato multipart/form-data a través
         * de @ModelAttribute.
         *
         * @param documentoVideojuegoEdit Objeto que contiene los datos del documento
         *                                del videojuego a crear.
         * @param bindingResult           Resultado de las validaciones de los datos
         *                                proporcionados.
         * @return Un objeto DocumentoVideojuegoResponse con los datos del documento
         *         creado, junto con el código HTTP 201 (CREATED).
         */
        @Operation(summary = "Crea un nuevo documento de videojuego", description = "Recibe datos en formato multipart/form-data para crear un documento asociado a un videojuego.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Documento de videojuego creado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DocumentoVideojuegoResponse.class))),
                        @ApiResponse(responseCode = "400", description = "Error de validación en los datos proporcionados", content = @Content(mediaType = "application/json"))
        })
        @PostMapping(consumes = "multipart/form-data")
        public ResponseEntity<DocumentoVideojuegoResponse> create(
                        @Valid @ModelAttribute DocumentoVideojuegoEdit documentoVideojuegoEdit,
                        BindingResult bindingResult) {
                BindingResultHelper.validateBindingResult(bindingResult, "DOC_VIDEOJUEGO_CREATE_VALIDATION");
                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(documentoVideojuegoService.create(documentoVideojuegoEdit));
        }

        /**
         * Obtiene la respuesta de un documento de videojuego a partir de su ID.
         *
         * @param id Identificador del documento a buscar.
         * @return Un objeto DocumentoVideojuegoResponse con los datos del documento y
         *         el código HTTP 200 (OK).
         */
        @Operation(summary = "Obtiene los detalles de un documento de videojuego por su ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Documento encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DocumentoVideojuegoResponse.class))),
                        @ApiResponse(responseCode = "404", description = "Documento no encontrado", content = @Content(mediaType = "application/json"))
        })
        @GetMapping("/{id}")
        public ResponseEntity<DocumentoVideojuegoResponse> read(@PathVariable String id) {
                return ResponseEntity.ok(documentoVideojuegoService.read(new IdEntityLong(id).getValue()));
        }

        /**
         * Obtiene una vista previa del documento de videojuego en formato de arreglo de
         * bytes.
         *
         * @param id Identificador del documento a previsualizar.
         * @return Un arreglo de bytes con el contenido del documento para
         *         previsualización, junto con el código HTTP 200 (OK).
         */
        @Operation(summary = "Obtiene la previsualización de un documento de videojuego")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Previsualización obtenida exitosamente", content = @Content(mediaType = "application/octet-stream")),
                        @ApiResponse(responseCode = "404", description = "Documento no encontrado", content = @Content(mediaType = "application/json"))
        })
        @GetMapping("/preview/{id}")
        public ResponseEntity<byte[]> previewDocument(@PathVariable String id) {
                return documentoVideojuegoService.getDocumentForPreview(new IdEntityLong(id).getValue());
        }

        /**
         * Actualiza los datos de un documento de videojuego existente.
         *
         * @param id                      Identificador del documento a actualizar.
         * @param documentoVideojuegoEdit Objeto que contiene los datos actualizados del
         *                                documento.
         * @param bindingResult           Resultado de las validaciones de los datos
         *                                proporcionados.
         * @return Un objeto DocumentoVideojuegoResponse con los datos actualizados y el
         *         código HTTP 200 (OK).
         */
        @Operation(summary = "Actualiza un documento de videojuego existente")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Documento actualizado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DocumentoVideojuegoResponse.class))),
                        @ApiResponse(responseCode = "400", description = "Error de validación en los datos proporcionados", content = @Content(mediaType = "application/json"))
        })
        @PutMapping("/{id}")
        public ResponseEntity<DocumentoVideojuegoResponse> update(@PathVariable String id,
                        @Valid @ModelAttribute DocumentoVideojuegoEdit documentoVideojuegoEdit,
                        BindingResult bindingResult) {
                BindingResultHelper.validateBindingResult(bindingResult, "DOC_VIDEOJUEGO_UPDATE_VALIDATION");
                return ResponseEntity.ok(documentoVideojuegoService.update(new IdEntityLong(id).getValue(),
                                documentoVideojuegoEdit));
        }

        /**
         * Elimina un documento de videojuego del sistema.
         *
         * @param id Identificador del documento a eliminar.
         * @return Un código HTTP 204 (NO CONTENT) indicando que la eliminación fue
         *         exitosa.
         */
        @Operation(summary = "Elimina un documento de videojuego")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "204", description = "Documento eliminado exitosamente", content = @Content),
                        @ApiResponse(responseCode = "404", description = "Documento no encontrado", content = @Content(mediaType = "application/json"))
        })
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete(@PathVariable String id) {
                documentoVideojuegoService.delete(new IdEntityLong(id).getValue());
                return ResponseEntity.noContent().build();
        }

        /**
         * Obtiene una lista paginada de documentos de videojuego aplicando filtros
         * mediante parámetros en la URL.
         *
         * @param filter Lista de filtros opcionales.
         * @param page   Número de página a obtener.
         * @param size   Tamaño de la página.
         * @param sort   Criterios de ordenación.
         * @return Un objeto PaginaResponse con la lista de documentos que cumplen los
         *         criterios y el código HTTP 200 (OK).
         * @throws FiltroException En caso de que los filtros aplicados no sean válidos.
         */
        @Operation(summary = "Obtiene una lista paginada de documentos de videojuego (método GET)", description = "Permite filtrar, paginar y ordenar la lista de documentos a través de parámetros en la URL.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Lista de documentos obtenida exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaginaResponse.class))),
                        @ApiResponse(responseCode = "400", description = "Error en los filtros o parámetros proporcionados", content = @Content(mediaType = "application/json"))
        })
        @GetMapping("/search")
        public ResponseEntity<PaginaResponse<DocumentoVideojuegoList>> getAllDocsGET(
                        @RequestParam(required = false) List<String> filter,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "3") int size,
                        @RequestParam(defaultValue = "id") List<String> sort) throws FiltroException {
                return ResponseEntity.ok(documentoVideojuegoService.findAll(filter, page, size, sort));
        }

        /**
         * Obtiene una lista paginada de documentos de videojuego aplicando filtros
         * mediante un objeto de petición.
         *
         * @param peticionListadoFiltrado Objeto que contiene los criterios de filtrado
         *                                y paginación.
         * @return Un objeto PaginaResponse con la lista de documentos que cumplen los
         *         criterios y el código HTTP 200 (OK).
         * @throws FiltroException En caso de que los filtros aplicados no sean válidos.
         */
        @Operation(summary = "Obtiene una lista paginada de documentos de videojuego (método POST)", description = "Permite obtener la lista de documentos aplicando filtros y paginación a través de un objeto de petición.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Lista de documentos obtenida exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaginaResponse.class))),
                        @ApiResponse(responseCode = "400", description = "Error en los filtros o datos proporcionados", content = @Content(mediaType = "application/json"))
        })
        @PostMapping("/search")
        public ResponseEntity<PaginaResponse<DocumentoVideojuegoList>> getAllDocsPOST(
                        @Valid @RequestBody PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException {
                return ResponseEntity.ok(documentoVideojuegoService.findAll(peticionListadoFiltrado));
        }
}
