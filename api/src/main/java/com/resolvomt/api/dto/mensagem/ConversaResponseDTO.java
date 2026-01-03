package com.resolvomt.api.dto.mensagem;

import com.resolvomt.api.model.Conversa;

import java.time.LocalDateTime;

public record ConversaResponseDTO(
        Long id,
        Long agendamentoId,
        String clienteNome,
        String prestadorNome,
        String servicoNome,
        LocalDateTime criadaEm,
        Long mensagensNaoLidas
) {
    public ConversaResponseDTO(Conversa conversa, Long mensagensNaoLidas) {
        this(
                conversa.getId(),
                conversa.getAgendamento().getId(),
                conversa.getCliente().getUsuario().getNomeCompleto(),
                conversa.getPrestador().getUsuario().getNomeCompleto(),
                conversa.getAgendamento().getServico().getNome(),
                conversa.getCriadaEm(),
                mensagensNaoLidas
        );
    }
}
