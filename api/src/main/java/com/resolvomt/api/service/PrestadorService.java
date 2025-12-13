package com.resolvomt.api.service;

import com.resolvomt.api.dto.prestador.*;
import com.resolvomt.api.enums.VerificacaoStatus;
import com.resolvomt.api.model.Prestador;
import com.resolvomt.api.repository.PrestadorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PrestadorService {

    private final PrestadorRepository prestadorRepository;

    public PrestadorService(PrestadorRepository prestadorRepository) {
        this.prestadorRepository = prestadorRepository;
    }

    // CREATE
    public PrestadorResponseDTO criar(PrestadorCreateDTO dto) {
        Prestador p = new Prestador();
        p.setNome(dto.nome());
        p.setCpf(dto.cpf());                 // ADICIONADO
        p.setEmail(dto.email());
        p.setTelefone(dto.telefone());
        p.setEndereco(dto.endereco());

        prestadorRepository.save(p);
        return toDTO(p);
    }

    // UPDATE STATUS IDENTIDADE
    public PrestadorResponseDTO atualizarStatusIdentidade(Long id, VerificacaoStatus status) {
        Prestador p = prestadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prestador não encontrado!"));

        p.setVerificacaoIdentidadeStatus(status);
        p.setVerificacaoIdentidadeData(LocalDateTime.now()); // CORRIGIDO
        p.atualizarStatusVerificado();

        prestadorRepository.save(p);
        return toDTO(p);
    }

    // UPDATE STATUS CRIMINAL
    public PrestadorResponseDTO atualizarStatusCriminal(Long id, VerificacaoStatus status) {
        Prestador p = prestadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prestador não encontrado!"));

        p.setVerificacaoCriminalStatus(status);
        p.setVerificacaoCriminalData(LocalDateTime.now());
        p.atualizarStatusVerificado();

        prestadorRepository.save(p);
        return toDTO(p);
    }

    // UPDATE NORMAL
    public PrestadorResponseDTO atualizar(Long id, PrestadorUpdateDTO dto) {
        Prestador p = prestadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prestador não encontrado!"));

        if (dto.nome() != null) p.setNome(dto.nome());
        if (dto.email() != null) p.setEmail(dto.email());
        if (dto.telefone() != null) p.setTelefone(dto.telefone());
        if (dto.endereco() != null) p.setEndereco(dto.endereco());

        prestadorRepository.save(p);
        return toDTO(p);
    }

    // BUSCAR POR ID
    public PrestadorResponseDTO buscarPorId(Long id) {
        Prestador p = prestadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prestador não encontrado!"));
        return toDTO(p);
    }

    // LISTAR RESUMIDO
    public List<PrestadorListDTO> listarTodos() {
        return prestadorRepository.findAll().stream()
                .map(p -> new PrestadorListDTO(
                        p.getId(),
                        p.getNome(),
                        p.getEmail(),
                        p.getPrestadorVerificado()
                ))
                .toList();
    }

    public PrestadorResponseDTO ativarPrestador(Long id) {
        Prestador p = prestadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prestador não encontrado!"));

        p.setPrestadorVerificado(true);
        prestadorRepository.save(p);

        return toDTO(p);
    }

    public PrestadorResponseDTO inativarPrestador(Long id) {
        Prestador p = prestadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prestador não encontrado!"));

        p.setPrestadorVerificado(false);
        prestadorRepository.save(p);

        return toDTO(p);
    }

    public List<PrestadorListDTO> listarApenasVerificados(){
        return prestadorRepository.findAll().stream()
                .filter(Prestador::getPrestadorVerificado)
                .map(p -> new PrestadorListDTO(
                        p.getId(),
                        p.getNome(),
                        p.getEmail(),
                        p.getPrestadorVerificado()
                ))
                .toList();
    }

    public List<PrestadorListDTO> listarPendentesVerificacao() {
        return prestadorRepository.findAll().stream()
                .filter(p ->
                        p.getVerificacaoIdentidadeStatus() == VerificacaoStatus.PENDING ||
                        p.getVerificacaoCriminalStatus() == VerificacaoStatus.PENDING)
                .map(p -> new PrestadorListDTO(
                        p.getId(),
                        p.getNome(),
                        p.getEmail(),
                        p.getPrestadorVerificado()
                ))
                .toList();
    }

    // DELETE
    public void deletar(Long id) {
        Prestador p = prestadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prestador não encontrado!"));

        prestadorRepository.delete(p);
    }

    // CONVERSOR → DTO
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
