package com.resolvomt.api.service;

import com.resolvomt.api.dto.prestador.PrestadorRegisterRequestDTO;
import com.resolvomt.api.dto.usuario.UsuarioCreateRequestDTO;
import com.resolvomt.api.enums.PlanoPrestador;
import com.resolvomt.api.enums.StatusAssinatura;
import com.resolvomt.api.enums.TipoUsuario;
import com.resolvomt.api.model.Prestador;
import com.resolvomt.api.model.Usuario;
import com.resolvomt.api.repository.PrestadorRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class PrestadorService {

    private final PrestadorRepository prestadorRepository;
    private final UsuarioService usuarioService;

    public PrestadorService(PrestadorRepository prestadorRepository, UsuarioService usuarioService) {
        this.prestadorRepository = prestadorRepository;
        this.usuarioService = usuarioService;
    }

    // ================ REGISTRO ================ //
    @Transactional
    public Prestador registrar(PrestadorRegisterRequestDTO dto) {

        if (prestadorRepository.existsByCnpj(dto.cnpj())){
            throw new RuntimeException(dto.nomeCompleto());
        }
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
        prestador.setPlano(PlanoPrestador.FREE);
        prestador.setStatusAssinatura(StatusAssinatura.TRIAL);
        prestador.setTrialAte(LocalDate.now().plusDays(30));
        prestador.setAssinaturaAte(null);

        return prestadorRepository.save(prestador);
    }

    // =============== PRESTADOR LOGADO =============== //
    public Prestador buscarPorEmailUsuario(String email) {
        return prestadorRepository.findByUsuarioEmail(email)
                .orElseThrow(() -> new RuntimeException("Prestador não encontrado!"));
    }

    // ================== ADMIN ===================== //
    public Prestador aprovarPrestador(Long id){
        Prestador prestador = prestadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prestador não encontrado"));
        prestador.setVerificado(true);
        return prestadorRepository.save(prestador);
    }

    public Prestador reprovarPrestador(Long id) {
        Prestador prestador = buscarPorId(id);
        prestador.setVerificado(false);
        prestador.setAtivo(false);
        return prestadorRepository.save(prestador);
    }

    public Prestador ativarPrestador(Long id) {
        Prestador prestador = prestadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prestador não encontrado"));
        prestador.setAtivo(true);
        return prestadorRepository.save(prestador);
    }

    public Prestador inativarPrestador(Long id) {
        Prestador prestador = prestadorRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Prestador não encontrado"));
        prestador.setAtivo(false);
        return prestadorRepository.save(prestador);
    }

    public Prestador buscarPorId(Long id) {
        return prestadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prestador não encontrado"));
    }

    public List<Prestador> listarVerificados() {
        return prestadorRepository.findByVerificadoTrueAndAtivoTrue();
    }

    public List<Prestador> listarPendentesVerificacao() {
        return prestadorRepository.findByVerificadoFalse();
    }

    public List<Prestador> listarTodos() {
        return prestadorRepository.findAll();
    }

}