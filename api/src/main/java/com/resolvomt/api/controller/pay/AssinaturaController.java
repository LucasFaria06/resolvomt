package com.resolvomt.api.controller.pay;

import com.resolvomt.api.dto.assinatura.AssinaturaResponseDTO;
import com.resolvomt.api.security.UserPrincipal;
import com.resolvomt.api.service.AssinaturaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/assinaturas")
@Tag(name = "Assinaturas", description = "Endpoints para gerenciamento de assinaturas")
@SecurityRequirement(name = "bearerAuth")
public class AssinaturaController {

    private final AssinaturaService assinaturaService;

    public AssinaturaController(AssinaturaService assinaturaService) {
        this.assinaturaService = assinaturaService;
    }

    @GetMapping("/minha")
    @Operation(summary = "Ver minha assinatura atual", description ="Retorna detalhes da assinatura do prestador")
    public ResponseEntity<AssinaturaResponseDTO> verMinhaAssinatura(
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        AssinaturaResponseDTO assinatura = assinaturaService.buscarAssinaturaPorUsuario(userPrincipal.getId());
        return ResponseEntity.ok(assinatura);
    }

    @GetMapping("/comissao-atual")
    @Operation(summary = "Ver taxa de comissão atual", description = "Retorna a taxa de comissão do plano atual")
    public ResponseEntity<Double> verComissaoAtual(
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Double comissao = assinaturaService.getComissaoAtualPorUsuario(userPrincipal.getId());
        return ResponseEntity.ok(comissao);
    }
}
