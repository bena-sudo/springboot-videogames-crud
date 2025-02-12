package edu.alumno.videogames.model.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideojuegoInfo {
    private Long id;
    private String nombre;
    private String categoria;
    private BigDecimal precio;
    private String imagenBase64;
}