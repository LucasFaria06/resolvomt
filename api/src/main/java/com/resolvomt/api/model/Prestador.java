package com.resolvomt.api.model;

import com.resolvomt.api.enums.PlanoPrestador;
import com.resolvomt.api.enums.StatusAssinatura;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private boolean verificado = false;

    @Column(nullable = false)
    private boolean ativo = true;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PlanoPrestador plano = PlanoPrestador.FREE;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAssinatura statusAssinatura = StatusAssinatura.TRIAL;

    @Column(name = "trial_ate")
    private LocalDate trialAte;

    @Column(name = "assinatura_ate")
    private LocalDate assinaturaAte;

    public Long getId() {
        return id;
    }

    public String getNome() { return nome; }

    // ======================================== //

    public void setNome(String nome) { this.nome = nome; }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public boolean isVerificado() {
        return verificado;
    }

    public void setVerificado(boolean verificado) {
        this.verificado = verificado;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public PlanoPrestador getPlano() { return plano;}

    public void setPlano(PlanoPrestador plano) { this.plano = plano;}

    public StatusAssinatura getStatusAssinatura() { return statusAssinatura;}

    public void setStatusAssinatura(StatusAssinatura statusAssinatura) { this.statusAssinatura = statusAssinatura;}

    public LocalDate getTrialAte() { return trialAte;}

    public void setTrialAte(LocalDate trialAte) { this.trialAte = trialAte;}

    public LocalDate getAssinaturaAte() { return assinaturaAte;}

    public void setAssinaturaAte(LocalDate assinaturaAte) { this.assinaturaAte = assinaturaAte;}
}