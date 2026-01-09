package com.resolvomt.api.model;

import com.resolvomt.api.enums.StatusAssinatura;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "assinaturas")
@Getter
@Setter
public class Assinatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "prestador_id", nullable = false)
    private Prestador prestador;

    @ManyToOne
    @JoinColumn(name = "plano_id", nullable = false)
    private Plano plano;

    @Column(name = "data_inicio", nullable = false)
    private LocalDateTime dataInicio;

    @Column(name = "data_fim", nullable = false)
    private LocalDateTime dataFim;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAssinatura status;

    @Column(name = "anos_fidelidade")
    private Integer anosFidelidade = 0;

    @Column(name = "trial_utilizado")
    private Boolean trialUtilizado = false;

    @PrePersist
    public void prePersist() {
        if (this.dataInicio == null) {
            this.dataInicio = LocalDateTime.now();
        }
        if (this.status == null) {
            this.status = StatusAssinatura.TRIAL;
        }
        if (this.trialUtilizado == null) {
            this.trialUtilizado = false;
        }
        if (this.anosFidelidade == null) {
            this.anosFidelidade = 0;
        }
    }

    public void adicionarDiasBonus(int dias) {
        if (this.dataFim != null) {
            this.dataFim = this.dataFim.plusDays(dias);
        }
    }

    public boolean isAtiva() {
        return (status == StatusAssinatura.TRIAL || status == StatusAssinatura.ATIVA)
                && LocalDateTime.now().isBefore(dataFim);
    }

    public boolean isTrial() {
        return status == StatusAssinatura.TRIAL && LocalDateTime.now().isBefore(dataFim);
    }

    public boolean isExpirada() {
        return LocalDateTime.now().isAfter(dataFim) || status == StatusAssinatura.EXPIRADA;
    }

    public void ativar(LocalDateTime novaDataFim) {
        this.status = StatusAssinatura.ATIVA;
        this.dataFim = novaDataFim;
    }

    public void cancelar() {
        this.status = StatusAssinatura.CANCELADA;
    }

    public void expirar() {
        this.status = StatusAssinatura.EXPIRADA;
    }

    public void renovarAnual() {
        this.anosFidelidade++;
        this.dataFim = LocalDateTime.now().plusYears(1);
        this.status = StatusAssinatura.ATIVA;
    }
}
