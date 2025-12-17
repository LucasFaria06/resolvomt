package com.resolvomt.api.controller.servico;

import com.resolvomt.api.dto.servico.ServicoResponseDTO;
import com.resolvomt.api.repository.ServicoRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/servicos")
public class ServicoPublicController {

    private final ServicoRepository servicoRepository;

    public ServicoPublicController(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }

    @GetMapping
    public List<ServicoResponseDTO> listar() {
        return servicoRepository
                .findByPrestadorVerificadoTrueAndPrestadorAtivoTrue()
                .stream()
                .map(ServicoResponseDTO::new)
                .toList();
    }
}
