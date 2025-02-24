package edu.alumno.videogames.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import edu.alumno.videogames.exception.BindingResultErrorsResponse;
import edu.alumno.videogames.exception.BindingResultException;
import edu.alumno.videogames.exception.CustomErrorResponse;
import edu.alumno.videogames.exception.DataValidationException;
import edu.alumno.videogames.exception.EntityAlreadyExistsException;
import edu.alumno.videogames.exception.EntityIllegalArgumentException;
import edu.alumno.videogames.exception.EntityNotFoundException;
import edu.alumno.videogames.exception.FiltroException;
import edu.alumno.videogames.helper.DataIntegrityViolationAnalyzer;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ControllerAdvice
public class GlobalExceptionHandler {

    @SuppressWarnings("unused")
    private final DataIntegrityViolationAnalyzer analyzer;

    @ExceptionHandler(EntityIllegalArgumentException.class) // Datos incorrectos
    public ResponseEntity<CustomErrorResponse> handleEntityIllegalArgumentException(EntityIllegalArgumentException ex) {
        CustomErrorResponse response = new CustomErrorResponse(ex.getErrorCode(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class) // No se ha encontrado el dato
    public ResponseEntity<CustomErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        CustomErrorResponse response = new CustomErrorResponse(ex.getErrorCode(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class) // Se intenta crear un dato con una pk que ya existe
    public ResponseEntity<CustomErrorResponse> handleEntityAlreadyExistsException(EntityAlreadyExistsException ex) {
        CustomErrorResponse response = new CustomErrorResponse(ex.getErrorCode(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DataValidationException.class) // Validación incorrecta de un atributo
    public ResponseEntity<CustomErrorResponse> handleDataValidationException(DataValidationException ex) {

        CustomErrorResponse response = new CustomErrorResponse(ex.getErrorCode(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BindingResultException.class) // Errores en las validaciones de BindingResult en la entidad
    public ResponseEntity<BindingResultErrorsResponse> handleBindingResultException(BindingResultException ex) {

        BindingResultErrorsResponse response = new BindingResultErrorsResponse(ex.getErrorCode(),
                ex.getValidationErrors());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) // Error en las validaciones de los datos
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<CustomErrorResponse> handleDataIntegrityViolationException(
            DataIntegrityViolationException ex) {
        String detailedMessage = (ex.getRootCause() != null) ? ex.getRootCause().getMessage() : ex.getMessage();

        String errorCode = DataIntegrityViolationAnalyzer.analyzeErrorCode(detailedMessage);
        String userMessage = DataIntegrityViolationAnalyzer.analyzeUserMessage(detailedMessage);

        CustomErrorResponse response = new CustomErrorResponse(errorCode, userMessage);
        response.setDetailedMessage(detailedMessage);

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<CustomErrorResponse> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex) {
        CustomErrorResponse response = new CustomErrorResponse("DATA_CONVERSION_ERROR",
                "Error en el tipo de dato de uno de los atributos suministrados",
                ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FiltroException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CustomErrorResponse> handleFiltroException(FiltroException ex) {
        CustomErrorResponse response = new CustomErrorResponse(ex.getErrorCode(), ex.getDetailedMessage(),
                ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<CustomErrorResponse> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex) {
        String errorMessage = String.format("El parámetro '%s' debe ser de tipo '%s'. Valor recibido: '%s'",
                ex.getName(),
                ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "desconocido",
                ex.getValue());

        CustomErrorResponse response = new CustomErrorResponse("INVALID_PARAMETER", errorMessage);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}