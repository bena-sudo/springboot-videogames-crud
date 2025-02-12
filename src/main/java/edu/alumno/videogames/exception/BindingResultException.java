package edu.alumno.videogames.exception;

import java.util.Map;

import lombok.Getter;

@Getter
public class BindingResultException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String errorCode;

    private final Map<String, String> validationErrors;

    public BindingResultException(String errorCode, Map<String, String> validationErrors) {
        this.errorCode = errorCode;
        this.validationErrors = validationErrors;
    }
}
