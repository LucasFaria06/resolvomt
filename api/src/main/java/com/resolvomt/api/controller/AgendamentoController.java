package com.resolvomt.api.controller;

import com.resolvomt.api.model.Agendamento;
import com.resolvomt.api.repository.AgendamentoRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @PostMapping
    public ResponseEntity<Agendamento> criar(@RequestBody Agendamento agendamento) {

        Agendamento novoAgendamento = agendamentoRepository.save(agendamento);
        return new ResponseEntity<>(novoAgendamento, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<Agendamento>> listarTodos() {
        List<Agendamento> lista = agendamentoRepository.findAll();
        return ResponseEntity.ok(lista);
    }

}
