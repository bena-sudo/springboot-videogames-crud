package edu.alumno.videogames.model.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import edu.alumno.videogames.model.db.Usuario;
import edu.alumno.videogames.model.db.Videojuego;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompraEdit implements Serializable{
    private static final long serialVersionUID = 1L;
    private Long id;
    @NotNull
    private Usuario usuario;
    @NotNull
    private Videojuego videojuego;
    private LocalDateTime fecha;
}