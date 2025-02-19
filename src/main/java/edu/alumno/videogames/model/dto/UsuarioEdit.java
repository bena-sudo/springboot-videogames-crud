package edu.alumno.videogames.model.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioEdit implements Serializable{
    private static final long serialVersionUID = 1L;
    private String nombre;
    private String email;
}