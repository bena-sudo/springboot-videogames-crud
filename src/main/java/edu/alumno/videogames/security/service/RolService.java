package edu.alumno.videogames.security.service;

import java.util.Optional;

import org.springframework.lang.NonNull;

import edu.alumno.videogames.enums.RolNombre;
import edu.alumno.videogames.model.db.Rol;



public interface RolService {
    public Optional<Rol> getByRolNombre(RolNombre rolNombre);

    public void save(@NonNull Rol rol);
}
