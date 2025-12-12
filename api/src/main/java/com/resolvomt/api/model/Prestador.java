package com.resolvomt.api.model;

import com.resolvomt.api.enums.VerificacaoStatus;
import jakarta.persistence.*;


import java.time.LocalDateTime;


@Entity
@Table(name = "prestadores")
public class Prestador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true)
    private String cpf;

    @Column(unique = true)
    private String email;

    private String telefone;

    private String endereco;

    @Enumerated(EnumType.STRING)
    private VerificacaoStatus verificacaoIdentidadeStatus = VerificacaoStatus.PENDING;

    @Enumerated(EnumType.STRING)
    private VerificacaoStatus verificacaoCriminalStatus = VerificacaoStatus.PENDING;

    private LocalDateTime verificacaoIdentidadeData;
    private LocalDateTime verificacaoCriminalData;

    private Boolean prestadorVerificado = false;
    private VerificacaoStatus verificacaoStatus;

    public Prestador() {
    }

    public void atualizarStatusVerificado() {

        this.prestadorVerificado =
                verificacaoIdentidadeStatus == verificacaoStatus.APPROVED &&
                        verificacaoCriminalStatus == verificacaoStatus.APPROVED;
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome(){
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public VerificacaoStatus getVerificacaoIdentidadeStatus() {
        return verificacaoIdentidadeStatus;
    }

    public void setVerificacaoIdentidadeStatus(VerificacaoStatus verificacaoIdentidadeStatus) {
        this.verificacaoIdentidadeStatus = verificacaoIdentidadeStatus;
    }

    public LocalDateTime getVerificacaoCriminalData() {
        return verificacaoCriminalData;
    }

    public void setVerificacaoCriminalData(LocalDateTime verificacaoCriminalData) {
        this.verificacaoCriminalData = verificacaoCriminalData;
    }

    public VerificacaoStatus getVerificacaoCriminalStatus() {
        return verificacaoCriminalStatus;
    }

    public void setVerificacaoCriminalStatus(VerificacaoStatus verificacaoCriminalStatus) {
        this.verificacaoCriminalStatus = verificacaoCriminalStatus;
    }

    public LocalDateTime getVerificacaoIdentidadeData() {
        return verificacaoIdentidadeData;
    }

    public void setVerificacaoIdentidadeData(LocalDateTime verificacaoIdentidadeData) {
        this.verificacaoIdentidadeData = verificacaoIdentidadeData;
    }

    public Boolean getPrestadorVerificado() {
        return prestadorVerificado;
    }

    public VerificacaoStatus getVerificacaoStatus() {
        return verificacaoStatus;
    }

    public void setVerificacaoStatus(VerificacaoStatus verificacaoStatus) {
        this.verificacaoStatus = verificacaoStatus;
    }

}