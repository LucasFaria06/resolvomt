package com.resolvomt.api.controller.mensagem;

import com.resolvomt.api.dto.mensagem.MensagemRequestDTO;
import com.resolvomt.api.dto.mensagem.MensagemResponseDTO;
import com.resolvomt.api.security.UserPrincipal;
import com.resolvomt.api.service.MensagemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mensagens")
@Tag(name = "Mensagens", description = "Endpoints para gerenciamento de mensagens")
@SecurityRequirement(name = "bearerAuth")
public class MensagemController {

    private final MensagemService mensagemService;

    public MensagemController(MensagemService mensagemService) {
        this.mensagemService = mensagemService;
    }

    @PostMapping("/conversa/{conversaId}")
    @Operation(summary = "Enviar mensagem em uma conversa")
    public ResponseEntity<MensagemResponseDTO> enviarMensagem(
            @PathVariable Long conversaId,
            @Valid @RequestBody MensagemRequestDTO dto,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        MensagemResponseDTO mensagem = mensagemService.enviarMensagem(conversaId, dto, userPrincipal.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(mensagem);
    }

    @GetMapping("/conversa/{conversaId}")
    @Operation(summary = "Listar todas as mensagens de uma conversa")
    public ResponseEntity<List<MensagemResponseDTO>> listarMensagens(
            @PathVariable Long conversaId,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        List<MensagemResponseDTO> mensagens = mensagemService.listarMensagens(conversaId, userPrincipal.getId());
        return ResponseEntity.ok(mensagens);
    }

    @PutMapping("/{id}/marcar-lida")
    @Operation(summary = "Marcar uma mensagem específica como lida")
    public ResponseEntity<Void> marcarComoLida(
            @PathVariable Long id,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        mensagemService.marcarComoLida(id, userPrincipal.getId());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/conversa/{conversaId}/marcar-todas-lidas")
    @Operation(summary = "Marcar todas as mensagens não lidas de uma conversa como lidas")
    public ResponseEntity<Void> marcarTodasComoLidas(
            @PathVariable Long conversaId,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        mensagemService.marcarTodasComoLidas(conversaId, userPrincipal.getId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/conversa/{conversaId}/nao-lidas/count")
    @Operation(summary = "Contar mensagens não lidas em uma conversa")
    public ResponseEntity<Long> contarNaoLidas(
            @PathVariable Long conversaId,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Long count = mensagemService.contarNaoLidas(conversaId, userPrincipal.getId());
        return ResponseEntity.ok(count);
    }
}
