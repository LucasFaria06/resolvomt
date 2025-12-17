package com.resolvomt.api.repository;

import com.resolvomt.api.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    List<Agendamento> findByClienteUsuarioEmail(String email);

    List<Agendamento> findByPrestadorUsuarioEmail(String email);
}
