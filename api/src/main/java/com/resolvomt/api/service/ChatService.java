package com.resolvomt.api.service;

import com.resolvomt.api.dto.mensagem.MensagemRequestDTO;
import com.resolvomt.api.model.Agendamento;
import com.resolvomt.api.model.Conversa;
import com.resolvomt.api.model.Mensagem;
import com.resolvomt.api.model.Usuario;
import com.resolvomt.api.repository.ConversaRepository;
import com.resolvomt.api.repository.MensagemRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatService {

    private final ConversaRepository conversaRepository;
    private final MensagemRepository mensagemRepository;
    private final UsuarioService usuarioService;
    private final AgendamentoService agendamentoService;

    public ChatService(
            ConversaRepository conversaRepository,
            MensagemRepository mensagemRepository,
            UsuarioService usuarioService,
            AgendamentoService agendamentoService
    ) {
        this.conversaRepository = conversaRepository;
        this.mensagemRepository = mensagemRepository;
        this.usuarioService = usuarioService;
        this.agendamentoService = agendamentoService;
    }

    public List<Mensagem> listarMensagem(Long agendamentoId, String email) {

        Agendamento agendamento = agendamentoService.buscarInterno(agendamentoId);
        validarAcesso(agendamento, email);

        Conversa conversa = conversaRepository
                .findByAgendamentoId(agendamentoId)
                .orElseThrow(() -> new RuntimeException("Conversa não encontrada"));

        return mensagemRepository
                .findByConversaIdOrderByEnviadaEmAsc(conversa.getId());
    }

    public Mensagem enviarMensagem(Long agendamentoId, String email, MensagemRequestDTO dto) {

        Agendamento agendamento = agendamentoService.buscarInterno(agendamentoId);
        validarAcesso(agendamento, email);

        Conversa conversa = conversaRepository
                .findByAgendamentoId(agendamentoId)
                .orElseGet(() -> {
                    Conversa nova = new Conversa();
                    nova.setAgendamento(agendamento);
                    nova.setCriadaEm(LocalDateTime.now());
                    return conversaRepository.save(nova);
                });

        Usuario remetente = usuarioService.buscarPorEmail(email);

        Mensagem msg = new Mensagem();
        msg.setConversa(conversa);
        msg.setRemetente(remetente);
        msg.setConteudo(dto.conteudo());
        msg.setEnviadaEm(LocalDateTime.now());
        msg.setLida(false);

        return mensagemRepository.save(msg);
    }

    private void validarAcesso(Agendamento agendamento, String email) {
        boolean cliente = agendamento.getCliente()
                .getUsuario()
                .getEmail()
                .equals(email);

        boolean prestador = agendamento.getPrestador()
                .getUsuario()
                .getEmail()
                .equals(email);

        if (!cliente && !prestador) {
            throw new RuntimeException("Você não tem acesso a este chat");
        }
    }
}
