package com.resolvomt.api.dto.cliente;

import jakarta.validation.constraints.*;
import com.resolvomt.api.validation.ValidCpf;

public record ClienteRegisterRequestDTO(

        @NotBlank(message = "Nome completo é obrigatório")
        @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
        String nomeCompleto,

        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Email inválido")
        String email,

        @NotBlank(message = "Senha é obrigatória")
        @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
        String senha,

        @NotBlank(message = "CPF é obrigatório")
        @ValidCpf
        String cpf,

        String telefone
) {}