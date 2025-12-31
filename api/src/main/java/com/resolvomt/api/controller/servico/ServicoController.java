package com.resolvomt.api.controller.servico;

import com.resolvomt.api.dto.servico.ServicoCreateRequestDTO;
import com.resolvomt.api.dto.servico.ServicoResponseDTO;
import com.resolvomt.api.dto.servico.ServicoUpdateRequestDTO;
import com.resolvomt.api.service.ServicoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prestador/servicos")
@PreAuthorize("hasRole('PRESTADOR')")
public class ServicoController {

    private final ServicoService servicoService;

    public ServicoController(ServicoService servicoService) {
        this.servicoService = servicoService;
    }

    @PostMapping
    public ResponseEntity<ServicoResponseDTO> criar (
            @Valid @RequestBody ServicoCreateRequestDTO dto,
            Authentication authentication) {

        var servico = servicoService.criar(dto, authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ServicoResponseDTO(servico));
    }

    @Transactional(readOnly = true)
    @GetMapping
    public ResponseEntity<List<ServicoResponseDTO>> listar(Authentication authentication) {
        var servicos = servicoService.listarPorPrestador(authentication.getName());
        return ResponseEntity.ok(servicos
                .stream()
                .map(ServicoResponseDTO::new)
                .toList()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicoResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ServicoUpdateRequestDTO dto,
            Authentication authentication) {

        var servico = servicoService.atualizar(id, dto, authentication.getName());
        return ResponseEntity.ok(new ServicoResponseDTO(servico));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id,
            Authentication authentication) {

        servicoService.deletar(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }
}
