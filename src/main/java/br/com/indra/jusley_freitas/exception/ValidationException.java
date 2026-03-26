package br.com.indra.jusley_freitas.exception;

import lombok.Generated;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Generated
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class ValidationException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public ValidationException(String exception) {
        super(exception);
    }
}
