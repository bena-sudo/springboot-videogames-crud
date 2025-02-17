package edu.alumno.videogames.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.alumno.videogames.enums.RolNombre;
import edu.alumno.videogames.model.db.Rol;


public interface RolRepository extends JpaRepository<Rol, Integer> {
    Optional<Rol> findByNombre(RolNombre rolNombre);
}
