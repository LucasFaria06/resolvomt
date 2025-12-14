package com.resolvomt.api.controller;

import com.resolvomt.api.dto.prestador.PrestadorResponseDTO;
import com.resolvomt.api.model.Prestador;
import com.resolvomt.api.service.PrestadorService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/prestadores")
public class PrestadorController {

    private final PrestadorService prestadorService;


    public PrestadorController(PrestadorService prestadorService) {
        this.prestadorService = prestadorService;
    }

    @GetMapping("/me")
    public PrestadorResponseDTO me(Authentication authentication) {
        Prestador prestador =
                prestadorService.buscarPorEmailUsuario(authentication.getName());
        return new PrestadorResponseDTO(prestador);
    }
}
