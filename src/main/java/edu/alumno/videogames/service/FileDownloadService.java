package edu.alumno.videogames.service;

import org.springframework.http.ResponseEntity;

public interface FileDownloadService {

    /**
     * Prepara una respuesta HTTP para descargar o previsualizar un archivo a partir
     * de su contenido en formato byte array.
     *
     * @param byteContent El contenido del archivo en formato de bytes.
     * @param contentType El tipo MIME del contenido del archivo (por ejemplo,
     *                    'application/pdf').
     * @param fileName    El nombre del archivo que se sugiere para la descarga o
     *                    previsualización.
     * @return Una ResponseEntity que contiene el contenido del archivo como bytes,
     *         con headers configurados para permitir la descarga o
     *         previsualización.
     */
    public ResponseEntity<byte[]> prepareDownloadResponse(byte[] byteContent, String contentType, String fileName);

    /**
     * Prepara una respuesta HTTP para descargar o previsualizar un archivo a partir
     * de su contenido en formato Base64.
     *
     * @param base64Content El contenido del archivo codificado en Base64.
     * @param contentType   El tipo MIME del contenido del archivo (por ejemplo,
     *                      'application/pdf').
     * @param fileName      El nombre del archivo que se sugiere para la descarga o
     *                      previsualización.
     * @return Una ResponseEntity que contiene el contenido del archivo decodificado
     *         de Base64 a bytes, con headers configurados para permitir la descarga
     *         o previsualización.
     */
    public ResponseEntity<byte[]> prepareDownloadResponse(String base64Content, String contentType, String fileName);
}
