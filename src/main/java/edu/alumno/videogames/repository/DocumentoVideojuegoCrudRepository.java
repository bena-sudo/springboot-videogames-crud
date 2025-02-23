package edu.alumno.videogames.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.alumno.videogames.model.db.DocumentoVideojuegoEditDB;

@Repository
public interface DocumentoVideojuegoCrudRepository extends JpaRepository<DocumentoVideojuegoEditDB, Long> {
}
