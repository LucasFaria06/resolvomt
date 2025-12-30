package com.resolvomt.api.model;

import com.resolvomt.api.enums.PlanoPrestador;
import com.resolvomt.api.enums.StatusAssinatura;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "prestadores")
public class Prestador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    @Column(nullable = false, unique = true)
    private String cnpj;

    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false)
    private boolean verificado;

    @Column(nullable = false)
    private boolean ativo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PlanoPrestador plano;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAssinatura statusAssinatura;

    @Column(name = "trial_ate")
    private LocalDate trialAte;

    @Column(name = "assinatura_ate")
    private LocalDate assinaturaAte;

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public Usuario getUsuario() { return usuario; }
    public String getCnpj() { return cnpj; }
    public String getTelefone() { return telefone; }
    public boolean isVerificado() { return verificado; }
    public boolean isAtivo() { return ativo; }
    public PlanoPrestador getPlano() { return plano; }
    public StatusAssinatura getStatusAssinatura() { return statusAssinatura; }
    public LocalDate getTrialAte() { return trialAte; }
    public LocalDate getAssinaturaAte() { return assinaturaAte; }

    public void setNome(String nome) { this.nome = nome; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public void setVerificado(boolean verificado) { this.verificado = verificado; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
    public void setPlano(PlanoPrestador plano) { this.plano = plano; }
    public void setStatusAssinatura(StatusAssinatura statusAssinatura) { this.statusAssinatura = statusAssinatura; }
    public void setTrialAte(LocalDate trialAte) { this.trialAte = trialAte; }
    public void setAssinaturaAte(LocalDate assinaturaAte) { this.assinaturaAte = assinaturaAte; }
}
