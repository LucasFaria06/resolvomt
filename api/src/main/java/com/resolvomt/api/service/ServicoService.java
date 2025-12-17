package com.resolvomt.api.service;

import com.resolvomt.api.dto.servico.ServicoRequestDTO;
import com.resolvomt.api.model.Prestador;
import com.resolvomt.api.model.Servico;
import com.resolvomt.api.repository.ServicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicoService {

    private final ServicoRepository servicoRepository;
    private final PrestadorService prestadorService;

    public ServicoService(ServicoRepository servicoRepository, PrestadorService prestadorService) {
        this.servicoRepository = servicoRepository;
        this.prestadorService = prestadorService;
    }

    public Servico criarServico (String emailPrestador, ServicoRequestDTO dto) {
        Prestador prestador = prestadorService.buscarPorEmailUsuario(emailPrestador);

        Servico servico = new Servico();
        servico.setPrestador(prestador);
        servico.setNome(dto.nome());
        servico.setDescricao(dto.descricao());
        servico.setValor(dto.valor());
        servico.setDuracaoMinutos(dto.duracaoMinutos());

        return servicoRepository.save(servico);
    }

    public List<Servico> listarMeusServicos(String emailPrestador) {
        Prestador prestador = prestadorService.buscarPorEmailUsuario(emailPrestador);
        return servicoRepository.findByPrestadorId(prestador.getId());
    }

    public void excluir(Long id) {
        servicoRepository.deleteById(id);
    }
}
