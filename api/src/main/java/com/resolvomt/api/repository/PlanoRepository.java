package com.resolvomt.api.repository;

import com.resolvomt.api.enums.TipoPlano;
import com.resolvomt.api.model.Plano;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlanoRepository extends JpaRepository<Plano, Long> {
    Optional<Plano> findByNome(String nome);

    Optional<Plano> findByTipo(TipoPlano tipo);

    boolean existsByTipo(TipoPlano tipo);
}