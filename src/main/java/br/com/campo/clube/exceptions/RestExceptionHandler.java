package br.com.campo.clube.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

//Esta classe vai observar até que uma exceção ocorra
@RestControllerAdvice
public class RestExceptionHandler {

    //Lida com as exceções dos DTOS
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> lidaComValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    // Lida com Parametro Invalido Exception
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ParametroInvalidoException.class)
    public Map<String, String> lidaComParametroInvalido(ParametroInvalidoException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("erro", ex.getMessage());
        return error;
    }
    //Lida com AssociadoException
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AssociadoException.class)
    public Map<String, String> lidaComAssociadoException(AssociadoException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("erro", ex.getMessage());
        return error;
    }

    //Lida com ReservaInvalidaException
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ReservaInvalidaException.class)
    public Map<String, String> lidaComReservaInvalidaException(ReservaInvalidaException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("erro", ex.getMessage());
        return error;
    }

}
