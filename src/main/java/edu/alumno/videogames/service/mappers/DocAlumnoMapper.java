package edu.alumno.videogames.service.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import edu.alumno.videogames.filters.model.FiltroBusqueda;
import edu.alumno.videogames.filters.model.PaginaResponse;
import edu.alumno.videogames.model.db.DocAlumnoDb;
import edu.alumno.videogames.model.db.DocAlumnoEditDb;
import edu.alumno.videogames.model.dto.DocAlumnoEdit;
import edu.alumno.videogames.model.dto.DocAlumnoList;
import edu.alumno.videogames.model.dto.DocAlumnoResponse;
import edu.alumno.videogames.utils.MultipartUtils;

@SuppressWarnings("unused")
@Mapper(imports = { MultipartUtils.class })
public interface DocAlumnoMapper {
    DocAlumnoMapper INSTANCE = Mappers.getMapper(DocAlumnoMapper.class);

    /**
     * CRUD
     */
    // Devuelve un objeto de tipo 'DocAlumnoRespomse' a partir de un objeto de tipo
    // 'DocAlumnoEditDb'
    DocAlumnoResponse docAlumnoEditDbToDocAlumnoResponse(DocAlumnoEditDb docAlumnoEditDb);

    // Devuelve un objeto de tipo 'DocAlumnoEditDb' a partir de un objeto de tipo
    // 'DocAlumnoEdit'
    @Mapping(target = "base64Documento", expression = "java(MultipartUtils.multipartToString(docAlumnoEdit.getMultipart()))")
    @Mapping(target = "extensionDocumento", expression = "java(MultipartUtils.getExtensionMultipartfile(docAlumnoEdit.getMultipart()))")
    @Mapping(target = "contentTypeDocumento", expression = "java(docAlumnoEdit.getMultipart().getContentType())")
    DocAlumnoEditDb docAlumnoEditToDocAlumnoEditDb(DocAlumnoEdit docAlumnoEdit);

    // Actualiza un objeto de tipo 'DocAlumnoEditDb' con los datos de un objeto de
    // tipo 'DocAlumnoEdit'
    @Mapping(target = "base64Documento", expression = "java(MultipartUtils.multipartToString(docAlumnoEdit.getMultipart()))")
    @Mapping(target = "extensionDocumento", expression = "java(MultipartUtils.getExtensionMultipartfile(docAlumnoEdit.getMultipart()))")
    @Mapping(target = "contentTypeDocumento", expression = "java(docAlumnoEdit.getMultipart().getContentType())")
    void updateDocAlumnoEditDbFromDocAlumnoEdit(DocAlumnoEdit docAlumnoEdit,
            @MappingTarget DocAlumnoEditDb docAlumnoDb);

    /**
     * LISTADOS
     */
    // Devuelve una lista de objetos 'DocAlumnoList' a partir de una lista de tipo
    // 'DocAlumnoDb'
    List<DocAlumnoList> docsAlumnoDbToDocsAlumnoList(List<DocAlumnoDb> docAlumnosDb);

    
    /**
     * Convierte una página de DocAlumnoDb en una respuesta paginada
     */
    static PaginaResponse<DocAlumnoList> pageToPaginaResponseAlumnoList(
            Page<DocAlumnoDb> page,
            List<FiltroBusqueda> filtros,
            List<String> ordenaciones) {
        return new PaginaResponse<>(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                INSTANCE.docsAlumnoDbToDocsAlumnoList(page.getContent()),
                filtros,
                ordenaciones);
    }
}
