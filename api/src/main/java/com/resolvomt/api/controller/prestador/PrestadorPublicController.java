package com.resolvomt.api.controller.prestador;

import com.resolvomt.api.dto.prestador.PrestadorResponseDTO;
import com.resolvomt.api.service.PrestadorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/prestadores")
public class PrestadorPublicController {

    private final PrestadorService prestadorService;

    public PrestadorPublicController(PrestadorService prestadorService) {

        this.prestadorService = prestadorService;
    }
    @GetMapping("/verificados")
    public List<PrestadorResponseDTO> listarVerificados() {
        return prestadorService.listarVerificados()
                .stream()
                .map(PrestadorResponseDTO::new)
                .toList();
    }
}
