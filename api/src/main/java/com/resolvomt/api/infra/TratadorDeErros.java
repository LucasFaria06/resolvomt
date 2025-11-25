package com.resolvomt.api.infra;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.dao.DataIntegrityViolationException;

@RestControllerAdvice
public class TratadorDeErros {

   @ExceptionHandler(DataIntegrityViolationException.class)
public ResponseEntity<String> tratarErroDeIntegridade(DataIntegrityViolationException ex) {
    String erroReal = ex.getRootCause() != null ? ex.getRootCause().getMessage() : ex.getMessage();
    
    return ResponseEntity
        .status(HttpStatus.CONFLICT)
        .body("Erro de Integridade do Banco: " + erroReal);
}
}
