package com.resolvomt.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.resolvomt.api.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    
}
