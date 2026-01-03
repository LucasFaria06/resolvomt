package com.resolvomt.api.service;

import com.resolvomt.api.dto.mensagem.MensagemRequestDTO;
import com.resolvomt.api.dto.mensagem.MensagemResponseDTO;
import com.resolvomt.api.exception.ResourceNotFoundException;
import com.resolvomt.api.model.Conversa;
import com.resolvomt.api.model.Mensagem;
import com.resolvomt.api.model.Usuario;
import com.resolvomt.api.repository.ConversaRepository;
import com.resolvomt.api.repository.MensagemRepository;
import com.resolvomt.api.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MensagemService {

    private final MensagemRepository mensagemRepository;
    private final ConversaRepository conversaRepository;
    private final UsuarioRepository usuarioRepository;

    public MensagemService(MensagemRepository mensagemRepository,
                           ConversaRepository conversaRepository,
                           UsuarioRepository usuarioRepository) {
        this.mensagemRepository = mensagemRepository;
        this.conversaRepository = conversaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public MensagemResponseDTO enviarMensagem(Long conversaId, MensagemRequestDTO dto, Long usuarioId) {
        Conversa conversa = conversaRepository.findById(conversaId)
                .orElseThrow(() -> new ResourceNotFoundException("Conversa não encontrada"));

        Usuario remetente = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        // Verificar se o usuário participa da conversa
        if (!conversa.usuarioParticipa(usuarioId)) {
            throw new IllegalArgumentException("Usuário não participa desta conversa");
        }

        Mensagem mensagem = new Mensagem();
        mensagem.setConversa(conversa);
        mensagem.setRemetente(remetente);
        mensagem.setConteudo(dto.conteudo());

        mensagem = mensagemRepository.save(mensagem);

        return new MensagemResponseDTO(mensagem);
    }

    @Transactional(readOnly = true)
    public List<MensagemResponseDTO> listarMensagens(Long conversaId, Long usuarioId) {
        Conversa conversa = conversaRepository.findById(conversaId)
                .orElseThrow(() -> new ResourceNotFoundException("Conversa não encontrada"));

        // Verificar se o usuário participa da conversa
        if (!conversa.usuarioParticipa(usuarioId)) {
            throw new IllegalArgumentException("Usuário não participa desta conversa");
        }

        List<Mensagem> mensagens = mensagemRepository.findByConversaIdOrderByEnviadaEmAsc(conversaId);

        return mensagens.stream()
                .map(MensagemResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void marcarComoLida(Long mensagemId, Long usuarioId) {
        Mensagem mensagem = mensagemRepository.findById(mensagemId)
                .orElseThrow(() -> new ResourceNotFoundException("Mensagem não encontrada"));

        // Verificar se o usuário participa da conversa
        Conversa conversa = mensagem.getConversa();
        if (!conversa.usuarioParticipa(usuarioId)) {
            throw new IllegalArgumentException("Usuário não participa desta conversa");
        }

        // Não pode marcar próprias mensagens como lidas
        if (mensagem.getRemetente().getId().equals(usuarioId)) {
            throw new IllegalArgumentException("Não é possível marcar própria mensagem como lida");
        }

        mensagem.setLida(true);
        mensagemRepository.save(mensagem);
    }

    @Transactional
    public void marcarTodasComoLidas(Long conversaId, Long usuarioId) {
        Conversa conversa = conversaRepository.findById(conversaId)
                .orElseThrow(() -> new ResourceNotFoundException("Conversa não encontrada"));

        // Verificar se o usuário participa da conversa
        if (!conversa.usuarioParticipa(usuarioId)) {
            throw new IllegalArgumentException("Usuário não participa desta conversa");
        }

        mensagemRepository.marcarTodasComoLidasPorConversa(conversaId, usuarioId);
    }

    @Transactional(readOnly = true)
    public Long contarNaoLidas(Long conversaId, Long usuarioId) {
        Conversa conversa = conversaRepository.findById(conversaId)
                .orElseThrow(() -> new ResourceNotFoundException("Conversa não encontrada"));

        // Verificar se o usuário participa da conversa
        if (!conversa.usuarioParticipa(usuarioId)) {
            throw new IllegalArgumentException("Usuário não participa desta conversa");
        }

        return mensagemRepository.countNaoLidasPorConversa(conversaId, usuarioId);
    }
}
