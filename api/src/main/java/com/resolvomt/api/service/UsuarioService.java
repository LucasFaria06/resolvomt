package com.resolvomt.api.service;

import com.resolvomt.api.model.Usuario;
import com.resolvomt.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder; 
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder; 
    public List<Usuario> listarTodos() {
        return repository.findAll();
    }

    public Usuario cadastrar(Usuario usuario) {
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);
        
        return repository.save(usuario);
    }
}