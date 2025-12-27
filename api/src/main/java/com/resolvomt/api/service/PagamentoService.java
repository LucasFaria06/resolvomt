package com.resolvomt.api.service;

import com.resolvomt.api.enums.StatusAssinatura;
import com.resolvomt.api.model.Pagamento;
import com.resolvomt.api.model.Prestador;
import com.resolvomt.api.repository.PagamentoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;
    private final PrestadorService prestadorService;

    public PagamentoService(PagamentoRepository pagamentoRepository, PrestadorService prestadorService) {
        this.pagamentoRepository = pagamentoRepository;
        this.prestadorService = prestadorService;
    }

    public Pagamento criarCobranca(Long prestadorId, BigDecimal valor, String referenciaExterna) {

        Prestador prestador = prestadorService.buscarPorId(prestadorId);

        Pagamento pagamento = new Pagamento();
        pagamento.setPrestador(prestador);
        pagamento.setValor(valor);
        pagamento.setReferenciaExterna(referenciaExterna);

        return pagamentoRepository.save(pagamento);
    }

    public void confirmarPagamento(String referenciaExterna) {

        Pagamento pagamento = pagamentoRepository.findByReferenciaExterna(referenciaExterna)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));

        pagamento.setStatus("PAID");
        pagamento.setPagoEm(LocalDateTime.now());

        Prestador prestador = pagamento.getPrestador();
        prestador.setStatusAssinatura(StatusAssinatura.ATIVA);
        prestador.setAssinaturaAte(LocalDate.now().plusMonths(1));

        pagamentoRepository.save(pagamento);
    }
}