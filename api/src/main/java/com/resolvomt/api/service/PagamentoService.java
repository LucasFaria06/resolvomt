package com.resolvomt.api.service;

import com.resolvomt.api.enums.StatusAssinatura;
import com.resolvomt.api.model.Assinatura;
import com.resolvomt.api.model.Pagamento;
import com.resolvomt.api.model.Prestador;
import com.resolvomt.api.repository.AssinaturaRepository;
import com.resolvomt.api.repository.PagamentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;
    private final PrestadorService prestadorService;
    private final AssinaturaRepository assinaturaRepository;

    public PagamentoService(
            PagamentoRepository pagamentoRepository,
            PrestadorService prestadorService,
            AssinaturaRepository assinaturaRepository) {
        this.pagamentoRepository = pagamentoRepository;
        this.prestadorService = prestadorService;
        this.assinaturaRepository = assinaturaRepository;
    }

    public Pagamento criarCobranca(Long prestadorId, BigDecimal valor, String referenciaExterna) {
        Prestador prestador = prestadorService.buscarPorId(prestadorId);

        Pagamento pagamento = new Pagamento();
        pagamento.setPrestador(prestador);
        pagamento.setValor(valor);
        pagamento.setReferenciaExterna(referenciaExterna);
        pagamento.setStatus("PENDING");

        return pagamentoRepository.save(pagamento);
    }

    @Transactional
    public void confirmarPagamento(String referenciaExterna) {
        Pagamento pagamento = pagamentoRepository.findByReferenciaExterna(referenciaExterna)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));

        pagamento.setStatus("PAID");
        pagamento.setPagoEm(LocalDateTime.now());

        Assinatura assinatura = assinaturaRepository.findByPrestadorId(pagamento.getPrestador().getId())
                .orElse(new Assinatura());

        if (assinatura.getPrestador() == null) {
            assinatura.setPrestador(pagamento.getPrestador());
        }

        assinatura.setStatus(StatusAssinatura.ATIVA);

        if (assinatura.getDataFim() != null && assinatura.getDataFim().isAfter(LocalDateTime.now())) {
            assinatura.setDataFim(assinatura.getDataFim().plusMonths(1));
        } else {
            assinatura.setDataInicio(LocalDateTime.now());
            assinatura.setDataFim(LocalDateTime.now().plusMonths(1));
        }

        assinaturaRepository.save(assinatura);
        pagamentoRepository.save(pagamento);
    }
}