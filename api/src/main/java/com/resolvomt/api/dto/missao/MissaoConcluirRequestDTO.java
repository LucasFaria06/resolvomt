package com.resolvomt.api.dto.missao;

import com.resolvomt.api.enums.MissaoTipo;
import jakarta.validation.constraints.NotNull;

public class MissaoConcluirRequestDTO {
        @NotNull(message = "Tipo da missão é obrigatório")
        private MissaoTipo missaoTipo;

        public MissaoConcluirRequestDTO(){
        }

        public MissaoConcluirRequestDTO(MissaoTipo missaoTipo) {
                this.missaoTipo = missaoTipo;
        }

        public MissaoTipo getMissaoTipo() {
                return missaoTipo;
        }

        public void setMissaoTipo(MissaoTipo missaoTipo) {
                this.missaoTipo = missaoTipo;
        }

}

