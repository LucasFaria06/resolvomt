package com.resolvomt.api.dto.servico;

import com.resolvomt.api.model.Servico;

import java.math.BigDecimal;

public record ServicoPublicoDTO (
        Long id,
        String nome,
        String descricao,
        BigDecimal valor,
        Integer duracaoMinutos,
        String nomePrestador
) {
    public ServicoPublicoDTO(Servico servico){
        this(
                servico.getId(),
                servico.getNome(),
                servico.getDescricao(),
                servico.getValor(),
                servico.getDuracaoMinutos(),
                servico.getPrestador().getNome()
        );
    }
}
