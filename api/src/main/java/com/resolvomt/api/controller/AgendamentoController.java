package com.resolvomt.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.resolvomt.api.dto.AgendamentoResponse;
import com.resolvomt.api.model.Agendamento;
import com.resolvomt.api.model.Usuario;
import com.resolvomt.api.repository.AgendamentoRepository;
import com.resolvomt.api.repository.UsuarioRepository;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController{
    
    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<AgendamentoResponse> criar(@RequestBody Agendamento agendamento) {
        
        Usuario clienteReal = usuarioRepository.findById(agendamento.getCliente().getId())
            .orElseThrow(() -> new RuntimeException("Cliente não encontrado no banco!"));

        Usuario prestadorReal = usuarioRepository.findById(agendamento.getPrestador().getId())
           .orElseThrow(() -> new RuntimeException("Prestador não encontrado no banco!"));

        agendamento.setCliente(clienteReal);
        agendamento.setPrestador(prestadorReal);

        Agendamento salvo = agendamentoRepository.save(agendamento);

        AgendamentoResponse response = new AgendamentoResponse(salvo);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AgendamentoResponse>> listarTodos(){
        List<Agendamento> agendamentosDoBanco = agendamentoRepository.findAll();
        
        List<AgendamentoResponse> listaSegura = agendamentosDoBanco.stream()
             .map(AgendamentoResponse::new)
             .toList();

        return ResponseEntity.ok(listaSegura);
    }
}