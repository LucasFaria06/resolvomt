package com.resolvomt.api.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "agendamentos")
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_servico", nullable = false)
    private LocalDateTime dataServico;

    private String descricao;

    @Column(name = "valor_total")
    private Double valorTotal;

    private String status;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Usuario cliente;

    @ManyToOne
    @JoinColumn(name = "prestador_id")
    private Usuario prestador;
}