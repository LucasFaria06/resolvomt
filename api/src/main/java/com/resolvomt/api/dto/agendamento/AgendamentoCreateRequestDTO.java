package com.resolvomt.api.dto.agendamento;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record AgendamentoCreateRequestDTO(
        @NotNull(message = "ID do serviço é obrigatório")
        Long servicoId,

        @NotNull(message = "Data e hora são obrigatórias")
        @Future(message = "Data deve ser no futuro")
        LocalDateTime dataHora,

        @Size(max = 500, message = "Observações devem ter no máximo 500 caracteres")
        String observacoes
) {}
