package com.resolvomt.api.model;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "missoes_prestador")
@Getter
@Setter
public class MissaoPrestador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "prestador_id", nullable = false)
    private Prestador prestador;

    @Column(name = "missao_tipo", nullable = false)
    private String missaoTipo;

    @Column(name = "concluida_em")
    private LocalDateTime concluidaEm;

    @Column(name = "dias_bonus")
    private Integer diasBonus;

    @PrePersist
    public void prePersist() {
        if (this.concluidaEm == null) {
            this.concluidaEm = LocalDateTime.now();
        }
    }

    public boolean isConcluida() {
        return this.concluidaEm != null;
    }
}
