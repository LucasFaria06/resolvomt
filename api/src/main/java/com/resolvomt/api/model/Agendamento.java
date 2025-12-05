package com.resolvomt.api.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "agendamentos")
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataHorario;

    private String status;

    @ManyToOne
    @JoinColumn(name = "profissional_id")
    private Profissional profissional;

    public Agendamento() {}

    public Agendamento(LocalDateTime dataHorario, Profissional profissional) {
        this.dataHorario = dataHorario;
        this.profissional = profissional;
        this.status ="PENDENTE";
    }

    public Long getId() { return id;}
    public void setId(Long id) {this.id = id;}

    public LocalDateTime getDataHorario() { return dataHorario;}
    public void setDataHorario(LocalDateTime dataHorario) { this.dataHorario = dataHorario;}

    public String getStatus() { return status;}
    public void setStatus( String status) {this.status = status;} 
    
    public Profissional profissional() {return profissional;}
    public void setProfissional(Profissional profissional) {this.profissional = profissional;}
}