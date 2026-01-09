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
        where p.verificado = false
    """)
    List<Prestador> buscarPendentes();

    @Query("""
        select p from Prestador p
        join fetch p.usuario
        where p.verificado = true
    """)
    List<Prestador> listarVerificados();

    @Query("""
        select p from Prestador p
        join fetch p.usuario u
        where u.email = :email
    """)
    Optional<Prestador> findByUsuarioEmail(String email);

    boolean existsByCnpj(String cnpj);

    Optional<Prestador> findByUsuarioId(Long usuarioId);
}

