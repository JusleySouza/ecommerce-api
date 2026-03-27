package br.com.indra.jusley_freitas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateCpfException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public DuplicateCpfException(String exception) {
        super(exception);
    }
}
