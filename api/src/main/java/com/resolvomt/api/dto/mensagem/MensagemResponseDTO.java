package com.resolvomt.api.dto.mensagem;

import com.resolvomt.api.model.Mensagem;

import java.time.LocalDateTime;

public record MensagemResponseDTO (
        Long id,
        String conteudo,
        String remetente,
        LocalDateTime enviadaEm
) {
    public MensagemResponseDTO(Mensagem m){
        this(
                m.getId(),
                m.getConteudo(),
                m.getRemetente().getNomeCompleto(),
                m.getEnviadaEm()
        );
    }
}
