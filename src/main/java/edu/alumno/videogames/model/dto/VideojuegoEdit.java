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
    @NotNull
    private String nombre;
    @NotNull
    private String categoria;
    @NotNull
    private BigDecimal precio;
    @NotNull
    private String imagenBase64;
}