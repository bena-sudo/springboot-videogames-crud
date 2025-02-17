package edu.alumno.videogames.model.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import edu.alumno.videogames.model.db.Usuario;
import edu.alumno.videogames.model.db.Videojuego;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompraInfo implements Serializable{
    private static final long serialVersionUID = 1L;
    private Long id;
    private Usuario usuario;
    private Videojuego videojuego;
    private LocalDateTime fecha;
}