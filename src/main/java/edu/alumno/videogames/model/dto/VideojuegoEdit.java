package edu.alumno.videogames.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideojuegoEdit implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(example = "1", description = "Identificador único del videojuego")
    private Long id;

    @NotNull(message = "El nombre del videojuego no puede estar vacío")
    @Schema(example = "Super Mario Odyssey", description = "Nombre del videojuego")
    private String nombre;

    @NotNull(message = "El nombre de la categoria no puede estar vacía")
    @Schema(example = "Aventura", description = "Categoría del videojuego")
    private String categoria;

    @NotNull(message = "El precio del videojuego no puede estar vacío")
    @Schema(example = "59.99", description = "Precio del videojuego")
    private BigDecimal precio;
}
