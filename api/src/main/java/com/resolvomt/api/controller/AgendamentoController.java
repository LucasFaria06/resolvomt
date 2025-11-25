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
    public ResponseEntity<AgendamentoResponse> criar(@RequestBody com.resolvomt.api.dto.AgendamentoRequestDTO dto) {
        
        // 1. O DTO nos entregou os IDs (Long). Agora buscamos os objetos reais no banco.
        Usuario clienteReal = usuarioRepository.findById(dto.getClienteId())
            .orElseThrow(() -> new RuntimeException("Cliente não encontrado id: " + dto.getClienteId()));

        Usuario prestadorReal = usuarioRepository.findById(dto.getPrestadorId())
            .orElseThrow(() -> new RuntimeException("Prestador não encontrado id: " + dto.getPrestadorId()));

        // 2. Montamos o Agendamento (Entity) para salvar
        Agendamento agendamento = new Agendamento();
        agendamento.setCliente(clienteReal);
        agendamento.setPrestador(prestadorReal);
        agendamento.setDataServico(dto.getDataServico());
        agendamento.setDescricao(dto.getDescricao());
        agendamento.setValorTotal(dto.getValorTotal());
        // status já é "PENDENTE" por padrão na classe Agendamento

        // 3. Salvamos no banco
        Agendamento salvo = agendamentoRepository.save(agendamento);

        // 4. Convertemos para o seu Response (que já existe e é ótimo)
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
}