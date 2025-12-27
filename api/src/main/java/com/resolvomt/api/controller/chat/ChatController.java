package com.resolvomt.api.controller.chat;

import com.resolvomt.api.dto.mensagem.MensagemRequestDTO;
import com.resolvomt.api.dto.mensagem.MensagemResponseDTO;
import com.resolvomt.api.service.ChatService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/agendamento/{agendamentoId}/mensagens")
    public MensagemResponseDTO enviar(
            @PathVariable Long agendamentoId,
            @RequestBody MensagemRequestDTO dto,
            Authentication auth
    ) {
        return new MensagemResponseDTO(
                chatService.enviarMensagem(agendamentoId, auth.getName(), dto)
        );
    }

    @GetMapping("/agendamento/{agendamentoId}/mensagens")
    public List<MensagemResponseDTO> listar(
            @PathVariable Long agendamentoId,
            Authentication auth
    ) {
        return chatService.listarMensagem(agendamentoId, auth.getName())
                .stream()
                .map(MensagemResponseDTO::new)
                .toList();
    }
}
