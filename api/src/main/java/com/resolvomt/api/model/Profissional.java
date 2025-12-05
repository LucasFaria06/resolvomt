package com.resolvomt.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.*;

@Entity
@Table(name = "/profissionais")
public class Profissional {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String profissao;
    private Double precoHora;
    private String cidade;

    public Profissional() {}

    public Profissional (String nome, String profissao, Double precoHora, String cidade){
        this.nome = nome;
        this.profissao = profissao;
        this.precoHora = precoHora;
        this.cidade = cidade;
    }

    public Long getId() { return id;}
    public void setId(Long id ) { this.id = id; }

    public String getNome() { return nome;}
    public void setNome(String nome ) { this.nome = nome; }

    public String getProfissao() { return profissao; }
    public void setProfissao(String profissao ) { this.profissao = profissao; }

    public Double precoHora() { return precoHora; }
    public void setPrecoHora(Double precoHora ) { this.precoHora = precoHora; }

    public String getCidade() {return cidade; }
    public void setCidade(String cidade ) { this.cidade = cidade; }

    
}
