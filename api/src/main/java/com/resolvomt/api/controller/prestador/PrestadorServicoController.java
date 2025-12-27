package com.resolvomt.api.controller.prestador;

import com.resolvomt.api.dto.servico.ServicoRequestDTO;
import com.resolvomt.api.dto.servico.ServicoResponseDTO;
import com.resolvomt.api.service.ServicoService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prestador/servicos")
@PreAuthorize("hasRole('PRESTADOR')")
public class PrestadorServicoController {

    private final ServicoService servicoService;

    public PrestadorServicoController(ServicoService servicoService) {
        this.servicoService = servicoService;
    }

    @PostMapping
    public ServicoResponseDTO criar(
            @AuthenticationPrincipal UserDetails user,
            @RequestBody ServicoRequestDTO dto
    ) {
        return new ServicoResponseDTO(
                servicoService.criarServico(user.getUsername(), dto)
        );
    }

    @GetMapping
    public List<ServicoResponseDTO> listarMeus(
            @AuthenticationPrincipal UserDetails user
    ) {
        return servicoService.listarMeusServicos(user.getUsername())
                .stream()
                .map(ServicoResponseDTO::new)
                .toList();
    }

    @PatchMapping("/{id}/desativar")
    public void desativar(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails user
    ) {
        servicoService.desativar(id, user.getUsername());
    }

    @PatchMapping("/{id}/ativar")
    public void ativar(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails user
    ) {
        servicoService.ativarServico(id, user.getUsername());
    }
}
