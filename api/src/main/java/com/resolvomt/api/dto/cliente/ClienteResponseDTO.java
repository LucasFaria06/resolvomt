package com.resolvomt.api.dto.cliente;

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

    record UsuarioSimplificadoDTO(
            Long id,
            String nomeCompleto,
            String email,
            String tipoUsuario
    ) {
        public UsuarioSimplificadoDTO(com.resolvomt.api.model.Usuario usuario) {
            this(
                    usuario.getId(),
                    usuario.getNomeCompleto(),
                    usuario.getEmail(),
                    usuario.getTipoUsuario().name()
            );
        }
    }

