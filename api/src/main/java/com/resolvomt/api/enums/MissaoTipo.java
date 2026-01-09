package com.resolvomt.api.enums;

public enum MissaoTipo {
    PERFIL_COMPLETO(3, "Complete seu perfil com foto e descrição"),
    CADASTRAR_3_SERVICOS(3, "Cadastre 3 serviços diferentes"),
    PRIMEIRO_AGENDAMENTO(3, "Receba seu primeiro agendamento"),
    PRIMEIRA_AVALIACAO_5_ESTRELAS(3, "Receba sua primeira avaliação 5 estrelas"),
    VERIFICAR_DOCUMENTOS(3, "Verifique seus documentos (CNPJ/CPF)");

    private final int diasBonus;
    private final String descricao;

    MissaoTipo(int diasBonus, String descricao) {
        this.diasBonus = diasBonus;
        this.descricao = descricao;
    }

    public int getDiasBonus() {
        return diasBonus;
    }

    public String getDescricao() {
        return descricao;
    }

    public static int getTotalDiasBonus() {
        int total = 0;
        for (MissaoTipo missao : values()) {
            total += missao.getDiasBonus();
        }
        return total;
    }
}