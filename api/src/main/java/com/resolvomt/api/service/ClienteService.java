package com.resolvomt.api.service;

import com.resolvomt.api.dto.cliente.ClienteRegisterRequestDTO;
import com.resolvomt.api.dto.usuario.UsuarioCreateRequestDTO;
import com.resolvomt.api.enums.TipoUsuario;
import com.resolvomt.api.model.Cliente;
import com.resolvomt.api.model.Usuario;
import com.resolvomt.api.repository.ClienteRepository;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final UsuarioService usuarioService;

    public ClienteService(ClienteRepository clienteRepository, UsuarioService usuarioService) {
        this.clienteRepository = clienteRepository;
        this.usuarioService = usuarioService;
    }

    public Cliente registrar(ClienteRegisterRequestDTO dto) {

        UsuarioCreateRequestDTO usuarioRequest = new UsuarioCreateRequestDTO();
        usuarioRequest.setNomeCompleto(dto.nomeCompleto());
        usuarioRequest.setEmail(dto.email());
        usuarioRequest.setSenha(dto.senha());
        usuarioRequest.setTipoUsuario(TipoUsuario.CLIENTE);

        Usuario usuarioCriado = usuarioService.cadastrar(usuarioRequest);

        Cliente cliente = new Cliente();
        cliente.setUsuario(usuarioCriado);

        String cpfLimpo = dto.cpf().replaceAll("\\D", "");
        cliente.setCpf(cpfLimpo);

        cliente.setTelefone(dto.telefone());

        return clienteRepository.save(cliente);
    }

    public Cliente buscarPorEmailUsuario(String email) {
        return clienteRepository.findByUsuarioEmail(email)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }

}
