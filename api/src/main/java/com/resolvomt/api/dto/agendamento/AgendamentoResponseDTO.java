package com.resolvomt.api.dto.agendamento;

import com.resolvomt.api.model.Agendamento;

import java.time.LocalDateTime;

public record AgendamentoResponseDTO (
        Long id,
        String servico,
        String prestador,
        LocalDateTime dataHora,
        String status
) {
    public AgendamentoResponseDTO(Agendamento agendamento) {
        this(
                agendamento.getId(),
                agendamento.getServico().getNome(),
                agendamento.getPrestador().getUsuario().getNomeCompleto(),
                agendamento.getDataHora(),
                agendamento.getStatus().name()
        );
    }
}
