package com.resolvomt.api.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "conversas")
public class Conversa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "agendamento_id", nullable = false, unique = true)
    private Agendamento agendamento;

    @OneToMany(mappedBy = "conversa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mensagem> mensagens = new ArrayList<>();

    @Column(name = "criada_em", nullable = false)
    private LocalDateTime criadaEm;

    @PrePersist
    public void prePersist() {
        if (this.criadaEm == null) {
            this.criadaEm = LocalDateTime.now();
        }
    }

    public Cliente getCliente() {
        return agendamento != null ? agendamento.getCliente() : null;
    }

    public Prestador getPrestador() {
        return agendamento != null ? agendamento.getPrestador() : null;
    }

    public boolean usuarioParticipa(Long usuarioId) {
        if (agendamento == null) return false;

        Cliente cliente = agendamento.getCliente();
        Prestador prestador = agendamento.getPrestador();

        boolean isCliente = cliente != null &&
                cliente.getUsuario() != null &&
                cliente.getUsuario().getId().equals(usuarioId);

        boolean isPrestador = prestador != null &&
                prestador.getUsuario() != null &&
                prestador.getUsuario().getId().equals(usuarioId);

        return isCliente || isPrestador;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Agendamento getAgendamento() { return agendamento; }

    public void setAgendamento(Agendamento agendamento) { this.agendamento = agendamento; }

    public List<Mensagem> getMensagens() { return mensagens; }

    public void setMensagens(List<Mensagem> mensagens) { this.mensagens = mensagens; }

    public LocalDateTime getCriadaEm() { return criadaEm; }

    public void setCriadaEm(LocalDateTime criadaEm) { this.criadaEm = criadaEm; }
}
