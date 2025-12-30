package com.resolvomt.api.dto.usuario;

import com.resolvomt.api.enums.TipoUsuario;
import com.resolvomt.api.model.Usuario;

public record UsuarioSimplificadoDTO(
        Long id,
        String nomeCompleto,
        String email,
        TipoUsuario tipoUsuario
) {
    public UsuarioSimplificadoDTO(
            Long id,
            String nomeCompleto,
            String email,
            TipoUsuario tipoUsuario
    ) {
        this.id = id;
        this.nomeCompleto = nomeCompleto;
        this.email = email;
        this.tipoUsuario = tipoUsuario;
    }

    public UsuarioSimplificadoDTO(Usuario usuario) {
        this(
                usuario.getId(),
                usuario.getNomeCompleto(),
                usuario.getEmail(),
                usuario.getTipoUsuario()
        );
    }
}
