package com.resolvomt.api.controller.missao;

import com.resolvomt.api.dto.missao.MissaoConcluirRequestDTO;
import com.resolvomt.api.dto.missao.MissaoPendenteDTO;
import com.resolvomt.api.dto.missao.MissaoResponseDTO;
import com.resolvomt.api.dto.missao.ProgressoMissoesDTO;
import com.resolvomt.api.security.UserPrincipal;
import com.resolvomt.api.service.MissaoService;
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
@RequestMapping("/api/missoes")
@Tag(name = "Missões", description = "Endpoints para sistema de gamificação e missões")
@SecurityRequirement(name = "bearerAuth")
public class MissaoController {

    private final MissaoService missaoService;

    public MissaoController(MissaoService missaoService) {
        this.missaoService = missaoService;
    }

    @GetMapping("/pendentes")
    @Operation(summary = "Listar missões pendentes", description = "Retorna as missões que o prestador ainda não completou")
    public ResponseEntity<List<MissaoPendenteDTO>> listarPendentes(
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        List<MissaoPendenteDTO> missoes = missaoService.listarMissoesPendentes(userPrincipal.getId());
        return ResponseEntity.ok(missoes);
    }

    @GetMapping("/historico")
    @Operation(summary = "Ver histórico de missões", description = "Retorna todas as missões já concluídas pelo prestador")
    public ResponseEntity<List<MissaoResponseDTO>> verHistorico(
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        List<MissaoResponseDTO> missoes = missaoService.listarMissoesConcluidas(userPrincipal.getId());
        return ResponseEntity.ok(missoes);
    }

    @PostMapping("/concluir")
    @Operation(summary = "Concluir missão manualmente", description = "Marca uma missão como concluída e adiciona dias bônus")
    public ResponseEntity<MissaoResponseDTO> concluirMissao(
            @Valid @RequestBody MissaoConcluirRequestDTO dto,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        MissaoResponseDTO missao = missaoService.concluirMissao(userPrincipal.getId(), dto.getMissaoTipo());
        return ResponseEntity.status(HttpStatus.CREATED).body(missao);
    }

    @GetMapping("/progresso")
    @Operation(summary = "Ver progresso geral", description = "Retorna estatísticas de missões concluídas e dias bônus ganhos")
    public ResponseEntity<ProgressoMissoesDTO> verProgresso(
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        ProgressoMissoesDTO progresso = missaoService.verProgresso(userPrincipal.getId());
        return ResponseEntity.ok(progresso);
    }
}

