package com.resolvomt.api.service;

import com.resolvomt.api.dto.servico.ServicoCreateRequestDTO;
import com.resolvomt.api.dto.servico.ServicoUpdateRequestDTO;
import com.resolvomt.api.model.Prestador;
import com.resolvomt.api.model.Servico;
import com.resolvomt.api.repository.ServicoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServicoService {

    private final ServicoRepository servicoRepository;
    private final PrestadorService prestadorService;

    public ServicoService(ServicoRepository servicoRepository, PrestadorService prestadorService) {
        this.servicoRepository = servicoRepository;
        this.prestadorService = prestadorService;
    }

    @Transactional
    public Servico criar(ServicoCreateRequestDTO dto, String emailPrestador) {
        Prestador prestador = prestadorService.buscarPorEmailUsuario(emailPrestador);

        Servico servico = new Servico();
        servico.setNome(dto.nome());
        servico.setDescricao(dto.descricao());
        servico.setValor(dto.valor());
        servico.setDuracaoMinutos(dto.duracaoMinutos());
        servico.setAtivo(dto.ativo() != null ? dto.ativo() : true);
        servico.setPrestador(prestador);

        return servicoRepository.save(servico);
    }

    @Transactional(readOnly = true)
    public List<Servico> listarPorPrestador(String emailPrestador) {
        Prestador prestador = prestadorService.buscarPorEmailUsuario(emailPrestador);
        return servicoRepository.findByPrestadorId(prestador.getId());
    }

    @Transactional(readOnly = true)
    public List<Servico> listarPublicos() {
        return servicoRepository.findAllPublicosComPrestador();
    }

    @Transactional
    public Servico atualizar(Long id, ServicoUpdateRequestDTO dto, String emailPrestador) {
        Servico servico = buscarPorIdPrestador(id, emailPrestador);

        servico.setNome(dto.nome());
        servico.setDescricao(dto.descricao());
        servico.setValor(dto.valor());
        servico.setDuracaoMinutos(dto.duracaoMinutos());
        servico.setAtivo(dto.ativo() != null ? dto.ativo() : servico.isAtivo());

        return servicoRepository.save(servico);
    }

    @Transactional
    public void deletar(Long id, String emailPrestador) {
        Servico servico = buscarPorIdPrestador(id, emailPrestador);
        servicoRepository.delete(servico);
    }

    @Transactional(readOnly = true)
    public Servico buscarPorIdPrestador(Long id, String emailPrestador) {
        Prestador prestador = prestadorService.buscarPorEmailUsuario(emailPrestador);
        return servicoRepository.findByIdAndPrestadorId(id, prestador.getId())
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado ou não pertence a você"));
    }

    public Servico buscarPorId(Long id) {
        return servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));
    }
}