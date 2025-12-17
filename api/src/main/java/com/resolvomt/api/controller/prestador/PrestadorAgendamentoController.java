package com.resolvomt.api.controller.prestador;

import com.resolvomt.api.dto.agendamento.AgendamentoResponseDTO;
import com.resolvomt.api.service.AgendamentoService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prestador/agendamentos")
public class PrestadorAgendamentoController {

    private final AgendamentoService agendamentoService;

    public PrestadorAgendamentoController(AgendamentoService agendamentoService) {
        this.agendamentoService = agendamentoService;
    }

    @GetMapping
    public List<AgendamentoResponseDTO> listar(Authentication auth) {
        return agendamentoService.listarPrestador(auth.getName())
                .stream()
                .map(AgendamentoResponseDTO::new)
                .toList();
    }

    @PatchMapping("/{id}/comfirmar")
    public void confirmar(@PathVariable Long id) {
        agendamentoService.confirmar(id);
    }

    @PatchMapping("/{id}/cancelar")
    public void cancelar(@PathVariable Long id) {
        agendamentoService.cancelar(id);
    }
}
