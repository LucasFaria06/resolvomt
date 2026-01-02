package com.resolvomt.api.dto.agendamento;

import com.resolvomt.api.enums.StatusAgendamento;
import com.resolvomt.api.model.Agendamento;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AgendamentoResponseDTO (
        Long id,
        LocalDateTime dataHora,
        StatusAgendamento status,
        String observacoes,
        BigDecimal valor,
        Integer duracaoMinutos,

        Long servicoId,
        String servicoNome,

        Long clienteId,
        String clienteNome,
        String clienteTelefone,

        Long prestadorId,
        String prestadorNome,
        String prestadorTelefone
) {
    public AgendamentoResponseDTO(Agendamento agendamento) {
        this(
                agendamento.getId(),
                agendamento.getDataHora(),
                agendamento.getStatus(),
                agendamento.getObservacoes(),
                agendamento.getValor(),
                agendamento.getDuracaoMinutos(),

                agendamento.getServico().getId(),
                agendamento.getServico().getNome(),

                agendamento.getCliente().getId(),
                agendamento.getCliente().getUsuario().getNomeCompleto(),
                agendamento.getCliente().getTelefone(),

                agendamento.getServico().getPrestador().getId(),
                agendamento.getServico().getPrestador().getNome(),
                agendamento.getServico().getPrestador().getTelefone()
        );
    }
}
