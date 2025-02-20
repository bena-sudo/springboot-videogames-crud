package edu.alumno.videogames.service.imp;

import org.springframework.stereotype.Service;

import edu.alumno.videogames.exception.EntityIllegalArgumentException;
import edu.alumno.videogames.exception.EntityNotFoundException;
import edu.alumno.videogames.model.db.Compra;
import edu.alumno.videogames.model.dto.CompraEdit;
import edu.alumno.videogames.model.dto.CompraInfo;
import edu.alumno.videogames.repository.CompraRepository;
import edu.alumno.videogames.service.CrudService;
import edu.alumno.videogames.service.mappers.CompraMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompraServiceImp implements CrudService<CompraEdit, CompraInfo> {

    private final CompraRepository compraRepository;

    @Override
    public CompraEdit create(CompraEdit entity) {
        if (entity.getId() != null) {
            throw new EntityIllegalArgumentException("COMPRA_ID_MISMATCH",
                    "El ID debe ser nulo al crear una nueva etiqueta.");
        }
        Compra compra = CompraMapper.INSTANCE.compraToCompraEdit(entity);
        return CompraMapper.INSTANCE.compraEditToCompra(compraRepository.save(compra));
    }

    @Override
    public CompraInfo read(Long id) {
        Compra entity = compraRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("COMPRA_NOT_FOUND",
                        "La compra con ID " + id + " no existe."));
        return CompraMapper.INSTANCE.compraToCompraInfo(entity);
    }

    @Override
    public CompraEdit update(Long id, CompraEdit entity) {
        if (!id.equals(entity.getId())) {
            throw new EntityIllegalArgumentException("COMPRA_ID_MISMATCH",
                    "El ID proporcionado no coincide con el ID de la compra.");
        }
        Compra existingEntity = compraRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("COMPRA_NOT_FOUND_FOR_UPDATE",
                        "No se puede actualizar. La compra con ID " + id + " no existe."));
        CompraMapper.INSTANCE.updateCompraFromCompraEdit(entity, existingEntity);
        return CompraMapper.INSTANCE.compraEditToCompra(compraRepository.save(existingEntity));
    }

    @Override
    public void delete(Long id) {
        if (compraRepository.existsById(id)) {
            compraRepository.deleteById(id);
        }
    }
}
