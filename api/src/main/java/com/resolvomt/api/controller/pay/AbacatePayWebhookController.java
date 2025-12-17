package com.resolvomt.api.controller.pay;

import com.resolvomt.api.dto.pay.AbacatePayWebhookDTO;
import com.resolvomt.api.service.PagamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class AbacatePayWebhookController {

    private final PagamentoService pagamentoService;

    public AbacatePayWebhookController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @PostMapping
    public ResponseEntity<Void> receberWebhook(@RequestBody AbacatePayWebhookDTO dto) {

        if ("PAID".equals(dto.status())) {
            pagamentoService.confirmarPagamento(dto.referencia());
        }

        return ResponseEntity.ok().build();
    }
}
