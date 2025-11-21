package com.resolvomt.api.dto;

import java.time.LocalDateTime;

import com.resolvomt.api.model.Agendamento;

import lombok.Data;

@Data
public class AgendamentoResponse {
    
    private Long id;
    private LocalDateTime dataServico;
    private String status;
    private Double valorTotal;

    private UsuarioResponse cliente;
    private UsuarioResponse prestador;

    public AgendamentoResponse(Agendamento agendamento){
        this.id = agendamento.getId();
        this.dataServico = agendamento.getDataServico();
        this.status = agendamento.getStatus();
        this.valorTotal = agendamento.getValorTotal();

        this.cliente = new UsuarioResponse(agendamento.getCliente());
        this.prestador = new UsuarioResponse(agendamento.getPrestador());
    }
}
