package com.resolvomt.api.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDateTime;

@Data 
public class AgendamentoRequest {

    @NotNull(message = "A data do serviço é obrigatória")
    @Future(message = "A data deve ser no futuro")
    private LocalDateTime dataServico;

    @NotBlank(message = "A descrição do serviço não pode estar vazia")
    private String descricao;

    @NotNull(message = "O valor é obrigatório")
    @Positive(message = "O valor deve ser positivo")
    private Double valorTotal;

    @NotNull(message = "ID do cliente é obrigatório")
    private Long clienteId;

    @NotNull(message = "ID do prestador é obrigatório")
    private Long prestadorId;
}