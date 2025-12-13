package com.resolvomt.api.service;

import com.resolvomt.api.dto.usuario.UsuarioCreateRequestDTO;
import com.resolvomt.api.enums.TipoUsuario;
import com.resolvomt.api.model.Usuario;
import com.resolvomt.api.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder; 
import org.springframework.stereotype.Service;


@Service
public class UsuarioService {

   private final UsuarioRepository repository;
   private final PasswordEncoder passwordEncoder;

   public UsuarioService(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
    this.repository = repository;
    this.passwordEncoder = passwordEncoder;
   }

   public Usuario cadastrar(UsuarioCreateRequestDTO request) {

    if (repository.existsByEmail(request.getEmail())) {
        throw new IllegalArgumentException("E-mail já está em uso.");
    }

    Usuario usuario = new Usuario();
    usuario.setNomeCompleto(request.getNomeCompleto());
    usuario.setEmail(request.getEmail());
    usuario.setSenha(passwordEncoder.encode(request.getSenha()));
    usuario.setTipoUsuario(request.getTipoUsuario());

    return repository.save(usuario);
   }
}