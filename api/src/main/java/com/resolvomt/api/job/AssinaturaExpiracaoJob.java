package com.resolvomt.api.job;

import com.resolvomt.api.service.AssinaturaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
public class AssinaturaExpiracaoJob {

    private final AssinaturaService assinaturaService;

    public AssinaturaExpiracaoJob(AssinaturaService assinaturaService) {
        this.assinaturaService = assinaturaService;
    }

    @Scheduled(cron = "0 0 2 * * ?")
    public void verificarExpiracoes() {
        log.info("Iniciando job de expiração de assinaturas");

        try {
            int expiradas = assinaturaService.expirarAssinaturasVencidas();
            log.info("Job concluído: {} assinaturas processadas", expiradas);
        } catch (Exception e) {
            log.error("Erro ao executar job de expiração", e);
        }
    }
}
