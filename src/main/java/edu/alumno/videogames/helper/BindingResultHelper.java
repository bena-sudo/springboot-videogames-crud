package edu.alumno.videogames.helper;

import java.util.HashMap;
import java.util.Map;

import org.springframework.validation.BindingResult;

import edu.alumno.videogames.exception.BindingResultException;

public class BindingResultHelper {
    private BindingResultHelper() {
        throw new UnsupportedOperationException("Esta clase no puede ser instanciada");
    }

    public static Map<String, String> extractErrors(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return errors;
    }

    public static void validateBindingResult(BindingResult bindingResult, String errorCode) {
        if (bindingResult.hasErrors()) {
            throw new BindingResultException(errorCode, extractErrors(bindingResult));
        }
    }
}
