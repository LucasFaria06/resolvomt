package com.resolvomt.api.dto.prestador;

import com.resolvomt.api.validation.ValidCpf;
import jakarta.validation.constraints.*;

public record PrestadorRegisterRequestDTO(

        @NotBlank(message = "Nome completo é obrigatório")
        @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
        String nomeCompleto,

        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Email inválido")
        String email,

        @NotBlank(message = "Senha é obrigatória")
        @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
        String senha,

        @NotBlank(message = "CNPJ é obrigatório")
        @Pattern(regexp = "\\d{14}|\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}",
                message = "CNPJ deve ter 14 dígitos ou formato XX.XXX.XXX/XXXX-XX")
        String cnpj,

        @NotBlank(message = "Telefone é obrigatório")
        String telefone,

        @NotBlank(message = "Nome do estabelecimento é obrigatório")
        String nome
) {}