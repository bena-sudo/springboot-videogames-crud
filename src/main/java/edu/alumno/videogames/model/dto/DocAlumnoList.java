package edu.alumno.videogames.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DocAlumnoList {
    @Schema(example = "1", description = "Identificador único del documento")
    private Long id;

    private Long idVideojuego;

    @Size(min = 5, message = "El nombre del fichero debe tener un tamaño mínimo de 5 caracteres")
    @Size(max = 255, message = "El nombre del fichero debe tener un tamaño máximo de 255 caracteres")
    @Schema(example = "DocumentoImportante.pdf", description = "Nombre del fichero entre 5 y 255 caracteres")
    private String nombreFichero;
}
