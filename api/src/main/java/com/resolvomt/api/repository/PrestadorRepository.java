package com.resolvomt.api.repository;

import com.resolvomt.api.model.Prestador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PrestadorRepository extends JpaRepository<Prestador, Long> {

    @Query("""
    select p from Prestador p 
    join fetch p.usuario
    where p.usuario.email = :email
    """)
    // ====== USO GERAL ====== //
    Optional<Prestador> findByUsuarioEmail(String email);

    // ====== PÚBLICO ====== //
    List<Prestador> findByVerificadoTrueAndAtivoTrue();

    // ====== ADMIN ====== //
    List<Prestador> findByVerificadoFalse();

    List<Prestador> findByAtivoTrue();

    List<Prestador> findByAtivoFalse();

    boolean existsByCnpj(String cnpj);
}
