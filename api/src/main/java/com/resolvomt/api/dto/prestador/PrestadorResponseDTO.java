package com.resolvomt.api.dto.prestador;

import com.resolvomt.api.enums.VerificacaoStatus;

import java.time.LocalDateTime;

public record PrestadorResponseDTO(
        Long id,
        String nome,
        String cpf,
        String email,
        String telefone,
        String endereco,
        VerificacaoStatus verificacaoIdentidadeStatus,
        VerificacaoStatus verificacaoCriminalStatus,
        Boolean prestadorVerificado,
        LocalDateTime verificacaoIdentidadeData,
        LocalDateTime verificacaoCriminalData
) {}
