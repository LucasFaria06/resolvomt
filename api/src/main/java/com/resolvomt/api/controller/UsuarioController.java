package com.resolvomt.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.resolvomt.api.dto.UsuarioCreateRequest;
import com.resolvomt.api.dto.UsuarioResponse;
import com.resolvomt.api.model.Usuario;
import com.resolvomt.api.service.UsuarioService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponse> cadastrar(@RequestBody @Valid UsuarioCreateRequest request) {
        Usuario novoUsuario = service.cadastrar(request);

        return ResponseEntity
               .status(HttpStatus.CREATED)
               .body(new UsuarioResponse(novoUsuario));
    }
}