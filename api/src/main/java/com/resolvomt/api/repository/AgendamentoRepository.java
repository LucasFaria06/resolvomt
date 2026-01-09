package com.resolvomt.api.repository;

import com.resolvomt.api.enums.StatusAgendamento;
import com.resolvomt.api.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    @Query("SELECT a FROM Agendamento a " +
            "JOIN FETCH a.servico s " +
            "JOIN FETCH s.prestador p " +
            "JOIN FETCH a.cliente c " +
            "WHERE c.id = :clienteId ORDER BY a.dataHora DESC")
    List<Agendamento> findByClienteIdWithDetails(@Param("clienteId") Long clienteId);

    @Query("SELECT a FROM Agendamento a " +
            "JOIN FETCH a.servico s " +
            "JOIN FETCH s.prestador p " +
            "JOIN FETCH a.cliente c " +
            "WHERE p.usuario.email = :email ORDER BY a.dataHora DESC")
    List<Agendamento> findByPrestadorEmailWithDetails(@Param("email") String email);

    @Query("SELECT a FROM Agendamento a " +
            "JOIN FETCH a.servico s " +
            "JOIN FETCH s.prestador p " +
            "JOIN FETCH a.cliente c " +
            "WHERE a.id = :id AND p.usuario.email = :email")
    Optional<Agendamento> findByIdAndPrestadorEmailWithDetails(@Param("id") Long id, @Param("email") String email);

    @Query("""
        SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END
        FROM Agendamento a
        WHERE a.prestador.id = :prestadorId
        AND a.status != :statusExcluido
        AND (
            (a.dataHora < :dataFim AND a.dataHora >= :dataInicio)
            OR (a.dataHora <= :dataInicio AND :dataInicio < a.dataHora)
        )
    """)
    boolean existsConflito(
            @Param("prestadorId") Long prestadorId,
            @Param("dataInicio") LocalDateTime dataInicio,
            @Param("dataFim") LocalDateTime dataFim,
            @Param("statusExcluido") StatusAgendamento statusExcluido
    );

    long countByPrestadorIdAndDataHoraAfter(Long prestadorId, LocalDateTime data);
}