package edu.alumno.videogames.service.imp;

import org.springframework.stereotype.Service;

import edu.alumno.videogames.model.dto.VideojuegoEdit;
import edu.alumno.videogames.model.dto.VideojuegoInfo;
import edu.alumno.videogames.repository.VideojuegoRepository;
import edu.alumno.videogames.service.VideojuegosService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VideojuegoServiceImpl implements VideojuegosService {

    @SuppressWarnings("unused")
    private VideojuegoRepository videojuegoRepository;

    @Override
    public VideojuegoEdit create(VideojuegoEdit entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public VideojuegoInfo read(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'read'");
    }

    @Override
    public VideojuegoEdit update(Long id, VideojuegoEdit entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

}