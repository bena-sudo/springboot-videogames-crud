package edu.alumno.videogames.service.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import edu.alumno.videogames.model.db.Compra;
import edu.alumno.videogames.model.dto.CompraEdit;
import edu.alumno.videogames.model.dto.CompraInfo;

@Mapper
public interface CompraMapper {
    CompraMapper INSTANCE = Mappers.getMapper(CompraMapper.class);

    Compra compraToCompraEdit(CompraEdit compraEdit);

    CompraEdit compraEditToCompra(Compra compra);

    CompraInfo compraToCompraInfo(Compra compra);

    void updateCompraFromCompraEdit(CompraEdit compraEdit, @MappingTarget Compra compra);
}