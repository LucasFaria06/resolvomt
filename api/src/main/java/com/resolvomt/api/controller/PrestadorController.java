package com.resolvomt.api.controller;

import com.resolvomt.api.dto.PrestadorCreateDTO;
import com.resolvomt.api.dto.PrestadorResponseDTO;
import com.resolvomt.api.enums.VerificacaoStatus;
import com.resolvomt.api.service.PrestadorService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prestadores")
public class PrestadorController {

    private final PrestadorService prestadorService;

    public PrestadorController(PrestadorService prestadorService) {
        this.prestadorService = prestadorService;
    }

    @PostMapping
    public PrestadorResponseDTO criar(@RequestBody PrestadorCreateDTO dto) {
        return prestadorService.criar(dto);
    }

    @PatchMapping("/{id}/verificacao/identidade")
    public PrestadorResponseDTO atualizarIdentidade (
            @PathVariable Long id,
            @RequestParam VerificacaoStatus status
            ) {
        return prestadorService.atualizarStatusIdentidade(id, status);
    }
    
    @PatchMapping("/{id}/verificacao/criminal")
    public PrestadorResponseDTO atualizarCriminal(
            @PathVariable Long id,
            @RequestParam VerificacaoStatus status
    ) {
        return prestadorService.atualizarStatusCriminal(id, status);
    }
}
