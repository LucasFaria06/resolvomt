package com.resolvomt.api.dto.agendamento;

import java.time.LocalDateTime;

public record AgendamentoRequestDTO (
        Long servicoId,
        LocalDateTime dataHora
) {}
