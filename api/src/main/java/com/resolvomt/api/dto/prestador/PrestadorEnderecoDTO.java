package com.resolvomt.api.dto.prestador;

public record PrestadorEnderecoDTO (
        String rua,
        String numero,
        String bairro,
        String cidade,
        String estado,
        String cep
)
{}
