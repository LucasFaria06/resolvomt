package com.resolvomt.api.service;

import com.resolvomt.api.dto.agendamento.AgendamentoRequestDTO;
import com.resolvomt.api.enums.StatusAgendamento;
import com.resolvomt.api.model.*;
import com.resolvomt.api.repository.AgendamentoRepository;
import com.resolvomt.api.repository.ServicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final ClienteService clienteService;
    private final ServicoRepository servicoRepository;

    public AgendamentoService(
            AgendamentoRepository agendamentoRepository,
            ClienteService clienteService,
            ServicoRepository servicoRepository
    ) {
        this.agendamentoRepository = agendamentoRepository;
        this.clienteService = clienteService;
        this.servicoRepository = servicoRepository;
    }

    // ================= CLIENTE =================

    public Agendamento criar(String emailCliente, AgendamentoRequestDTO dto) {

        Cliente cliente = clienteService.buscarPorEmailUsuario(emailCliente);

        Servico servico = servicoRepository.findById(dto.servicoId())
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

        Agendamento agendamento = new Agendamento();
        agendamento.setCliente(cliente);
        agendamento.setPrestador(servico.getPrestador());
        agendamento.setServico(servico);
        agendamento.setDataHora(dto.dataHora());
        agendamento.setStatus(StatusAgendamento.CRIADO);

        return agendamentoRepository.save(agendamento);
    }

    public List<Agendamento> listarCliente(String emailCliente) {
        return agendamentoRepository.findByClienteUsuarioEmail(emailCliente);
    }

    // ================= PRESTADOR =================

    public List<Agendamento> listarPrestador(String emailPrestador) {
        return agendamentoRepository.findByPrestadorUsuarioEmail(emailPrestador);
    }

    public void confirmar(Long id) {
        Agendamento agendamento = buscar(id);
        agendamento.setStatus(StatusAgendamento.CONFIRMADO);
        agendamentoRepository.save(agendamento);
    }

    public void cancelar(Long id) {
        Agendamento agendamento = buscar(id);
        agendamento.setStatus(StatusAgendamento.CANCELADO);
        agendamentoRepository.save(agendamento);
    }

    // ================= INTERNO =================

    private Agendamento buscar(Long id) {
        return agendamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));
    }
}
