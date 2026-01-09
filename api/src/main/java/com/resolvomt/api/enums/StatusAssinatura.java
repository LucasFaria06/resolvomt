package com.resolvomt.api.enums;

public enum StatusAssinatura {
    PENDENTE,   // Criada mas aguardando primeiro pagamento
    TRIAL,      // Período de teste gratuito (60 dias)
    ATIVA,      // Assinatura paga e funcionando
    EXPIRADA,   // Venceu e não renovou (pode reativar)
    CANCELADA   // Cancelada pelo usuário (não volta)
}
