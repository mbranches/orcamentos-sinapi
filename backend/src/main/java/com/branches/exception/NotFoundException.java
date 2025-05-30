package com.branches.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotFoundException extends ResponseStatusException {
    public NotFoundException(String mensagem) {
        super(HttpStatus.NOT_FOUND, mensagem);
    }
}
