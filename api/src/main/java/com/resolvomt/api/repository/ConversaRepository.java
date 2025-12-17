package com.resolvomt.api.repository;

import com.resolvomt.api.model.Conversa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConversaRepository extends JpaRepository<Conversa, Long> {

    Optional<Conversa> findByAgendamentoId(Long agendamentoId);
}
