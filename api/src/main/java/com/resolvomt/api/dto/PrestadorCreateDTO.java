package com.resolvomt.api.dto;

public record PrestadorCreateDTO(
    String nome,
    String cpf,
    String email,
    String telefone,
    String endereco
) {}
