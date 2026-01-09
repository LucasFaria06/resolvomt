package com.resolvomt.api.dto.missao;

public class ProgressoMissoesDTO {

    private int totalMissoes;
    private int missoesCompletas;
    private int missoesPendentes;
    private int diasBonusGanhos;
    private int diasBonusRestantes;

    public ProgressoMissoesDTO() {

    }

    public ProgressoMissoesDTO(int totalMissoes, int missoesCompletas, int missoesPendentes, int diasBonusGanhos, int diasBonusRestantes) {

        this.totalMissoes = totalMissoes;
        this.missoesCompletas = missoesCompletas;
        this.missoesPendentes = missoesPendentes;
        this.diasBonusGanhos = diasBonusGanhos;
        this.diasBonusRestantes = diasBonusRestantes;
    }

    public int getTotalMissoes() {
        return totalMissoes;
    }

    public void setTotalMissoes(int totalMissoes) {
        this.totalMissoes = totalMissoes;
    }

    public int getMissoesCompletas() {
        return missoesCompletas;
    }

    public void setMissoesCompletas(int missoesCompletas) {
        this.missoesCompletas = missoesCompletas;
    }

    public int getMissoesPendentes() {
        return missoesPendentes;
    }

    public void setMissoesPendentes(int missoesPendentes) {
        this.missoesPendentes = missoesPendentes;
    }

    public int getDiasBonusGanhos() {
        return diasBonusGanhos;
    }

    public void setDiasBonusGanhos(int diasBonusGanhos) {
        this.diasBonusGanhos = diasBonusGanhos;
    }

    public int getDiasBonusRestantes() {
        return diasBonusRestantes;
    }

    public void setDiasBonusRestantes(int diasBonusRestantes) {
        this.diasBonusRestantes = diasBonusRestantes;
    }
}
