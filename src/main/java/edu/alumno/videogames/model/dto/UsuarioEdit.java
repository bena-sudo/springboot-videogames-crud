package edu.alumno.videogames.model.dto;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioEdit implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(example = "Juan Pérez", description = "Nombre completo del usuario")
    private String nombre;

    @Schema(example = "juan.perez@example.com", description = "Correo electrónico del usuario")
    private String email;
}
