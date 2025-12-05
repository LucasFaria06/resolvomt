package com.resolvomt.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.resolvomt.api.model.Agendamento;
import com.resolvomt.api.repository.AgendamentoRepository;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {
    
    @Autowired
    private AgendamentoRepository repository;

    @GetMapping
    public List<Agendamento> listar() {
        return repository.findAll();
    }

    @PostMapping
    public Agendamento agendar(@RequestBody Agendamento agendamento) {

        System.out.println("Chegou data " + agendamento.getDataHorario());
        System.out.println("Chegou profissional " + agendamento.getProfissional());
        if (agendamento.getStatus() == null) {
            agendamento.setStatus("PENDENTE");
        }
        return repository.save(agendamento);
    }
}