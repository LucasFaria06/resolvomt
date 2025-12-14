package com.resolvomt.api.repository;

import com.resolvomt.api.model.Prestador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PrestadorRepository extends JpaRepository<Prestador, Long> {

    Optional<Prestador> findByUsuarioEmail(String email);
}
