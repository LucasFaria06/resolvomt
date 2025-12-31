package com.resolvomt.api.dto.servico;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ServicoCreateRequestDTO (

        @NotBlank(message = "Nome do serviço é obrigatório")
        @Size(min = 3, max = 100, message = "Descrição deve ter entre 3 e 100 caracteres")
        String nome,

        @NotBlank(message = "Descrição é obrigatória")
        @Size(min = 10, max = 500, message = "Descrição deve ter entre 10 e 500 caracteres")
        String descricao,

        @NotNull(message = "Valor é obrigatório")
        @DecimalMin(value = "0.01", message = "Valor deve ser maior que zero")
        BigDecimal valor,

        @NotNull(message = "Duração é obrigatória")
        @Min(value = 1, message = "Duração deve ser maior que zero")
        Integer duracaoMinutos,

        Boolean ativo
) {}
