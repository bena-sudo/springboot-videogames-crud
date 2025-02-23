package edu.alumno.videogames.controllers;

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
import org.springframework.web.bind.annotation.RestController;

import edu.alumno.videogames.enums.IdEntityLong;
import edu.alumno.videogames.helper.BindingResultHelper;
import edu.alumno.videogames.model.dto.CompraEdit;
import edu.alumno.videogames.model.dto.CompraInfo;
import edu.alumno.videogames.service.CrudService;
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
public class CompraController {

    // Preuba para probar CRUD genérico para gestionar operaciones de Compra. Asi
    // solo implementar una unico service para todos los CRUDs
    private final CrudService<CompraEdit, CompraInfo> crudService;

    /**
     * Crea una nueva compra en el sistema.
     *
     * @param compraEdit    Objeto que contiene los datos de la compra a crear.
     * @param bindingResult Resultado de las validaciones de los datos
     *                      proporcionados.
     * @return Un objeto CompraEdit con los datos de la compra creada, junto con el
     *         código HTTP 201 (CREATED).
     */
    @Operation(summary = "Crea una nueva compra en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Compra creada exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CompraEdit.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación en los datos proporcionados", content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/compra/")
    public ResponseEntity<CompraEdit> create(@Valid @RequestBody CompraEdit compraEdit,
            BindingResult bindingResult) {
        BindingResultHelper.validateBindingResult(bindingResult, "COMPRA_CREATE_VALIDATION");
        return ResponseEntity.status(HttpStatus.CREATED).body(crudService.create(compraEdit));
    }

    /**
     * Obtiene la información de una compra a partir de su identificador.
     *
     * @param id Identificador de la compra a buscar.
     * @return Un objeto CompraInfo con los datos de la compra, junto con el código
     *         HTTP 200 (OK).
     */
    @Operation(summary = "Obtiene la información de una compra por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Compra encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CompraInfo.class))),
            @ApiResponse(responseCode = "404", description = "Compra no encontrada", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/compra/{id}")
    public ResponseEntity<CompraInfo> read(@PathVariable String id) {
        return ResponseEntity.ok(crudService.read(new IdEntityLong(id).getValue()));
    }

    /**
     * Actualiza los datos de una compra existente.
     *
     * @param id            Identificador de la compra a actualizar.
     * @param compraEdit    Objeto que contiene los datos actualizados de la compra.
     * @param bindingResult Resultado de las validaciones de los datos
     *                      proporcionados.
     * @return Un objeto CompraEdit con los datos actualizados, junto con el código
     *         HTTP 200 (OK).
     */
    @Operation(summary = "Actualiza los datos de una compra existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Compra actualizada exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CompraEdit.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación en los datos proporcionados", content = @Content(mediaType = "application/json"))
    })
    @PutMapping("/compra/{id}")
    public ResponseEntity<CompraEdit> update(@PathVariable String id, @Valid @RequestBody CompraEdit compraEdit,
            BindingResult bindingResult) {
        BindingResultHelper.validateBindingResult(bindingResult, "COMPRA_UPDATE_VALIDATION");
        return ResponseEntity.ok(crudService.update(new IdEntityLong(id).getValue(), compraEdit));
    }

    /**
     * Elimina una compra del sistema.
     *
     * @param id Identificador de la compra a eliminar.
     * @return Un código HTTP 204 (NO CONTENT) indicando que la eliminación fue
     *         exitosa.
     */
    @Operation(summary = "Elimina una compra del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Compra eliminada exitosamente", content = @Content),
            @ApiResponse(responseCode = "404", description = "Compra no encontrada", content = @Content(mediaType = "application/json"))
    })
    @DeleteMapping("/compra/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        crudService.delete(new IdEntityLong(id).getValue());
        return ResponseEntity.noContent().build();
    }
}
