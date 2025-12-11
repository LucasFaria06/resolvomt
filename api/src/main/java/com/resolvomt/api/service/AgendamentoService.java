package com.resolvomt.api.service;

import java.util.List;

import com.resolvomt.api.dto.AgendamentoRequest; 
import com.resolvomt.api.model.Usuario;             
import com.resolvomt.api.repository.UsuarioRepository; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.resolvomt.api.model.Agendamento;
import com.resolvomt.api.repository.AgendamentoRepository;

@Service
public class AgendamentoService {

    @Autowired
    private AgendamentoRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Agendamento> listarTodos() {
        return repository.findAll();
    }

    public Agendamento criar(AgendamentoRequest dto) {
        
        Usuario cliente = usuarioRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado id: " + dto.getClienteId()));

        Usuario prestador = usuarioRepository.findById(dto.getPrestadorId())
                .orElseThrow(() -> new RuntimeException("Prestador não encontrado id: " + dto.getPrestadorId()));

        Agendamento agendamento = new Agendamento();
        agendamento.setCliente(cliente);
        agendamento.setPrestador(prestador);
        agendamento.setDataServico(dto.getDataServico());
        agendamento.setValorTotal(dto.getValorTotal());
        
        agendamento.setDescricao(dto.getDescricao()); 
        
        agendamento.setStatus("PENDENTE");

        return repository.save(agendamento);
    }
    
    public Agendamento buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));
    }

    public void cancelar(Long id) {
        Agendamento agendamento = buscarPorId(id);

        if(!"PENDENTE".equals(agendamento.getStatus())) {
            throw new RuntimeException("Não é possível cancelar agendamentos que não estão PENDENTES.");
        }
        
        repository.delete(agendamento);
    }
}