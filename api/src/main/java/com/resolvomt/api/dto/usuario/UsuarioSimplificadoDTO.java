package com.resolvomt.api.dto.usuario;

import com.resolvomt.api.model.Usuario;

public record UsuarioSimplificadoDTO(
        Long id,
        String nomeCompleto,
        String email,
        String tipoUsuario
) {
    public UsuarioSimplificadoDTO(Usuario usuario) {
        this(
                usuario.getId(),
                usuario.getNomeCompleto(),
                usuario.getEmail(),
                usuario.getTipoUsuario().name()
        );
    }
}
