package br.com.indra.jusley_freitas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateCategoryException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DuplicateCategoryException(String exception) {
        super(exception);
    }
}
