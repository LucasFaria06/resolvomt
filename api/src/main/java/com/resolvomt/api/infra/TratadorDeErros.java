package com.resolvomt.api.infra;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.dao.DataIntegrityViolationException;

@RestControllerAdvice
public class TratadorDeErros {
    
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> tratarErroDeDuplicidade(){
        return ResponseEntity
        .status(HttpStatus.CONFLICT)
        .body("Já existe um usuário cadastrado com este email!");
    }
}
