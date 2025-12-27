package com.resolvomt.api.service;

import com.resolvomt.api.dto.agendamento.AgendamentoRequestDTO;
import com.resolvomt.api.enums.StatusAgendamento;
import com.resolvomt.api.model.*;
import com.resolvomt.api.repository.AgendamentoRepository;
import com.resolvomt.api.repository.ServicoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public Agendamento criar(String emailCliente, AgendamentoRequestDTO dto) {

        if (dto.dataHora().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Não é possível agendar um horário no passado");
        }

        Cliente cliente = clienteService.buscarPorEmailUsuario(emailCliente);

        Servico servico = servicoRepository.findById(dto.servicoId())
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

        if (!servico.isAtivo()) {
            throw new RuntimeException("Este serviço não está disponível para agendamento");
        }

        Prestador prestador = servico.getPrestador();

        boolean conflito = agendamentoRepository
                .existsByPrestadorIdAndDataHora(prestador.getId(), dto.dataHora());

        if (conflito) {
            throw new RuntimeException("Este horário já está ocupado para o prestador");
        }

        Agendamento agendamento = new Agendamento();
        agendamento.setCliente(cliente);
        agendamento.setPrestador(prestador);
        agendamento.setServico(servico);
        agendamento.setDataHora(dto.dataHora());
        agendamento.setStatus(StatusAgendamento.CRIADO);

        return agendamentoRepository.save(agendamento);
    }

    public List<Agendamento> listarCliente(String emailCliente) {
        return agendamentoRepository.findByClienteUsuarioEmail(emailCliente);
    }


    public List<Agendamento> listarPrestador(String emailPrestador) {
        return agendamentoRepository.findByPrestadorUsuarioEmail(emailPrestador);
    }

    public void confirmar(Long id, String emailPrestador) {
        Agendamento agendamento = buscarInterno(id);

        if (!agendamento.getPrestador().getUsuario().getEmail().equals(emailPrestador)) {
            throw new RuntimeException("Você não pode confirmar este agendamento");
        }

        agendamento.setStatus(StatusAgendamento.CONFIRMADO);
        agendamentoRepository.save(agendamento);
    }

    public void cancelar(Long id, String emailPrestador) {
        Agendamento agendamento = buscarInterno(id);

        if (!agendamento.getPrestador().getUsuario().getEmail().equals(emailPrestador)) {
            throw new RuntimeException("Você não pode cancelar este agendamento");
        }

        if (agendamento.getStatus() == StatusAgendamento.CANCELADO) {
            throw new RuntimeException("Agendamento já está cancelado");
        }

        agendamento.setStatus(StatusAgendamento.CANCELADO);
        agendamentoRepository.save(agendamento);
    }

    public Agendamento buscarInterno(Long id) {
        return agendamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));
    }
}
