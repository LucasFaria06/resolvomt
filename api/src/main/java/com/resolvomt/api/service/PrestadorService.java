package com.resolvomt.api.service;

import com.resolvomt.api.dto.prestador.PrestadorRegisterRequestDTO;
import com.resolvomt.api.dto.usuario.UsuarioCreateRequestDTO;
import com.resolvomt.api.enums.TipoUsuario;
import com.resolvomt.api.model.Prestador;
import com.resolvomt.api.model.Usuario;
import com.resolvomt.api.repository.PrestadorRepository;
import org.springframework.stereotype.Service;


@Service
public class PrestadorService {

    private final PrestadorRepository prestadorRepository;
    private final UsuarioService usuarioService;

    public PrestadorService(PrestadorRepository prestadorRepository, UsuarioService usuarioService) {
        this.prestadorRepository = prestadorRepository;
        this.usuarioService = usuarioService;
    }

    public Prestador registrar(PrestadorRegisterRequestDTO dto) {

        UsuarioCreateRequestDTO usuarioRequest = new UsuarioCreateRequestDTO();
        usuarioRequest.setNomeCompleto(dto.nomeCompleto());
        usuarioRequest.setEmail(dto.email());
        usuarioRequest.setSenha(dto.senha());
        usuarioRequest.setTipoUsuario(TipoUsuario.PRESTADOR);

        Usuario usuarioCriado = usuarioService.cadastrar(usuarioRequest);

        Prestador prestador = new Prestador();
        prestador.setUsuario(usuarioCriado);
        prestador.setCnpj(dto.cnpj());
        prestador.setTelefone(dto.telefone());
        prestador.setVerificado(false);
        prestador.setAtivo(true);

        return prestadorRepository.save(prestador);
    }

    public Prestador buscarPorEmailUsuario(String email) {
        return prestadorRepository.findByUsuarioEmail(email)
                .orElseThrow(() -> new RuntimeException("Prestador não encontrado!"));
    }
}