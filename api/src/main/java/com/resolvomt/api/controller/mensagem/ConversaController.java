package com.resolvomt.api.controller.mensagem;

import com.resolvomt.api.dto.mensagem.ConversaResponseDTO;
import com.resolvomt.api.security.UserPrincipal;
import com.resolvomt.api.service.ConversaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conversas")
@Tag(name = "Conversas", description = "Endpoints para gerenciamento de conversas")
@SecurityRequirement(name = "bearerAuth")
public class ConversaController {

    private final ConversaService conversaService;

    public ConversaController(ConversaService conversaService) {
        this.conversaService = conversaService;
    }

    @PostMapping("/agendamento/{agendamentoId}")
    @Operation(summary = "Criar conversa para um agendamento")
    public ResponseEntity<ConversaResponseDTO> criarConversa(
            @PathVariable Long agendamentoId,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        ConversaResponseDTO conversa = conversaService.criarConversa(agendamentoId, userPrincipal.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(conversa);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar conversa por ID")
    public ResponseEntity<ConversaResponseDTO> buscarPorId(
            @PathVariable Long id,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        ConversaResponseDTO conversa = conversaService.buscarPorId(id, userPrincipal.getId());
        return ResponseEntity.ok(conversa);
    }

    @GetMapping("/agendamento/{agendamentoId}")
    @Operation(summary = "Buscar conversa por ID do agendamento")
    public ResponseEntity<ConversaResponseDTO> buscarPorAgendamento(
            @PathVariable Long agendamentoId,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        ConversaResponseDTO conversa = conversaService.buscarPorAgendamento(agendamentoId, userPrincipal.getId());
        return ResponseEntity.ok(conversa);
    }

    @GetMapping
    @Operation(summary = "Listar todas as conversas do usuário autenticado")
    public ResponseEntity<List<ConversaResponseDTO>> listarMinhasConversas(
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        List<ConversaResponseDTO> conversas = conversaService.listarConversasDoUsuario(userPrincipal.getId());
        return ResponseEntity.ok(conversas);
    }
}
