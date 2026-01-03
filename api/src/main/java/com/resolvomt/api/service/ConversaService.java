package com.resolvomt.api.service;

import com.resolvomt.api.dto.mensagem.ConversaResponseDTO;
import com.resolvomt.api.exception.ResourceNotFoundException;
import com.resolvomt.api.model.Agendamento;
import com.resolvomt.api.model.Conversa;
import com.resolvomt.api.repository.AgendamentoRepository;
import com.resolvomt.api.repository.ConversaRepository;
import com.resolvomt.api.repository.MensagemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConversaService {

    private final ConversaRepository conversaRepository;
    private final AgendamentoRepository agendamentoRepository;
    private final MensagemRepository mensagemRepository;

    public ConversaService(ConversaRepository conversaRepository,
                           AgendamentoRepository agendamentoRepository,
                           MensagemRepository mensagemRepository) {
        this.conversaRepository = conversaRepository;
        this.agendamentoRepository = agendamentoRepository;
        this.mensagemRepository = mensagemRepository;
    }

    @Transactional
    public ConversaResponseDTO criarConversa(Long agendamentoId, Long usuarioId) {
        if (conversaRepository.existsByAgendamentoId(agendamentoId)) {
            throw new IllegalArgumentException("Já existe uma conversa para este agendamento");
        }

        Agendamento agendamento = agendamentoRepository.findById(agendamentoId)
                .orElseThrow(() -> new ResourceNotFoundException("Agendamento não encontrado"));

        boolean isCliente = agendamento.getCliente() != null &&
                agendamento.getCliente().getUsuario() != null &&
                agendamento.getCliente().getUsuario().getId().equals(usuarioId);

        boolean isPrestador = agendamento.getPrestador() != null &&
                agendamento.getPrestador().getUsuario() != null &&
                agendamento.getPrestador().getUsuario().getId().equals(usuarioId);

        if (!isCliente && !isPrestador) {
            throw new IllegalArgumentException("Usuário não participa deste agendamento");
        }

        Conversa conversa = new Conversa();
        conversa.setAgendamento(agendamento);

        conversa = conversaRepository.save(conversa);

        return new ConversaResponseDTO(conversa, 0L);
    }

    @Transactional(readOnly = true)
    public ConversaResponseDTO buscarPorId(Long id, Long usuarioId) {
        Conversa conversa = conversaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Conversa não encontrada"));

        if (!conversa.usuarioParticipa(usuarioId)) {
            throw new IllegalArgumentException("Usuário não participa desta conversa");
        }

        Long naoLidas = mensagemRepository.countNaoLidasPorConversa(id, usuarioId);

        return new ConversaResponseDTO(conversa, naoLidas);
    }

    @Transactional(readOnly = true)
    public ConversaResponseDTO buscarPorAgendamento(Long agendamentoId, Long usuarioId) {
        Conversa conversa = conversaRepository.findByAgendamentoId(agendamentoId)
                .orElseThrow(() -> new ResourceNotFoundException("Conversa não encontrada para este agendamento"));

        if (!conversa.usuarioParticipa(usuarioId)) {
            throw new IllegalArgumentException("Usuário não participa desta conversa");
        }

        Long naoLidas = mensagemRepository.countNaoLidasPorConversa(conversa.getId(), usuarioId);

        return new ConversaResponseDTO(conversa, naoLidas);
    }

    @Transactional(readOnly = true)
    public List<ConversaResponseDTO> listarConversasDoUsuario(Long usuarioId) {
        List<Conversa> conversas = conversaRepository.findByUsuarioId(usuarioId);

        return conversas.stream()
                .map(conversa -> {
                    Long naoLidas = mensagemRepository.countNaoLidasPorConversa(conversa.getId(), usuarioId);
                    return new ConversaResponseDTO(conversa, naoLidas);
                })
                .collect(Collectors.toList());
    }
}
