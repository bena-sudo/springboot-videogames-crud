package edu.alumno.videogames.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import edu.alumno.videogames.model.db.DocumentoVideojuegoDB;

@Repository
public interface DocumentoVideojuegoRepository extends JpaRepository<DocumentoVideojuegoDB, Long>, JpaSpecificationExecutor<DocumentoVideojuegoDB> {
    @NonNull
    @SuppressWarnings("override")
    Page<DocumentoVideojuegoDB> findAll(@NonNull Pageable pageable);
}