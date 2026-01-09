package com.resolvomt.api.model;

import com.resolvomt.api.enums.TipoPlano;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "planos")
@Getter
@Setter
public class Plano {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoPlano tipo;

    @Column(nullable = false, unique = true)
    private String nome;

    private BigDecimal precoMensal;
    private BigDecimal precoAnual;
    private BigDecimal comissaoPercentual;
    private Integer limiteAgendamentosMes;
}
