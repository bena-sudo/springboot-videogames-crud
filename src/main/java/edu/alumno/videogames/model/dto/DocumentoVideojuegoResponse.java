package edu.alumno.videogames.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DocumentoVideojuegoResponse {
    @Schema(example = "1", description = "Identificador único del documento")
    private Long id;

    private Long idVideojuego;

    @Size(min = 5, message = "El nombre del fichero debe tener un tamaño mínimo de 5 caracteres")
    @Size(max = 255, message = "El nombre del fichero debe tener un tamaño máximo de 255 caracteres")
    @Schema(example = "DocumentoImportante.pdf", description = "Nombre del fichero entre 5 y 255 caracteres")
    private String nombreFichero;

    @Schema(example = "Comentarios adicionales sobre el documento", description = "Descripción adicional del documento")
    private String comentario;

    @Schema(example = "JVBERi0xLjcKJcOkw7zDtsO...", description = "Archivo en Base64 (solo para lectura)")
    public String base64Documento;

    @NotNull(message = "La extensión del fichero del documento no puede estar vacio")
    @Schema(example = "pdf", description = "Extensión del fichero del documento")
    private String extensionDocumento;

    @NotNull(message = "El content_type del fichero del documento no puede estar vacio")
    @Schema(example = "application/pdf", description = "Tipo MIME del fichero del documento")
    private String contentTypeDocumento;
}
