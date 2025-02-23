package edu.alumno.videogames.model.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import edu.alumno.videogames.model.db.Usuario;
import edu.alumno.videogames.model.db.Videojuego;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompraEdit implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(example = "1", description = "Identificador único de la compra")
    private Long id;

    @NotNull(message = "El usuario no puede ser nulo")
    @Schema(description = "Objeto usuario asociado a la compra", implementation = Usuario.class)
    private Usuario usuario;

    @NotNull(message = "El videojuego no puede ser nulo")
    @Schema(description = "Objeto videojuego asociado a la compra", implementation = Videojuego.class)
    private Videojuego videojuego;

    @Schema(example = "2023-01-01T12:00:00", description = "Fecha y hora en la que se realizó la compra")
    private LocalDateTime fecha;
}