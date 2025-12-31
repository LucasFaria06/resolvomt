package com.resolvomt.api.dto.servico;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ServicoUpdateRequestDTO (

        @NotBlank(message = "Nome do serviço é obrigatŕorio ")
        @Size(min = 3, max = 100)
        String nome,

        @NotBlank(message = "Descrição é obrigatória")
        @Size(min = 10, max = 500)
        String descricao,

        @NotNull(message = "Valor é obrigatório")
        @DecimalMin(value = "0.01", message = "Valor deve ser maior que zero")
        BigDecimal valor,

        @NotNull(message = "Duração é obrigatória")
        @Min(value = 1, message = "Duração deve ser maior que zero")
        Integer duracaoMinutos,

        Boolean ativo
        ) {}