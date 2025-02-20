package edu.alumno.videogames.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideojuegoEdit implements Serializable{
    private static final long serialVersionUID = 1L;
    private Long id;
    @NotNull(message = "El nombre del videojuego no puede estar vacío")
    private String nombre;
    @NotNull(message = "El nombre de la categoria no puede estar vacía")
    private String categoria;
    @NotNull(message = "El precio del videojuego no puede estar vacío")
    private BigDecimal precio;
}