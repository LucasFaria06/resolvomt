package com.resolvomt.api.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AgendamentoRequest (
    @NotBlank(message = "A descrição é obrigatória")
    String descricao,

    @NotNull(message = "A data é obrigatória")
    @Future(message = "A data deve ser no futuro")
    LocalDateTime dataServico,

    @NotNull
    @Positive(message = "O valor deve ser positivo")
    Double valorTotal,

    @NotNull(message = "ID do cliente é obrigatório")
    Long idCliente,

    @NotNull(message = "ID do prestador é obrigatório")
    Long idPrestador
){
    
}
    
