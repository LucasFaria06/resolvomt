package com.resolvomt.api.controller.prestador;

import com.resolvomt.api.dto.agendamento.AgendamentoResponseDTO;
import com.resolvomt.api.service.AgendamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prestador/agendamentos")
@PreAuthorize("hasRole('PRESTADOR')")
public class AgendamentoPrestadorController {

    private final AgendamentoService agendamentoService;

    public AgendamentoPrestadorController(AgendamentoService agendamentoService) {
        this.agendamentoService = agendamentoService;
    }

    @GetMapping
    public ResponseEntity<List<AgendamentoResponseDTO>> listar(Authentication authentication) {
        var agendamentos = agendamentoService.listarPorPrestador(authentication.getName());
        return ResponseEntity.ok(
                agendamentos.stream().map(AgendamentoResponseDTO::new).toList()
        );
    }

    @PatchMapping("/{id}/confirmar")
    public ResponseEntity<AgendamentoResponseDTO> confirmar(
            @PathVariable Long id,
            Authentication authentication) {

        var agendamento = agendamentoService.confirmar(id, authentication.getName());
        return ResponseEntity.ok(new AgendamentoResponseDTO(agendamento));
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<AgendamentoResponseDTO> cancelar(
            @PathVariable Long id,
            Authentication authentication) {

        var agendamento = agendamentoService.cancelarPorPrestador(id, authentication.getName());
        return ResponseEntity.ok(new AgendamentoResponseDTO(agendamento));
    }
}
