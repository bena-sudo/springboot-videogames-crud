package edu.alumno.videogames.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
import edu.alumno.videogames.helper.BindingResultHelper;
import edu.alumno.videogames.model.dto.VideojuegoEdit;
import edu.alumno.videogames.model.dto.VideojuegoInfo;
import edu.alumno.videogames.model.dto.VideojuegoList;
import edu.alumno.videogames.service.VideojuegosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/")
@CrossOrigin
public class VideojuegoController {

    private final VideojuegosService videojuegosService;

    /**
     * Crea un nuevo videojuego en el sistema.
     *
     * @param videojuegoEdit Objeto que contiene los datos del videojuego a crear.
     * @param bindingResult  Resultado de las validaciones de los datos
     *                       proporcionados.
     * @return Un objeto VideojuegoEdit con los datos del videojuego creado, junto
     *         con el código HTTP 201 (CREATED).
     */
    @Operation(summary = "Crea un nuevo videojuego", description = "Crea un videojuego en el sistema utilizando los datos proporcionados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Videojuego creado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = VideojuegoEdit.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación en los datos proporcionados", content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/videojuego")
    public ResponseEntity<VideojuegoEdit> create(@Valid @RequestBody VideojuegoEdit videojuegoEdit,
            BindingResult bindingResult) {
        BindingResultHelper.validateBindingResult(bindingResult, "VIDEOJUEGO_CREATE_VALIDATION");
        return ResponseEntity.status(HttpStatus.CREATED).body(videojuegosService.create(videojuegoEdit));
    }

    /**
     * Obtiene la información de un videojuego a partir de su ID.
     *
     * @param id Identificador del videojuego a buscar.
     * @return Un objeto VideojuegoInfo con los datos del videojuego y el código
     *         HTTP 200 (OK).
     */
    @Operation(summary = "Obtiene la información de un videojuego", description = "Recupera los detalles de un videojuego por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Videojuego encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = VideojuegoInfo.class))),
            @ApiResponse(responseCode = "404", description = "Videojuego no encontrado", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/videojuego/{id}")
    public ResponseEntity<VideojuegoInfo> read(@PathVariable String id) {
        return ResponseEntity.ok(videojuegosService.read(new IdEntityLong(id).getValue()));
    }

    /**
     * Actualiza los datos de un videojuego existente.
     *
     * @param id             Identificador del videojuego a actualizar.
     * @param videojuegoEdit Objeto que contiene los datos actualizados del
     *                       videojuego.
     * @param bindingResult  Resultado de las validaciones de los datos
     *                       proporcionados.
     * @return Un objeto VideojuegoEdit con los datos actualizados y el código HTTP
     *         200 (OK).
     */
    @Operation(summary = "Actualiza un videojuego", description = "Actualiza la información de un videojuego existente utilizando el ID proporcionado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Videojuego actualizado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = VideojuegoEdit.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación en los datos proporcionados", content = @Content(mediaType = "application/json"))
    })
    @PutMapping("/videojuego/{id}")
    public ResponseEntity<VideojuegoEdit> update(@PathVariable String id,
            @Valid @RequestBody VideojuegoEdit videojuegoEdit,
            BindingResult bindingResult) {
        BindingResultHelper.validateBindingResult(bindingResult, "VIDEOJUEGO_UPDATE_VALIDATION");
        return ResponseEntity.ok(videojuegosService.update(new IdEntityLong(id).getValue(), videojuegoEdit));
    }

    /**
     * Elimina un videojuego del sistema.
     *
     * @param id Identificador del videojuego a eliminar.
     * @return Un código HTTP 204 (NO CONTENT) indicando que la eliminación fue
     *         exitosa.
     */
    @Operation(summary = "Elimina un videojuego", description = "Elimina un videojuego del sistema utilizando su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Videojuego eliminado exitosamente", content = @Content),
            @ApiResponse(responseCode = "404", description = "Videojuego no encontrado", content = @Content(mediaType = "application/json"))
    })
    @DeleteMapping("/videojuego/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        videojuegosService.delete(new IdEntityLong(id).getValue());
        return ResponseEntity.noContent().build();
    }

    /**
     * Obtiene una lista paginada de videojuegos aplicando filtros, paginación y
     * ordenación.
     *
     * @param filter Lista de filtros opcionales.
     * @param page   Número de página a obtener.
     * @param size   Tamaño de la página.
     * @param sort   Criterios de ordenación.
     * @return Un objeto PaginaResponse con la lista de videojuegos que cumplen los
     *         criterios y el código HTTP 200 (OK).
     * @throws FiltroException En caso de que los filtros aplicados no sean válidos.
     */
    @Operation(summary = "Obtiene una lista de videojuegos", description = "Recupera una lista paginada de videojuegos aplicando filtros, paginación y ordenación.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de videojuegos obtenida exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaginaResponse.class))),
            @ApiResponse(responseCode = "400", description = "Error en los filtros o parámetros proporcionados", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/videojuegos")
    public ResponseEntity<PaginaResponse<VideojuegoList>> getAll(
            @RequestParam(required = false) List<String> filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") List<String> sort) throws FiltroException {
        return ResponseEntity.ok(videojuegosService.findAll(filter, page, size, sort));
    }
}
