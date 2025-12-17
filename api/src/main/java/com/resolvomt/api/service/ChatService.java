package com.resolvomt.api.service;

import com.resolvomt.api.dto.mensagem.MensagemRequestDTO;
import com.resolvomt.api.model.Conversa;
import com.resolvomt.api.model.Mensagem;
import com.resolvomt.api.model.Usuario;
import com.resolvomt.api.repository.ConversaRepository;
import com.resolvomt.api.repository.MensagemRepository;

import java.time.LocalDateTime;
import java.util.List;

public class ChatService {

    private final ConversaRepository conversaRepository;
    private final MensagemRepository mensagemRepository;
    private final UsuarioService usuarioService;

    public ChatService(ConversaRepository conversaRepository, MensagemRepository mensagemRepository, UsuarioService usuarioService) {
        this.conversaRepository = conversaRepository;
        this.mensagemRepository = mensagemRepository;
        this.usuarioService = usuarioService;
    }

    public List<Mensagem> listarMensagem(Long agendamentoId) {
        Conversa conversa = conversaRepository.findByAgendamentoId(agendamentoId)
                .orElseThrow(() -> new RuntimeException("Conversa não encontrada"));

        return mensagemRepository.findByConversaIdOrderByEnviadaEmAsc(conversa.getId());
    }

    public Mensagem enviarMensagem(Long agendamentoId, String email, MensagemRequestDTO dto) {
        Conversa conversa = conversaRepository.findByAgendamentoId(agendamentoId)
                .orElseThrow(() -> new RuntimeException("Conversa não encontrada"));

        Usuario rementente = usuarioService.buscarPorEmail(email);

        Mensagem msg = new Mensagem();
        msg.setConversa(conversa);
        msg.setRemetente(rementente);
        msg.setConteudo(dto.conteudo());
        msg.setEnviadaEm(LocalDateTime.now());
        msg.setLida(false);

        return mensagemRepository.save(msg);
    }
}
