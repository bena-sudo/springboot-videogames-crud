package edu.alumno.videogames.model.dto;

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
public class CompraEdit {
    private Long id;
    @NotNull
    private Usuario usuario;
    @NotNull
    private Videojuego videojuego;
    @NotNull
    private LocalDateTime fecha;
}