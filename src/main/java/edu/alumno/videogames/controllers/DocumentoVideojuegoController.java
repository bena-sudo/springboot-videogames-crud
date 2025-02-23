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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/docs")
@CrossOrigin
public class DocumentoVideojuegoController {

        private final DocumentoVideojuegoService documentoVideojuegoService;

        @PostMapping(consumes = "multipart/form-data")
        public ResponseEntity<DocumentoVideojuegoResponse> create(
                        @Valid @ModelAttribute DocumentoVideojuegoEdit documentoVideojuegoEdit,
                        BindingResult bindingResult) {
                BindingResultHelper.validateBindingResult(bindingResult, "DOC_VIDEOJUEGO_CREATE_VALIDATION");
                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(documentoVideojuegoService.create(documentoVideojuegoEdit));
        }

        @GetMapping("/{id}")
        public ResponseEntity<DocumentoVideojuegoResponse> read(@PathVariable String id) {
                return ResponseEntity.ok(documentoVideojuegoService.read(new IdEntityLong(id).getValue()));
        }

        @GetMapping("/preview/{id}")
        public ResponseEntity<byte[]> previewDocument(@PathVariable String id) {
                return documentoVideojuegoService.getDocumentForPreview(new IdEntityLong(id).getValue());
        }

        @PutMapping("/{id}")
        public ResponseEntity<DocumentoVideojuegoResponse> update(@PathVariable String id,
                        @Valid @ModelAttribute DocumentoVideojuegoEdit documentoVideojuegoEdit,
                        BindingResult bindingResult) {
                BindingResultHelper.validateBindingResult(bindingResult, "DOC_VIDEOJUEGO_UPDATE_VALIDATION");
                return ResponseEntity.ok(documentoVideojuegoService.update(new IdEntityLong(id).getValue(),
                                documentoVideojuegoEdit));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete(@PathVariable String id) {
                documentoVideojuegoService.delete(new IdEntityLong(id).getValue());
                return ResponseEntity.noContent().build();
        }

        @GetMapping("/search")
        public ResponseEntity<PaginaResponse<DocumentoVideojuegoList>> getAllDocsGET(
                        @RequestParam(required = false) List<String> filter,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "3") int size,
                        @RequestParam(defaultValue = "id") List<String> sort) throws FiltroException {
                return ResponseEntity.ok(documentoVideojuegoService.findAll(filter, page, size, sort));
        }

        @PostMapping("/search")
        public ResponseEntity<PaginaResponse<DocumentoVideojuegoList>> getAllDocsPOST(
                        @Valid @RequestBody PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException {
                return ResponseEntity.ok(documentoVideojuegoService.findAll(peticionListadoFiltrado));
        }
}
