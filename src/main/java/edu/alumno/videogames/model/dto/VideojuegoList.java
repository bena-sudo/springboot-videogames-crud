package edu.alumno.videogames.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideojuegoList implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String nombre;
    private String categoria;
    private BigDecimal precio;
}