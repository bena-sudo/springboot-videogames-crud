package edu.alumno.videogames.model.db;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "videojuegos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Videojuego implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "El nombre del videojuego no puede estar vacío")
    private String nombre;
    @NotNull(message = "El nombre de la categoria no puede estar vacía")
    private String categoria;
    @NotNull(message = "El precio del videojuego no puede estar vacío")
    private BigDecimal precio;
}