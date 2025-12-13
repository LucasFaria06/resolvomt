package com.resolvomt.api.dto.cliente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClienteRegisterRequestDTO (
    @NotBlank(message = "O nome é obrigatório.")
    String nomeCompleto,

    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "Formato de e-mail inválido.")
    String email,

    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres.")
    String senha,

    @NotBlank(message = "O CPF é obrigatório.")
    String cpf,

    String telefone
) {}
