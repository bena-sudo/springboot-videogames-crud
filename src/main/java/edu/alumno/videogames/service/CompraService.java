package edu.alumno.videogames.service;

import edu.alumno.videogames.model.dto.CompraEdit;
import edu.alumno.videogames.model.dto.CompraInfo;

public interface CompraService {
    CompraEdit create(CompraEdit compraEdit);

    CompraInfo read(Long id);

    CompraEdit update(Long id, CompraEdit compraEdit);

    void delete(Long id);
}
