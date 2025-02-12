package edu.alumno.videogames.exception;

import lombok.Getter;

@Getter
public class DataValidationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String errorCode;

    public DataValidationException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
