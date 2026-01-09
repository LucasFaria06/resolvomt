package com.resolvomt.api.dto.missao;

import com.resolvomt.api.enums.MissaoTipo;
import com.resolvomt.api.model.MissaoPrestador;

import java.time.LocalDateTime;

public record MissaoResponseDTO(
        Long id,
        String missaoTipo,
        String descricao,
        Integer diasBonus,
        LocalDateTime concluidaEm
) {
    public MissaoResponseDTO(MissaoPrestador missao, MissaoTipo tipo) {
        this(
                missao.getId(),
                missao.getMissaoTipo(),
                tipo.getDescricao(),
                missao.getDiasBonus(),
                missao.getConcluidaEm()
        );
    }
}