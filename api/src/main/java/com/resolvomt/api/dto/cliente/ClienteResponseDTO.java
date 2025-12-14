package com.resolvomt.api.dto.cliente;

import com.resolvomt.api.dto.usuario.UsuarioSimplificadoDTO;
import com.resolvomt.api.model.Cliente;

public record ClienteResponseDTO(
        Long id,
        String cpf,
        String telefone,
        UsuarioSimplificadoDTO usuario
) {
    public ClienteResponseDTO(Cliente cliente) {
        this(
                cliente.getId(),
                cliente.getCpf(),
                cliente.getTelefone(),
                new UsuarioSimplificadoDTO(cliente.getUsuario())
        );
    }
}
