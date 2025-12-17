package com.resolvomt.api.controller.cliente;

import com.resolvomt.api.dto.agendamento.AgendamentoRequestDTO;
import com.resolvomt.api.dto.agendamento.AgendamentoResponseDTO;
import com.resolvomt.api.service.AgendamentoService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cliente/agendamentos")
public class ClienteAgendamentoController {

    private final AgendamentoService agendamentoService;

    public ClienteAgendamentoController(AgendamentoService agendamentoService) {
        this.agendamentoService = agendamentoService;
    }

    @PostMapping
    public AgendamentoResponseDTO criar(@RequestBody AgendamentoRequestDTO dto, Authentication auth) {
        return new AgendamentoResponseDTO(
                agendamentoService.criar(auth.getName(), dto)
        );
    }

    @GetMapping
    public List<AgendamentoResponseDTO> listar(Authentication auth) {
        return agendamentoService.listarCliente(auth.getName())
                .stream()
                .map(AgendamentoResponseDTO::new)
                .toList();
    }
}
