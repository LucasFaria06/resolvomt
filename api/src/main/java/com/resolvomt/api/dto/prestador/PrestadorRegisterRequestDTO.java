package com.resolvomt.api.dto.prestador;

public record PrestadorRegisterRequestDTO (

    String nomeCompleto,
    String email,
    String senha,
    String cnpj,
    String telefone

) {}