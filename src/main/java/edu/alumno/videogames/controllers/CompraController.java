package edu.alumno.videogames.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.alumno.videogames.model.IdEntityLong;
import edu.alumno.videogames.model.dto.CompraEdit;
import edu.alumno.videogames.model.dto.CompraInfo;
import edu.alumno.videogames.service.CrudService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/")
public class CompraController {
    private final CrudService<CompraEdit, CompraInfo> crudService;

    @GetMapping("/compra/{id}")
    public ResponseEntity<CompraInfo> read(@PathVariable String id) {
        return ResponseEntity.ok(crudService.read(new IdEntityLong(id).getValue()));
    }
}
