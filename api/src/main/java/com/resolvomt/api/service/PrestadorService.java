package com.resolvomt.api.service;

import com.resolvomt.api.dto.prestador.PrestadorRegisterRequestDTO;
import com.resolvomt.api.dto.prestador.PrestadorResponseDTO;
import com.resolvomt.api.dto.usuario.UsuarioCreateRequestDTO;
import com.resolvomt.api.enums.StatusAssinatura;
import com.resolvomt.api.enums.TipoUsuario;
import com.resolvomt.api.model.Assinatura;
import com.resolvomt.api.model.Plano;
import com.resolvomt.api.model.Prestador;
import com.resolvomt.api.model.Usuario;
import com.resolvomt.api.repository.AssinaturaRepository;
import com.resolvomt.api.repository.PlanoRepository;
import com.resolvomt.api.repository.PrestadorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PrestadorService {

    private final PrestadorRepository prestadorRepository;
    private final UsuarioService usuarioService;
    private final AssinaturaService assinaturaService;

    public PrestadorService(
            PrestadorRepository prestadorRepository,
            UsuarioService usuarioService,
            AssinaturaService assinaturaService
    ) {
        this.prestadorRepository = prestadorRepository;
        this.usuarioService = usuarioService;
        this.assinaturaService = assinaturaService;
    }

    @Transactional
    public Prestador registrar(PrestadorRegisterRequestDTO dto) {

        String cnpjLimpo = dto.cnpj().replaceAll("\\D", "");

        if (prestadorRepository.existsByCnpj(cnpjLimpo)) {
            throw new IllegalArgumentException("CNPJ já cadastrado");
        }

        UsuarioCreateRequestDTO usuarioRequest = new UsuarioCreateRequestDTO();
        usuarioRequest.setNomeCompleto(dto.nomeCompleto());
        usuarioRequest.setEmail(dto.email());
        usuarioRequest.setSenha(dto.senha());
        usuarioRequest.setTipoUsuario(TipoUsuario.PRESTADOR);

        Usuario usuarioCriado = usuarioService.cadastrar(usuarioRequest);

        Prestador prestador = new Prestador();
        prestador.setUsuario(usuarioCriado);
        prestador.setNome(dto.nome());
        prestador.setCnpj(cnpjLimpo);
        prestador.setTelefone(dto.telefone());
        prestador.setVerificado(false);
        prestador.setAtivo(true);

        Prestador prestadorSalvo = prestadorRepository.save(prestador);

        assinaturaService.criarAssinaturaTrial(prestadorSalvo.getId());

        return prestadorSalvo;
    }

    public Prestador buscarPorId(Long id) {
        return prestadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prestador não encontrado"));
    }

    public Prestador buscarPorEmailUsuario(String email) {
        return prestadorRepository.findByUsuarioEmail(email)
                .orElseThrow(() -> new RuntimeException("Prestador não encontrado"));
    }

    @Transactional(readOnly = true)
    public List<PrestadorResponseDTO> listarPendentesVerificacao() {
        return prestadorRepository.buscarPendentes()
                .stream()
                .map(PrestadorResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PrestadorResponseDTO> listarVerificados() {
        return prestadorRepository.listarVerificados()
                .stream()
                .map(PrestadorResponseDTO::new)
                .toList();
    }

    @Transactional
    public Prestador aprovarPrestador(Long id) {
        Prestador prestador = buscarPorId(id);
        prestador.setVerificado(true);
        return prestador;
    }

    @Transactional
    public Prestador reprovarPrestador(Long id) {
        Prestador prestador = buscarPorId(id);
        prestador.setVerificado(false);
        return prestador;
    }

    @Transactional
    public Prestador ativarPrestador(Long id) {
        Prestador prestador = buscarPorId(id);
        prestador.setAtivo(true);
        return prestador;
    }

    @Transactional
    public Prestador inativarPrestador(Long id) {
        Prestador prestador = buscarPorId(id);
        prestador.setAtivo(false);
        return prestador;
    }

}