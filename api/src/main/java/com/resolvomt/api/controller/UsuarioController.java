package com.resolvomt.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

import com.resolvomt.api.dto.UsuarioResponse;
import com.resolvomt.api.model.Usuario;
import com.resolvomt.api.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.resolvomt.api.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping
    public ResponseEntity<UsuarioResponse> cadastrar(@RequestBody @Valid Usuario usuario){

        Usuario novoUsuario = service.cadastrar(usuario);
        
        return new ResponseEntity<>(new UsuarioResponse(novoUsuario), HttpStatus.CREATED);
    }
}