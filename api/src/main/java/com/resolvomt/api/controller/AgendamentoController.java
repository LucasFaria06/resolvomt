package com.resolvomt.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.resolvomt.api.dto.AgendamentoRequest;
import com.resolvomt.api.dto.AgendamentoResponse;
import com.resolvomt.api.dto.AtualizacaoStatusRequest;
import com.resolvomt.api.model.Agendamento;
import com.resolvomt.api.model.Usuario;
import com.resolvomt.api.service.AgendamentoService;

import jakarta.validation.Valid;

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

    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoResponse> buscarPorId(@PathVariable Long id) {
        Agendamento agendamento = service.buscarPorId(id);
        return ResponseEntity.ok(new AgendamentoResponse(agendamento));
    }

    @PostMapping
    public ResponseEntity<AgendamentoResponse> criar(@RequestBody @Valid AgendamentoRequest request) {
       
        Agendamento agendamento = new Agendamento();
        agendamento.setDescricao(request.descricao());
        agendamento.setDataServico(request.dataServico());
        agendamento.setValorTotal(request.valorTotal());
        agendamento.setStatus("PENDENTE");

        Usuario cliente = new Usuario();
        cliente.setId(request.idCliente());
        agendamento.setCliente(cliente);

        Usuario prestador = new Usuario();
        prestador.setId(request.idPrestador());
        agendamento.setPrestador(prestador);

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelar(@PathVariable Long id){
        service.cancelar(id);
        return ResponseEntity.noContent().build();
    }
}