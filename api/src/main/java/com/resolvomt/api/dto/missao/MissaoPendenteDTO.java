package com.resolvomt.api.dto.missao;

import com.resolvomt.api.enums.MissaoTipo;

public record MissaoPendenteDTO(
        String missaoTipo,
        String descricao,
        Integer diasBonus
) {
    public MissaoPendenteDTO(MissaoTipo tipo) {
        this(
                tipo.name(),
                tipo.getDescricao(),
                tipo.getDiasBonus()
        );
    }
}