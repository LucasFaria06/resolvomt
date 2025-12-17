package com.resolvomt.api.controller.servico;

import com.resolvomt.api.dto.servico.ServicoRequestDTO;
import com.resolvomt.api.dto.servico.ServicoResponseDTO;
import com.resolvomt.api.service.ServicoService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prestador/servicos")
public class ServicoController {

    private final ServicoService servicoService;

    public ServicoController(ServicoService servicoService) {
        this.servicoService = servicoService;
    }

    @PostMapping
    public ServicoResponseDTO criar(@RequestBody ServicoRequestDTO dto, Authentication authentication) {
        return new ServicoResponseDTO(
                servicoService.criarServico(authentication.getName(), dto)
        );
    }

    @GetMapping
    public List<ServicoResponseDTO> listar(Authentication authentication) {
        return servicoService.listarMeusServicos(authentication.getName())
                .stream()
                .map(ServicoResponseDTO::new)
                .toList();
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id){
        servicoService.excluir(id);
    }
}
