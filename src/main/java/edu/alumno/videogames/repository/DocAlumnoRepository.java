package edu.alumno.videogames.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import edu.alumno.videogames.model.db.DocAlumnoDb;

@Repository
public interface DocAlumnoRepository extends JpaRepository<DocAlumnoDb, Long>, JpaSpecificationExecutor<DocAlumnoDb> {
    @NonNull
    @SuppressWarnings("override")
    Page<DocAlumnoDb> findAll(@NonNull Pageable pageable);
}