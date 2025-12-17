package com.resolvomt.api.controller.cliente;

import com.resolvomt.api.dto.cliente.ClienteResponseDTO;
import com.resolvomt.api.model.Cliente;
import com.resolvomt.api.service.ClienteService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/me")
    public ClienteResponseDTO me(Authentication authentication) {
        System.out.println("AUTH = " + authentication);
        if (authentication == null) {
            throw new RuntimeException("AUTH NULL");
        }
        Cliente cliente = clienteService.buscarPorEmailUsuario(authentication.getName());
        return new ClienteResponseDTO(cliente);
    }

}
