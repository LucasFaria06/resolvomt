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

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Usuario cliente;

    @ManyToOne
    @JoinColumn(name = "prestador_id", nullable = false)
    private Usuario prestador;

    @Column(nullable = false)
    private LocalDateTime dataServico;

    @Column(nullable = false)
    private String status = "PENDENTE";

    @Column(name = "valor_total", nullable = false)
    private Double valorTotal;
}