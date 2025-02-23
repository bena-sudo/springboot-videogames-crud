package edu.alumno.videogames.service.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import edu.alumno.videogames.filters.model.FiltroBusqueda;
import edu.alumno.videogames.filters.model.PaginaResponse;
import edu.alumno.videogames.model.db.DocumentoVideojuegoDB;
import edu.alumno.videogames.model.db.DocumentoVideojuegoEditDB;
import edu.alumno.videogames.model.dto.DocumentoVideojuegoEdit;
import edu.alumno.videogames.model.dto.DocumentoVideojuegoList;
import edu.alumno.videogames.model.dto.DocumentoVideojuegoResponse;
import edu.alumno.videogames.utils.MultipartUtils;

@Mapper(imports = { MultipartUtils.class })
public interface DocumentoVideojuegoMapper {
        DocumentoVideojuegoMapper INSTANCE = Mappers.getMapper(DocumentoVideojuegoMapper.class);

        /**
         * CRUD
         */
        DocumentoVideojuegoResponse docVideojuegoEditDbToDocVideojuegoResponse(
                        DocumentoVideojuegoEditDB documentoVideojuegoEditDB);

        @Mapping(target = "base64Documento", expression = "java(MultipartUtils.multipartToString(documentoVideojuegoEdit.getMultipart()))")
        @Mapping(target = "extensionDocumento", expression = "java(MultipartUtils.getExtensionMultipartfile(documentoVideojuegoEdit.getMultipart()))")
        @Mapping(target = "contentTypeDocumento", expression = "java(documentoVideojuegoEdit.getMultipart().getContentType())")
        DocumentoVideojuegoEditDB docVideojuegoEditToDocVideojuegoEditDb(DocumentoVideojuegoEdit documentoVideojuegoEdit);

        @Mapping(target = "base64Documento", expression = "java(MultipartUtils.multipartToString(documentoVideojuegoEdit.getMultipart()))")
        @Mapping(target = "extensionDocumento", expression = "java(MultipartUtils.getExtensionMultipartfile(documentoVideojuegoEdit.getMultipart()))")
        @Mapping(target = "contentTypeDocumento", expression = "java(documentoVideojuegoEdit.getMultipart().getContentType())")
        void updateDocVideojuegoEditDbFromDocVideojuegoEdit(DocumentoVideojuegoEdit documentoVideojuegoEdit,
                        @MappingTarget DocumentoVideojuegoEditDB documentoVideojuegoEditDB);

        /**
         * LISTADOS
         */
        List<DocumentoVideojuegoList> docsVideojuegoDbToDocsVideojuegoList(List<DocumentoVideojuegoDB> documentoVideojuegoDB);

        static PaginaResponse<DocumentoVideojuegoList> pageToPaginaResponseVideojuegoList(
                        Page<DocumentoVideojuegoDB> page,
                        List<FiltroBusqueda> filtros,
                        List<String> ordenaciones) {
                return new PaginaResponse<>(
                                page.getNumber(),
                                page.getSize(),
                                page.getTotalElements(),
                                page.getTotalPages(),
                                INSTANCE.docsVideojuegoDbToDocsVideojuegoList(page.getContent()),
                                filtros,
                                ordenaciones);
        }
}
