package com.resolvomt.api.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "servicos")
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prestador_id", nullable = false)
    private Prestador prestador;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, length = 500)
    private String descricao;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(nullable = false)
    private Integer duracaoMinutos;

    public Long getId() {  return id;}

    public Prestador getPrestador() { return prestador;}

    public void setPrestador(Prestador prestador) { this.prestador = prestador;}

    public String getNome() { return nome;}

    public void setNome(String nome) { this.nome = nome;}

    public String getDescricao() { return descricao;}

    public void setDescricao(String descricao) { this.descricao = descricao;}

    public BigDecimal getValor() { return valor;}

    public void setValor(BigDecimal valor) { this.valor = valor;}

    public Integer getDuracaoMinutos() { return duracaoMinutos;}

    public void setDuracaoMinutos(Integer duracaoMinutos) { this.duracaoMinutos = duracaoMinutos;}
}
