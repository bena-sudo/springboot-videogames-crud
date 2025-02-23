package edu.alumno.videogames.helper;

import java.util.HashMap;
import java.util.Map;

import org.springframework.validation.BindingResult;

import edu.alumno.videogames.exception.BindingResultException;
import edu.alumno.videogames.exception.DataValidationException;

/**
 * Clase utilitaria para ayudar con operaciones comunes en controladores.
 * 
 * Proporciona métodos estáticos para manejar tareas repetitivas como la
 * validación de errores en los controladores.
 */
public class BindingResultHelper {
    private BindingResultHelper() {
        // Constructor privado para prevenir instanciación
        throw new UnsupportedOperationException("Esta clase no puede ser instanciada.");
    }

    /**
     * Extrae los errores de validación de un BindingResult y los organiza en un
     * mapa.
     * 
     * @param bindingResult Resultado de la validación, típicamente asociado con
     *                      anotaciones como @Valid.
     * @return Un mapa donde las claves son los nombres de los campos y los valores
     *         son los mensajes de error.
     */
    public static Map<String, String> extractErrors(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return errors;
    }

    /**
     * Valida el resultado de la vinculación (binding) de un objeto y lanza una
     * excepción si se detectan errores de validación.
     *
     * Este método es útil para centralizar la lógica de validación de objetos
     * anotados con restricciones de Bean Validation (@Valid) en los controladores,
     * reduciendo la repetición de código y mejorando la legibilidad.
     *
     * @param bindingResult El objeto {@link BindingResult} que contiene los
     *                      resultados de la validación realizada por el
     *                      framework Spring.
     * @param errorCode     Un código de error personalizado que identifica el
     *                      contexto o tipo de validación que falló.
     * @throws DataValidationException Si se detectan errores de validación, lanza
     *                                 una excepción con el código de error
     *                                 proporcionado y un mapa de los errores
     *                                 específicos.
     *
     * @see BindingResult
     * @see BindingResultException
     */
    public static void validateBindingResult(BindingResult bindingResult, String errorCode) {
        if (bindingResult.hasErrors()) {
            throw new BindingResultException(errorCode, BindingResultHelper.extractErrors(bindingResult));
        }
    }

}
