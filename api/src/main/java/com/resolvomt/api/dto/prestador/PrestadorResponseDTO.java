package com.resolvomt.api.dto.prestador;

import com.resolvomt.api.dto.usuario.UsuarioSimplificadoDTO;
import com.resolvomt.api.model.Prestador;

public record PrestadorResponseDTO(
        Long id,
        String cnpj,
        String telefone,
        boolean verificado,
        boolean ativo,
        UsuarioSimplificadoDTO usuario
) {
    public PrestadorResponseDTO(Prestador prestador) {
        this(
                prestador.getId(),
                prestador.getCnpj(),
                prestador.getTelefone(),
                prestador.isVerificado(),
                prestador.isAtivo(),
                new UsuarioSimplificadoDTO(prestador.getUsuario())
        );
    }
}
