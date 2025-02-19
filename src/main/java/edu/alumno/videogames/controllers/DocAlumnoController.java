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
import edu.alumno.videogames.model.dto.DocAlumnoEdit;
import edu.alumno.videogames.model.dto.DocAlumnoList;
import edu.alumno.videogames.model.dto.DocAlumnoResponse;
import edu.alumno.videogames.service.DocAlumnoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/docs")
@CrossOrigin
public class DocAlumnoController {

        private final DocAlumnoService docAlumnoService;

        @PostMapping(consumes = "multipart/form-data")
        public ResponseEntity<DocAlumnoResponse> create(@Valid @ModelAttribute DocAlumnoEdit docAlumnoEdit,
                        BindingResult bindingResult) {
                BindingResultHelper.validateBindingResult(bindingResult, "DOC_ALUMNO_CREATE_VALIDATION");
                return ResponseEntity.status(HttpStatus.CREATED).body(docAlumnoService.create(docAlumnoEdit));
        }

        @GetMapping("/{id}")
        public ResponseEntity<DocAlumnoResponse> read(@PathVariable String id) {
                return ResponseEntity.ok(docAlumnoService.read(new IdEntityLong(id).getValue()));
        }

        @GetMapping("/preview/{id}")
        public ResponseEntity<byte[]> previewDocument(@PathVariable String id) {
                return docAlumnoService.getDocumentForPreview(new IdEntityLong(id).getValue());
        }

        @PutMapping("/{id}")
        public ResponseEntity<DocAlumnoResponse> update(@PathVariable String id,
                        @Valid @ModelAttribute DocAlumnoEdit docAlumnoEdit, BindingResult bindingResult) {
                BindingResultHelper.validateBindingResult(bindingResult, "DOC_ALUMNO_UPDATE_VALIDATION");
                return ResponseEntity.ok(docAlumnoService.update(new IdEntityLong(id).getValue(), docAlumnoEdit));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete(@PathVariable String id) {
                docAlumnoService.delete(new IdEntityLong(id).getValue());
                return ResponseEntity.noContent().build();
        }

        @GetMapping("/search")
        public ResponseEntity<PaginaResponse<DocAlumnoList>> getAllDocsGET(
                        @RequestParam(required = false) List<String> filter,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "3") int size,
                        @RequestParam(defaultValue = "id") List<String> sort) throws FiltroException {
                return ResponseEntity.ok(docAlumnoService.findAll(filter, page, size, sort));
        }

        @PostMapping("/search")
        public ResponseEntity<PaginaResponse<DocAlumnoList>> getAllDocsPOST(
                        @Valid @RequestBody PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException {
                return ResponseEntity.ok(docAlumnoService.findAll(peticionListadoFiltrado));
        }
}
