package com.resolvomt.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.resolvomt.api.dto.AgendamentoResponse;
import com.resolvomt.api.dto.AtualizacaoStatusRequest;
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
    public ResponseEntity<AgendamentoResponse> criar(@RequestBody com.resolvomt.api.dto.AgendamentoRequestDTO dto) {
            Usuario clienteReal = usuarioRepository.findById(dto.getClienteId())
            .orElseThrow(() -> new RuntimeException("Cliente não encontrado id: " + dto.getClienteId()));

        Usuario prestadorReal = usuarioRepository.findById(dto.getPrestadorId())
            .orElseThrow(() -> new RuntimeException("Prestador não encontrado id: " + dto.getPrestadorId()));

        Agendamento agendamento = new Agendamento();
        agendamento.setCliente(clienteReal);
        agendamento.setPrestador(prestadorReal);
        agendamento.setDataServico(dto.getDataServico());
        agendamento.setDescricao(dto.getDescricao());
        agendamento.setValorTotal(dto.getValorTotal());

        Agendamento salvo = agendamentoRepository.save(agendamento);

        return new ResponseEntity<>(new AgendamentoResponse(salvo), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AgendamentoResponse>> listarTodos(){
        List<Agendamento> agendamentosDoBanco = agendamentoRepository.findAll();
        
        List<AgendamentoResponse> listaSegura = agendamentosDoBanco.stream()
             .map(AgendamentoResponse::new)
             .toList();

        return ResponseEntity.ok(listaSegura);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<AgendamentoResponse> atualizarStatus(
        @PathVariable Long id,
        @RequestBody AtualizacaoStatusRequest request) {

        Agendamento agendamento = agendamentoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Agendamento não encontrado!"));
        
        agendamento.setStatus(request.getStatus());

        agendamentoRepository.save(agendamento);

        return ResponseEntity.ok(new AgendamentoResponse(agendamento));
        }
    
}