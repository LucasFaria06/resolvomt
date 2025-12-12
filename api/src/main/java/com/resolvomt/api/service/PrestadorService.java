package com.resolvomt.api.service;

import com.resolvomt.api.dto.PrestadorCreateDTO;
import com.resolvomt.api.dto.PrestadorResponseDTO;
import com.resolvomt.api.enums.VerificacaoStatus;
import com.resolvomt.api.model.Prestador;
import com.resolvomt.api.repository.PrestadorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PrestadorService {

    private final PrestadorRepository prestadorRepository;

    public PrestadorService(PrestadorRepository prestadorRepository) {
        this.prestadorRepository = prestadorRepository;
    }

    public PrestadorResponseDTO criar(PrestadorCreateDTO dto) {
        Prestador p = new Prestador();
        p.setNome(dto.nome());
        p.setCpf(dto.cpf());
        p.setEmail(dto.email());
        p.setEndereco(dto.endereco());
        p.setTelefone(dto.telefone());

        prestadorRepository.save(p);

        return toDTO(p);
    }

    public PrestadorResponseDTO atualizarStatusIdentidade(Long id, VerificacaoStatus status) {
        Prestador p = prestadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prestador não encontrado!"));

        p.setVerificacaoIdentidadeStatus(status);
        p.setVerificacaoCriminalData(LocalDateTime.now());
        p.atualizarStatusVerificado();

        prestadorRepository.save(p);

        return toDTO(p);
    }

    public PrestadorResponseDTO atualizarStatusCriminal(Long id, VerificacaoStatus status) {
        Prestador p = prestadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prestador não encontrado!"));

        p.setVerificacaoCriminalStatus(status);
        p.setVerificacaoCriminalData(LocalDateTime.now());
        p.atualizarStatusVerificado();

        prestadorRepository.save(p);

        return toDTO(p);
    }

    private PrestadorResponseDTO toDTO(Prestador p) {
        return new PrestadorResponseDTO(
                p.getId(),
                p.getNome(),
                p.getCpf(),
                p.getEmail(),
                p.getTelefone(),
                p.getEndereco(),
                p.getVerificacaoIdentidadeStatus(),
                p.getVerificacaoCriminalStatus(),
                p.getPrestadorVerificado(),
                p.getVerificacaoIdentidadeData(),
                p.getVerificacaoCriminalData()
        );
    }
}
