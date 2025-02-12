package edu.alumno.videogames.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import edu.alumno.videogames.model.db.Videojuego;

@Repository
public interface VideojuegoRepository extends JpaRepository<Videojuego, Long>, JpaSpecificationExecutor<Videojuego> {
}