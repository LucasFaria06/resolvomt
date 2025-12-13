package com.resolvomt.api.dto.prestador;

import com.resolvomt.api.enums.VerificacaoStatus;

public record PrestadorVerificacaoUpdateDTO (
        VerificacaoStatus status
){
}
