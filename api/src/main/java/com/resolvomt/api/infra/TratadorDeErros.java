package com.resolvomt.api.infra;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarErro404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException ex) {
        var erros = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity tratarErroRegraDeNegocio(RuntimeException ex) {
        return ResponseEntity.badRequest().body(new DadosErroGenerico(ex.getMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity tratarErroDeIntegridade(DataIntegrityViolationException ex) {
        String erroReal = ex.getRootCause() != null ? ex.getRootCause().getMessage() : ex.getMessage();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new DadosErroGenerico("Erro de Integridade: " + erroReal));
    }

    
    private record DadosErroValidacao(String campo, String mensagem) {
        public DadosErroValidacao(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
        }
    }
    
    private record DadosErroGenerico(String mensagem) {}
}