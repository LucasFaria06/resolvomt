package com.resolvomt.api.repository;

import com.resolvomt.api.model.MissaoPrestador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MissaoPrestadorRepository extends JpaRepository<MissaoPrestador, Long> {

    List<MissaoPrestador> findByPrestadorId(Long prestadorId);

    boolean existsByPrestadorIdAndMissaoTipo(Long prestadorId, String missaoTipo);
}
