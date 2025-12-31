package com.resolvomt.api.controller.servico;

import com.resolvomt.api.dto.servico.ServicoResponseDTO;
import com.resolvomt.api.service.ServicoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/servicos")
public class ServicoPublicController {

    private final ServicoService servicoService;

    public ServicoPublicController(ServicoService servicoService) {
        this.servicoService = servicoService;
    }

    @GetMapping
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public ResponseEntity<List<ServicoResponseDTO>> listarPublicos() {
        var servicos = servicoService.listarPublicos();
        return ResponseEntity.ok(
                servicos.stream()
                        .map(ServicoResponseDTO::new)
                        .toList()
        );
    }
}