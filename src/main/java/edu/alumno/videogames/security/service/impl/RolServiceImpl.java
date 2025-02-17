package edu.alumno.videogames.security.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.alumno.videogames.enums.RolNombre;
import edu.alumno.videogames.model.db.Rol;
import edu.alumno.videogames.repository.RolRepository;
import edu.alumno.videogames.security.service.RolService;


@Service
@Transactional
public class RolServiceImpl implements RolService {

    @Autowired
    RolRepository rolRepository;

    @SuppressWarnings("override")
    public Optional<Rol> getByRolNombre(RolNombre rolNombre){
        return rolRepository.findByNombre(rolNombre);
    }

    @SuppressWarnings("override")
    public void save(@NonNull Rol rol){
        rolRepository.save(rol);
    }
}


