package com.resolvomt.api.service;

import com.resolvomt.api.dto.agendamento.AgendamentoCreateRequestDTO;
import com.resolvomt.api.enums.StatusAgendamento;
import com.resolvomt.api.model.Agendamento;
import com.resolvomt.api.model.Cliente;
import com.resolvomt.api.model.Servico;
import com.resolvomt.api.repository.AgendamentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final ClienteService clienteService;
    private final ServicoService servicoService;
    private final AssinaturaService assinaturaService;

    public AgendamentoService(AgendamentoRepository agendamentoRepository,
                              ClienteService clienteService,
                              ServicoService servicoService, AssinaturaService assinaturaService) {
        this.agendamentoRepository = agendamentoRepository;
        this.clienteService = clienteService;
        this.servicoService = servicoService;
        this.assinaturaService = assinaturaService;
    }

    @Transactional
    public Agendamento criar(AgendamentoCreateRequestDTO dto, String emailCliente) {
        Cliente cliente = clienteService.buscarPorEmailUsuario(emailCliente);
        Servico servico = servicoService.buscarPorId(dto.servicoId());

        validarLimiteAgendamento(servico.getPrestador().getId());

        if (!servico.isAtivo()) {
            throw new IllegalArgumentException("Serviço não está disponível");
        }

        if (!servico.getPrestador().isVerificado()) {
            throw new IllegalArgumentException("Prestador não está verificado");
        }

        if (!servico.getPrestador().isAtivo()) {
            throw new IllegalArgumentException("Prestador não está ativo");
        }

        LocalDateTime dataFim = dto.dataHora().plusMinutes(servico.getDuracaoMinutos());
        boolean conflito = agendamentoRepository.existsConflito(
                servico.getPrestador().getId(),
                dto.dataHora(),
                dataFim,
                StatusAgendamento.CANCELADO
        );

        if (conflito) {
            throw new IllegalArgumentException("Horário não disponível para este prestador");
        }

        Agendamento agendamento = new Agendamento();
        agendamento.setCliente(cliente);
        agendamento.setServico(servico);
        agendamento.setPrestador(servico.getPrestador());
        agendamento.setDataHora(dto.dataHora());
        agendamento.setObservacoes(dto.observacoes());
        agendamento.setValor(servico.getValor());
        agendamento.setDuracaoMinutos(servico.getDuracaoMinutos());
        agendamento.setStatus(StatusAgendamento.CRIADO);

        return agendamentoRepository.save(agendamento);
    }

    private void validarLimiteAgendamento(Long prestadorId) {
        LocalDateTime inicioMes = LocalDateTime.now()
                .withDayOfMonth(1)
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);

        long agendamentosNoMes =
                agendamentoRepository.countByPrestadorIdAndDataHoraAfter(prestadorId, inicioMes);

        boolean podeCriar = assinaturaService
                .podeCriarAgendamento(prestadorId, (int) agendamentosNoMes);

        if (!podeCriar) {
            throw new IllegalStateException(
                    "Limite de agendamentos do plano atingido"
            );
        }
    }


    @Transactional(readOnly = true)
    public List<Agendamento> listarPorCliente(String emailCliente) {
        Cliente cliente = clienteService.buscarPorEmailUsuario(emailCliente);
        return agendamentoRepository.findByClienteIdWithDetails(cliente.getId());
    }

    @Transactional(readOnly = true)
    public List<Agendamento> listarPorPrestador(String emailPrestador) {
        return agendamentoRepository.findByPrestadorEmailWithDetails(emailPrestador);
    }

    @Transactional
    public Agendamento confirmar(Long id, String emailPrestador) {
        Agendamento agendamento = buscarPorIdEPrestador(id, emailPrestador);

        if (agendamento.getStatus() != StatusAgendamento.CRIADO) {
            throw new IllegalArgumentException("Apenas agendamentos com status CRIADO podem ser confirmados");
        }

        agendamento.setStatus(StatusAgendamento.CONFIRMADO);
        return agendamentoRepository.save(agendamento);
    }

    @Transactional
    public Agendamento cancelarPorPrestador(Long id, String emailPrestador) {
        Agendamento agendamento = buscarPorIdEPrestador(id, emailPrestador);

        if (agendamento.getStatus() == StatusAgendamento.CANCELADO) {
            throw new IllegalArgumentException("Agendamento já está cancelado");
        }

        agendamento.setStatus(StatusAgendamento.CANCELADO);
        return agendamentoRepository.save(agendamento);
    }

    private Agendamento buscarPorIdEPrestador(Long id, String emailPrestador) {
        return agendamentoRepository.findByIdAndPrestadorEmailWithDetails(id, emailPrestador)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));
    }
}