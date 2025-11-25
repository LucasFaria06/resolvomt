package com.resolvomt.api.dto;

import lombok.Data;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

@Data
public class AgendamentoRequestDTO {
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataServico;
    private String descricao;
    private Double valorTotal;
    
    private Long clienteId;
    private Long prestadorId;
}