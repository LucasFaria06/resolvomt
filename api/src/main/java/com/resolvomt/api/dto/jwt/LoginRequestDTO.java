package com.resolvomt.api.dto.jwt;

public record LoginRequestDTO (
   String email,
   String senha
) {}
