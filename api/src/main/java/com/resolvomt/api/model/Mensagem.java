package com.resolvomt.api.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Mensagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Conversa conversa;

    private String conteudo;

    private LocalDateTime enviadaEm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "remetente_id", nullable = false)
    private Usuario remetente;

    @Column(nullable = false)
    private boolean lida;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Conversa getConversa() { return conversa; }

    public void setConversa(Conversa conversa) { this.conversa = conversa; }

    public String getConteudo() { return conteudo; }

    public void setConteudo(String conteudo) { this.conteudo = conteudo; }

    public LocalDateTime getEnviadaEm() { return enviadaEm; }

    public void setEnviadaEm(LocalDateTime enviadaEm) { this.enviadaEm = enviadaEm; }

    public Usuario getRemetente() { return remetente; }

    public void setRemetente(Usuario remetente) { this.remetente = remetente; }

    public void setLida(boolean lida) { this.lida = lida; }

    public boolean isLida() { return lida; }

}
