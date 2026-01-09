package com.resolvomt.api.service;

import com.resolvomt.api.dto.missao.MissaoPendenteDTO;
import com.resolvomt.api.dto.missao.MissaoResponseDTO;
import com.resolvomt.api.dto.missao.ProgressoMissoesDTO;
import com.resolvomt.api.enums.MissaoTipo;
import com.resolvomt.api.exception.ResourceNotFoundException;
import com.resolvomt.api.model.Assinatura;
import com.resolvomt.api.model.MissaoPrestador;
import com.resolvomt.api.model.Prestador;
import com.resolvomt.api.repository.AssinaturaRepository;
import com.resolvomt.api.repository.MissaoPrestadorRepository;
import com.resolvomt.api.repository.PrestadorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MissaoService {

    private final MissaoPrestadorRepository missaoRepository;
    private final PrestadorRepository prestadorRepository;
    private final AssinaturaRepository assinaturaRepository;

    public MissaoService(MissaoPrestadorRepository missaoRepository,
                         PrestadorRepository prestadorRepository,
                         AssinaturaRepository assinaturaRepository) {
        this.missaoRepository = missaoRepository;
        this.prestadorRepository = prestadorRepository;
        this.assinaturaRepository = assinaturaRepository;
    }

    @Transactional(readOnly = true)
    public List<MissaoPendenteDTO> listarMissoesPendentes(Long usuarioId) {
        Prestador prestador = prestadorRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Prestador não encontrado"));

        List<String> concluidasStr = missaoRepository.findByPrestadorId(prestador.getId())
                .stream()
                .map(MissaoPrestador::getMissaoTipo)
                .collect(Collectors.toList());

        return Arrays.stream(MissaoTipo.values())
                .filter(tipo -> !concluidasStr.contains(tipo.name()))
                .map(MissaoPendenteDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MissaoResponseDTO> listarMissoesConcluidas(Long usuarioId) {
        Prestador prestador = prestadorRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Prestador não encontrado"));

        return missaoRepository.findByPrestadorId(prestador.getId())
                .stream()
                .map(missao -> {
                    MissaoTipo tipo = MissaoTipo.valueOf(missao.getMissaoTipo());
                    return new MissaoResponseDTO(missao, tipo);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public MissaoResponseDTO concluirMissao(Long usuarioId, MissaoTipo missaoTipo) {
        Prestador prestador = prestadorRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Prestador não encontrado"));

        if (missaoRepository.existsByPrestadorIdAndMissaoTipo(prestador.getId(), missaoTipo.name())) {
            throw new IllegalStateException("Missão já foi concluída anteriormente");
        }

        MissaoPrestador missao = new MissaoPrestador();
        missao.setPrestador(prestador);
        missao.setMissaoTipo(missaoTipo.name());
        missao.setDiasBonus(missaoTipo.getDiasBonus());
        missao = missaoRepository.save(missao);

        Assinatura assinatura = assinaturaRepository.findByPrestadorId(prestador.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Assinatura não encontrada"));

        assinatura.adicionarDiasBonus(missaoTipo.getDiasBonus());
        assinaturaRepository.save(assinatura);

        return new MissaoResponseDTO(missao, missaoTipo);
    }

    @Transactional(readOnly = true)
    public ProgressoMissoesDTO verProgresso(Long usuarioId) {
        Prestador prestador = prestadorRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Prestador não encontrado"));

        List<MissaoPrestador> concluidas = missaoRepository.findByPrestadorId(prestador.getId());

        int totalMissoes = MissaoTipo.values().length;
        int missoesCompletas = concluidas.size();
        int missoesPendentes = totalMissoes - missoesCompletas;

        int diasBonusGanhos = concluidas.stream()
                .mapToInt(MissaoPrestador::getDiasBonus)
                .sum();

        int diasBonusRestantes = MissaoTipo.getTotalDiasBonus() - diasBonusGanhos;

        return new ProgressoMissoesDTO(
                totalMissoes,
                missoesCompletas,
                missoesPendentes,
                diasBonusGanhos,
                diasBonusRestantes
        );
    }
}
