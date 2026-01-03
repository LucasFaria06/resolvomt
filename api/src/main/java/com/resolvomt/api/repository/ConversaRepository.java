package com.resolvomt.api.repository;

import com.resolvomt.api.model.Conversa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConversaRepository extends JpaRepository<Conversa, Long> {

    Optional<Conversa> findByAgendamentoId(Long agendamentoId);

    @Query("""
        SELECT c FROM Conversa c 
        WHERE c.agendamento.cliente.usuario.id = :usuarioId 
           OR c.agendamento.prestador.usuario.id = :usuarioId 
        ORDER BY c.criadaEm DESC
    """)
    List<Conversa> findByUsuarioId(@Param("usuarioId") Long usuarioId);

    boolean existsByAgendamentoId(Long agendamentoId);
}
