package com.resolvomt.api.model;

import com.resolvomt.api.service.AgendamentoService;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Conversa {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Agendamento agendamento;

    @OneToMany(mappedBy = "conversa", cascade = CascadeType.ALL)
    private List<Mensagem> mensgens;

    private LocalDateTime criadaEm;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Agendamento getAgendamento() { return agendamento; }

    public void setAgendamento(Agendamento agendamento) { this.agendamento = agendamento; }

    public List<Mensagem> getMensgens() { return mensgens; }

    public void setMensgens(List<Mensagem> mensgens) { this.mensgens = mensgens; }

    public LocalDateTime getCriadaEm() { return criadaEm; }

    public void setCriadaEm(LocalDateTime criadaEm) { this.criadaEm = criadaEm; }
}
