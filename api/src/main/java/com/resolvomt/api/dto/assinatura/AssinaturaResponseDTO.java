package com.resolvomt.api.dto.assinatura;

import com.resolvomt.api.enums.StatusAssinatura;
import com.resolvomt.api.model.Assinatura;

import java.time.LocalDateTime;

public class AssinaturaResponseDTO {
    private Long id;
    private String planoNome;
    private Double precoMensal;
    private Double precoAnual;
    private Double taxaComissao;
    private Integer limiteAgendamentos;
    private StatusAssinatura status;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private Integer anosFidelidade;
    private Boolean trialUtilizado;
    private Boolean ativa;
    private Long diasRestantes;

    public AssinaturaResponseDTO() {
    }

    public AssinaturaResponseDTO(Assinatura assinatura) {
        this.id = assinatura.getId();
        this.planoNome = assinatura.getPlano().getNome();
        this.precoMensal = assinatura.getPlano().getPrecoMensal() != null
                ? assinatura.getPlano().getPrecoMensal().doubleValue()
                : null;
        this.precoAnual = assinatura.getPlano().getPrecoAnual() != null
                ? assinatura.getPlano().getPrecoAnual().doubleValue()
                : null;
        this.taxaComissao = assinatura.getPlano().getComissaoPercentual().doubleValue();
        this.limiteAgendamentos = assinatura.getPlano().getLimiteAgendamentosMes();
        this.status = assinatura.getStatus();
        this.dataInicio = assinatura.getDataInicio();
        this.dataFim = assinatura.getDataFim();
        this.anosFidelidade = assinatura.getAnosFidelidade();
        this.trialUtilizado = assinatura.getTrialUtilizado();
        this.ativa = assinatura.isAtiva();
        this.diasRestantes = calcularDiasRestantes(assinatura.getDataFim());
    }

    private static Long calcularDiasRestantes(LocalDateTime dataFim) {
        if (dataFim == null) return null;
        long dias = java.time.Duration.between(LocalDateTime.now(), dataFim).toDays();
        return dias > 0 ? dias : 0;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPlanoNome() { return planoNome; }
    public void setPlanoNome(String planoNome) { this.planoNome = planoNome; }

    public Double getPrecoMensal() { return precoMensal; }
    public void setPrecoMensal(Double precoMensal) { this.precoMensal = precoMensal; }

    public Double getPrecoAnual() { return precoAnual; }
    public void setPrecoAnual(Double precoAnual) { this.precoAnual = precoAnual; }

    public Double getTaxaComissao() { return taxaComissao; }
    public void setTaxaComissao(Double taxaComissao) { this.taxaComissao = taxaComissao; }

    public Integer getLimiteAgendamentos() { return limiteAgendamentos; }
    public void setLimiteAgendamentos(Integer limiteAgendamentos) { this.limiteAgendamentos = limiteAgendamentos; }

    public StatusAssinatura getStatus() { return status; }
    public void setStatus(StatusAssinatura status) { this.status = status; }

    public LocalDateTime getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDateTime dataInicio) { this.dataInicio = dataInicio; }

    public LocalDateTime getDataFim() { return dataFim; }
    public void setDataFim(LocalDateTime dataFim) { this.dataFim = dataFim; }

    public Integer getAnosFidelidade() { return anosFidelidade; }
    public void setAnosFidelidade(Integer anosFidelidade) { this.anosFidelidade = anosFidelidade; }

    public Boolean getTrialUtilizado() { return trialUtilizado; }
    public void setTrialUtilizado(Boolean trialUtilizado) { this.trialUtilizado = trialUtilizado; }

    public Boolean getAtiva() { return ativa; }
    public void setAtiva(Boolean ativa) { this.ativa = ativa; }

    public Long getDiasRestantes() { return diasRestantes; }
    public void setDiasRestantes(Long diasRestantes) { this.diasRestantes = diasRestantes; }
}