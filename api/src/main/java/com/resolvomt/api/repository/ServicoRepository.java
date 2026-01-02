package com.resolvomt.api.repository;

import com.resolvomt.api.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ServicoRepository extends JpaRepository<Servico, Long> {

    @Query("SELECT s FROM Servico s JOIN FETCH s.prestador WHERE s.prestador.id = :prestadorId")
    List<Servico> findByPrestadorId(@Param("prestadorId") Long prestadorId);

    @Query("SELECT s FROM Servico s JOIN FETCH s.prestador WHERE s.id = :id AND s.prestador.id = :prestadorId")
    Optional<Servico> findByIdAndPrestadorId(@Param("id") Long id, @Param("prestadorId") Long prestadorId);

    @Query("SELECT s FROM Servico s JOIN FETCH s.prestador p " +
            "WHERE p.verificado = true AND p.ativo = true AND s.ativo = true")
    List<Servico> findAllPublicosComPrestador();

    @Query("SELECT s FROM Servico s JOIN FETCH s.prestador WHERE s.id = :id")
    Optional<Servico> findByIdComPrestador(@Param("id") Long id);
}
