package edu.alumno.videogames.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.alumno.videogames.helper.BindingResultHelper;
import edu.alumno.videogames.model.IdEntityLong;
import edu.alumno.videogames.model.dto.CompraEdit;
import edu.alumno.videogames.model.dto.CompraInfo;
import edu.alumno.videogames.service.CrudService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/")
public class CompraController {
    private final CrudService<CompraEdit, CompraInfo> crudService;

    @PostMapping("/compra/")
    public ResponseEntity<CompraEdit> create(@Valid @RequestBody CompraEdit compraEdit,
            BindingResult bindingResult) {
        BindingResultHelper.validateBindingResult(bindingResult, "COMPRA_CREATE_VALIDATION");
        return ResponseEntity.status(HttpStatus.CREATED).body(crudService.create(compraEdit));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/compra/{id}")
    public ResponseEntity<CompraInfo> read(@PathVariable String id) {
        return ResponseEntity.ok(crudService.read(new IdEntityLong(id).getValue()));
    }

    @PutMapping("/compra/{id}")
    public ResponseEntity<CompraEdit> update(@PathVariable String id, @Valid @RequestBody CompraEdit compraEdit,
            BindingResult bindingResult) {
        BindingResultHelper.validateBindingResult(bindingResult, " COMPRA_UPDATE_VALIDATION");
        return ResponseEntity.ok(crudService.update(new IdEntityLong(id).getValue(), compraEdit));
    }

    @DeleteMapping("/compra/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        crudService.delete(new IdEntityLong(id).getValue());
        return ResponseEntity.noContent().build();
    }
}
