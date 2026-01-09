package com.resolvomt.api.service;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.resolvomt.api.dto.assinatura.AssinaturaResponseDTO;
import com.resolvomt.api.enums.MissaoTipo;
import com.resolvomt.api.enums.StatusAssinatura;
import com.resolvomt.api.enums.TipoPlano;
import com.resolvomt.api.exception.ResourceNotFoundException;
import com.resolvomt.api.model.Assinatura;
import com.resolvomt.api.model.MissaoPrestador;
import com.resolvomt.api.model.Plano;
import com.resolvomt.api.model.Prestador;
import com.resolvomt.api.repository.AssinaturaRepository;
import com.resolvomt.api.repository.MissaoPrestadorRepository;
import com.resolvomt.api.repository.PlanoRepository;
import com.resolvomt.api.repository.PrestadorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Service
@Slf4j
public class AssinaturaService {

    private final AssinaturaRepository assinaturaRepository;
    private final PlanoRepository planoRepository;
    private final PrestadorRepository prestadorRepository;
    private final MissaoPrestadorRepository missaoRepository;

    public AssinaturaService(AssinaturaRepository assinaturaRepository,
                             PlanoRepository planoRepository,
                             PrestadorRepository prestadorRepository,
                             MissaoPrestadorRepository missaoRepository) {
        this.assinaturaRepository = assinaturaRepository;
        this.planoRepository = planoRepository;
        this.prestadorRepository = prestadorRepository;
        this.missaoRepository = missaoRepository;
    }

    @Transactional
    public Assinatura criarAssinaturaTrial(Long prestadorId) {
        Prestador prestador = prestadorRepository.findById(prestadorId)
                .orElseThrow(() -> new ResourceNotFoundException("Prestador não encontrado"));

        if (assinaturaRepository.findByPrestadorId(prestadorId).isPresent()) {
            throw new IllegalStateException("Prestador já possui assinatura");
        }

        Plano planoFree = planoRepository.findByNome("FREE")
                .orElseThrow(() -> new ResourceNotFoundException("Plano FREE não encontrado"));

        Assinatura assinatura = new Assinatura();
        assinatura.setPrestador(prestador);
        assinatura.setPlano(planoFree);
        assinatura.setDataInicio(LocalDateTime.now());
        assinatura.setDataFim(LocalDateTime.now().plusDays(60));
        assinatura.setStatus(StatusAssinatura.TRIAL);
        assinatura.setTrialUtilizado(true);

        return assinaturaRepository.save(assinatura);
    }

    @Transactional
    public void concluirMissao(Long prestadorId, MissaoTipo missaoTipo) {
        Prestador prestador = prestadorRepository.findById(prestadorId)
                .orElseThrow(() -> new ResourceNotFoundException("Prestador não encontrado"));

        if (missaoRepository.existsByPrestadorIdAndMissaoTipo(prestadorId, missaoTipo.name())) {
            return;
        }

        MissaoPrestador missao = new MissaoPrestador();
        missao.setPrestador(prestador);
        missao.setMissaoTipo(missaoTipo.name());
        missao.setDiasBonus(missaoTipo.getDiasBonus());
        missaoRepository.save(missao);

        Assinatura assinatura = assinaturaRepository.findByPrestadorId(prestadorId)
                .orElseThrow(() -> new ResourceNotFoundException("Assinatura não encontrada"));

        assinatura.adicionarDiasBonus(missaoTipo.getDiasBonus());
        assinaturaRepository.save(assinatura);
    }


    @Transactional(readOnly = true)
    public AssinaturaResponseDTO buscarAssinaturaPorUsuario(Long usuarioId) {
        Prestador prestador = prestadorRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Prestador não encontrado"));

        Assinatura assinatura = assinaturaRepository.findByPrestadorId(prestador.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Assinatura não encontrada"));

        return new AssinaturaResponseDTO(assinatura);
    }

    public boolean podeCriarAgendamento(Long prestadorId, int agendamentosNoMes) {
        Assinatura assinatura = assinaturaRepository.findByPrestadorId(prestadorId)
                .orElseThrow(() -> new ResourceNotFoundException("Assinatura não encontrada"));

        if (!assinatura.isAtiva()) {
            return false;
        }

        Integer limite = assinatura.getPlano().getLimiteAgendamentosMes();

        if (limite == null || limite == 999) {
            return true;
        }

        return agendamentosNoMes < limite;
    }

    @Transactional
    public int expirarAssinaturasVencidas() {
        LocalDate hoje = LocalDate.now();
        int contador = 0;

        List<Assinatura> vencidas = assinaturaRepository
        .findByStatusInAndDataFimBefore(
                Arrays.asList(StatusAssinatura.TRIAL, StatusAssinatura.ATIVA),
                hoje
        );

        for (Assinatura assinatura : vencidas) {
            if (assinatura.getStatus() == StatusAssinatura.TRIAL) {
                migrarParaFree(assinatura);
            } else {
                assinatura.setStatus(StatusAssinatura.EXPIRADA);
                assinaturaRepository.save(assinatura);
            }

            contador ++;
            log.info("Assinatura {} expirada para prestador {}",
                    assinatura.getId(), assinatura.getPrestador().getId());
        }

        return contador;
    }

    private void migrarParaFree(Assinatura trialVencido) {
        Prestador prestador = trialVencido.getPrestador();
        Plano planoFree = planoRepository.findByTipo(TipoPlano.FREE)
                .orElseThrow(() -> new RuntimeException("Plano FREE não encontrado"));

        trialVencido.setStatus(StatusAssinatura.EXPIRADA);
        assinaturaRepository.save(trialVencido);

        Assinatura novaFree = new Assinatura();
        novaFree.setPrestador(prestador);
        novaFree.setPlano(planoFree);
        novaFree.setStatus(StatusAssinatura.ATIVA);
        novaFree.setDataInicio(LocalDateTime.now());
        novaFree.setDataFim(null);

        assinaturaRepository.save(novaFree);

        log.info("Prestador {} migrado de TRIAL para FREE", prestador.getId());
    }

    public Double getComissaoAtualPorUsuario(Long usuarioId) {
        Prestador prestador = prestadorRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Prestador não encontrado"));

        Assinatura assinatura = assinaturaRepository.findByPrestadorId(prestador.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Assinatura não encontrada"));

        return assinatura.getPlano().getComissaoPercentual().doubleValue();
    }

    public List<MissaoPrestador> listarMissoesConcluidas(Long prestadorId) {
        return missaoRepository.findByPrestadorId(prestadorId);
    }

    public List<MissaoTipo> listarMissoesPendentes(Long prestadorId) {
        List<String> concluidasStr = missaoRepository.findByPrestadorId(prestadorId)
                .stream()
                .map(MissaoPrestador::getMissaoTipo)
                .toList();

        return java.util.Arrays.stream(MissaoTipo.values())
                .filter(missao -> !concluidasStr.contains(missao.name()))
                .toList();
    }
}
