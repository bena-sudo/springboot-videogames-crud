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
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/")
@CrossOrigin
public class VideojuegoController {

    private final VideojuegosService videojuegosService;

    @PostMapping("/videojuego")
    public ResponseEntity<VideojuegoEdit> create(@Valid @RequestBody VideojuegoEdit videojuegoEdit,
            BindingResult bindingResult) {
        BindingResultHelper.validateBindingResult(bindingResult, "VIDEOJUEGO_CREATE_VALIDATION");
        return ResponseEntity.status(HttpStatus.CREATED).body(videojuegosService.create(videojuegoEdit));
    }

    @GetMapping("/videojuego/{id}")
    public ResponseEntity<VideojuegoInfo> read(@PathVariable String id) {
        return ResponseEntity.ok(videojuegosService.read(new IdEntityLong(id).getValue()));
    }

    @PutMapping("/videojuego/{id}")
    public ResponseEntity<VideojuegoEdit> update(@PathVariable String id,
            @Valid @RequestBody VideojuegoEdit videojuegoEdit,
            BindingResult bindingResult) {
        BindingResultHelper.validateBindingResult(bindingResult, "VIDEOJUEGO_UPDATE_VALIDATION");
        return ResponseEntity.ok(videojuegosService.update(new IdEntityLong(id).getValue(), videojuegoEdit));
    }

    @DeleteMapping("/videojuego/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        videojuegosService.delete(new IdEntityLong(id).getValue());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/videojuegos")
    public ResponseEntity<PaginaResponse<VideojuegoList>> getAll(
            @RequestParam(required = false) List<String> filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") List<String> sort) throws FiltroException {
        return ResponseEntity.ok(videojuegosService.findAll(filter, page, size, sort));
    }
}
