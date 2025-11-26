package com.resolvomt.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.resolvomt.api.dto.AgendamentoResponse;
import com.resolvomt.api.dto.AtualizacaoStatusRequest;
import com.resolvomt.api.model.Agendamento;
import com.resolvomt.api.service.AgendamentoService;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {

    @Autowired
    private AgendamentoService service;

    @GetMapping
    public ResponseEntity<List<AgendamentoResponse>> listar() {
        List<AgendamentoResponse> lista = service.listarTodos().stream()
                .map(AgendamentoResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @PostMapping
    public ResponseEntity<AgendamentoResponse> criar(@RequestBody Agendamento agendamento) {
        Agendamento salvo = service.cadastrar(agendamento);
        return new ResponseEntity<>(new AgendamentoResponse(salvo), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<AgendamentoResponse> atualizarStatus(@PathVariable Long id, @RequestBody AtualizacaoStatusRequest request) {
        Agendamento agendamento = service.buscarPorId(id);
        
        agendamento.setStatus(request.getStatus());
        
        Agendamento atualizado = service.cadastrar(agendamento);
        
        return ResponseEntity.ok(new AgendamentoResponse(atualizado));
    }
}