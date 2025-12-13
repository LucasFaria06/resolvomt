package com.resolvomt.api.dto.usuario;

import java.time.LocalDateTime;
import com.resolvomt.api.model.Usuario;

import lombok.Getter;


@Getter
public class UsuarioResponseDTO {
    
    private Long id;
    private String nomeCompleto;
    private String email;
    private LocalDateTime dataCadastro;
    private String tipoUsuario;

    public UsuarioResponseDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nomeCompleto = usuario.getNomeCompleto();
        this.email = usuario.getEmail();
        this.tipoUsuario = usuario.getTipoUsuario().name();
        this.dataCadastro = usuario.getDataCadastro();
    }
}
