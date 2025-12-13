package com.resolvomt.api.dto.prestador;

public record PrestadorListDTO (
        Long id,
        String nome,
        String email,
        Boolean prestadorVerificado
) {}
