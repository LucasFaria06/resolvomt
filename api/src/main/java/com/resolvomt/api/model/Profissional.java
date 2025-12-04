package com.resolvomt.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
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
    public String getNome() { return nome;}
    public String getProfissao() { return profissao; }
    public Double precoHora() { return precoHora; }
    public String getCidade() {return cidade; }
    
    public void setId(Long id) { this.id = id;}
}
