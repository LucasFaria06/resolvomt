package com.resolvomt.api.controller.servico;

import com.resolvomt.api.dto.servico.ServicoPublicoDTO;
import com.resolvomt.api.service.ServicoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/servicos")
public class ServicoPublicoController {

    private final ServicoService servicoService;

    public ServicoPublicoController(ServicoService servicoService) {
        this.servicoService = servicoService;
    }

    @GetMapping
    public List<ServicoPublicoDTO> listarServicos() {
        return servicoService.listarServicosPublicos()
                .stream()
                .map(ServicoPublicoDTO::new)
                .toList();
    }
}
