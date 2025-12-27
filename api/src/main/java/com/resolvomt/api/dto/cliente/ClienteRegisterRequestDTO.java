package com.resolvomt.api.dto.cliente;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;

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
        @Pattern(regexp = "\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}",
                message = "CPF inválido")
        String cpf,

        String telefone
) {}