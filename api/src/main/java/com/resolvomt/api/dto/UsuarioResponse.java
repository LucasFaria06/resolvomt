package com.resolvomt.api.dto;

import com.resolvomt.api.model.Usuario;

import lombok.Data;

@Data
public class UsuarioResponse {
    
    private Long id;
    private String nomeCompleto;
    private String email;
    private String tipoUsuario;

    public UsuarioResponse(Usuario usuario) {
        this.id = usuario.getId();
        this.nomeCompleto = usuario.getNomeCompleto();
        this.email = usuario.getEmail();
        this.tipoUsuario = usuario.getTipoUsuario();
    }
}
