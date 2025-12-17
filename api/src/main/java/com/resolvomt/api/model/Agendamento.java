package com.resolvomt.api.model;

import com.resolvomt.api.enums.StatusAgendamento;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "agendamentos")
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prestador_id", nullable = false)
    private Prestador prestador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "servico_id", nullable = false)
    private Servico servico;

    @Column(nullable = false)
    private LocalDateTime dataHora;

    @Column(nullable = false)
    private StatusAgendamento status;

    @PrePersist
    public void prePersist() {
        this.status = StatusAgendamento.CRIADO;
    }
    public Long getId() { return id; }

    public void setId( Long id) { this.id = id; }

    public Cliente getCliente() { return cliente; }

    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Prestador getPrestador() { return prestador; }

    public void setPrestador(Prestador prestador) { this.prestador = prestador; }

    public Servico getServico() { return servico; }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public LocalDateTime getDataHora() { return dataHora; }

    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }

    public StatusAgendamento getStatus() { return status; }

    public void setStatus(StatusAgendamento status) { this.status = status; }
}
