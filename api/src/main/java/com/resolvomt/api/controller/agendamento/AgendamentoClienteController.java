package com.resolvomt.api.controller.agendamento;

import com.resolvomt.api.dto.agendamento.AgendamentoCreateRequestDTO;
import com.resolvomt.api.dto.agendamento.AgendamentoResponseDTO;
import com.resolvomt.api.service.AgendamentoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cliente/agendamentos")
@PreAuthorize("hasRole('CLIENTE')")
public class AgendamentoClienteController {

    private final AgendamentoService agendamentoService;

    public AgendamentoClienteController(AgendamentoService agendamentoService) {
        this.agendamentoService = agendamentoService;
    }

    @PostMapping
    public ResponseEntity<AgendamentoResponseDTO> criar(
            @Valid @RequestBody AgendamentoCreateRequestDTO dto,
            Authentication authentication) {
        var agendamento = agendamentoService.criar(dto, authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AgendamentoResponseDTO(agendamento));
    }

    @GetMapping
    public ResponseEntity<List<AgendamentoResponseDTO>> listar(Authentication authentication) {
        var agendamentos = agendamentoService.listarPorCliente(authentication.getName());
        return ResponseEntity.ok(
                agendamentos.stream().map(AgendamentoResponseDTO::new).toList()
        );
    }
}