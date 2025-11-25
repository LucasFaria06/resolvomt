package com.resolvomt.api.dto;

import com.resolvomt.api.model.Agendamento;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AgendamentoResponse {
    private Long id;
    private LocalDateTime dataServico;
    private String status;
    private Double valorTotal;
    
    // Aqui usamos a versão segura do usuário
    private UsuarioResponse cliente;
    private UsuarioResponse prestador;

    public AgendamentoResponse(Agendamento agendamento) {
        this.id = agendamento.getId();
        this.dataServico = agendamento.getDataServico();
        this.status = agendamento.getStatus();
        this.valorTotal = agendamento.getValorTotal();
        
        // Converte os usuários brutos em usuários seguros (DTOs)
        this.cliente = new UsuarioResponse(agendamento.getCliente());
        this.prestador = new UsuarioResponse(agendamento.getPrestador());
    }
}

