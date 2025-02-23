package edu.alumno.videogames.model.dto;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DocumentoVideojuegoEdit {
    @Schema(example = "1", description = "Identificador único del documento")
    private Long id;

    private Long idVideojuego;

    @Size(min = 5, message = "El nombre del fichero debe tener un tamaño mínimo de 5 caracteres")
    @Size(max = 255, message = "El nombre del fichero debe tener un tamaño máximo de 255 caracteres")
    @Schema(example = "DocumentoImportante.pdf", description = "Nombre del fichero entre 5 y 255 caracteres")
    private String nombreFichero;

    @Schema(example = "Comentarios adicionales sobre el documento", description = "Descripción adicional del documento")
    private String comentario;

    // Campo para el archivo (no se persiste directamente en la base de datos. Debe
    // pasarse a base64)
    @Schema(example = "Selecciona el archivo...", description = "Archivo del documento en formato multipart")
    private MultipartFile multipart;
}
