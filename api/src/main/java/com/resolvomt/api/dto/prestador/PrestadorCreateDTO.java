package com.resolvomt.api.dto.prestador;

public record PrestadorCreateDTO(
    String nome,
    String email,
    String telefone,
    String cpf,
    String endereco
) {}
