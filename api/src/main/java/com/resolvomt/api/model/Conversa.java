package com.resolvomt.api.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "conversas")
public class Conversa {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Agendamento agendamento;

    @OneToMany(mappedBy = "conversa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mensagem> mensagens;

    private LocalDateTime criadaEm = LocalDateTime.now();

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Agendamento getAgendamento() { return agendamento; }

    public void setAgendamento(Agendamento agendamento) { this.agendamento = agendamento; }

    public List<Mensagem> getMensagens() { return mensagens; }

    public void setMensagens(List<Mensagem> mensagens) { this.mensagens = mensagens; }

    public LocalDateTime getCriadaEm() { return criadaEm; }

    public void setCriadaEm(LocalDateTime criadaEm) { this.criadaEm = criadaEm; }
}
