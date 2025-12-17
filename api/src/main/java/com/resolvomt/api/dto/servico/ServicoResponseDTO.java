package com.resolvomt.api.dto.servico;

import com.resolvomt.api.model.Servico;

import java.math.BigDecimal;

public record ServicoResponseDTO (
        Long id,
        String nome,
        String descricao,
        BigDecimal valor,
        Integer duracaoMinutos
) {
    public ServicoResponseDTO(Servico servico) {
        this(
                servico.getId(),
                servico.getNome(),
                servico.getDescricao(),
                servico.getValor(),
                servico.getDuracaoMinutos()
        );
    }
}
