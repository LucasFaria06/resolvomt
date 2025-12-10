package com.resolvomt.api.service;

import com.resolvomt.api.dto.UsuarioCreateRequest;
import com.resolvomt.api.enums.TipoUsuario;
import com.resolvomt.api.model.Usuario;
import com.resolvomt.api.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder; 
import org.springframework.stereotype.Service;


@Service
public class UsuarioService {

   private final UsuarioRepository repository;
   private final PasswordEncoder PasswordEncoder;

   public UsuarioService(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
    this.repository = repository;
    this.PasswordEncoder = passwordEncoder;
   }

   public Usuario cadastrar(UsuarioCreateRequest request) {

    if (repository.existsByEmail(request.getEmail())) {
        throw new IllegalArgumentException("E-mail já está em uso.");
    }

    Usuario usuario = new Usuario();
    usuario.setNomeCompleto(request.getNome());
    usuario.setEmail(request.getEmail());
    usuario.setSenha(PasswordEncoder.encode(request.getSenha()));
    usuario.setTipoUsuario(TipoUsuario.USER);

    return repository.save(usuario);
   }
}