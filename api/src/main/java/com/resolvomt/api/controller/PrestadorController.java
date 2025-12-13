package com.resolvomt.api.controller;

import com.resolvomt.api.dto.prestador.PrestadorCreateDTO;
import com.resolvomt.api.dto.prestador.PrestadorListDTO;
import com.resolvomt.api.dto.prestador.PrestadorResponseDTO;
import com.resolvomt.api.dto.prestador.PrestadorUpdateDTO;
import com.resolvomt.api.enums.VerificacaoStatus;
import com.resolvomt.api.service.PrestadorService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prestadores")
public class PrestadorController {

    private final PrestadorService prestadorService;

    public PrestadorController(PrestadorService prestadorService) {
        this.prestadorService = prestadorService;
    }

    // ============================================================
    // CRUD BÁSICO
    // ============================================================

    @PostMapping
    public PrestadorResponseDTO criar(@RequestBody PrestadorCreateDTO dto) {
        return prestadorService.criar(dto);
    }

    @GetMapping("/{id}")
    public PrestadorResponseDTO buscarPorId(@PathVariable Long id) {
        return prestadorService.buscarPorId(id);
    }

    @GetMapping
    public List<PrestadorListDTO> listar() {
        return prestadorService.listarTodos();
    }

    @PatchMapping("/{id}")
    public PrestadorResponseDTO atualizar(
            @PathVariable Long id,
            @RequestBody PrestadorUpdateDTO dto
    ) {
        return prestadorService.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        prestadorService.deletar(id);
    }

    // ============================================================
    // VERIFICAÇÕES (IDENTIDADE / ANTECEDENTES)
    // ============================================================

    @PatchMapping("/{id}/verificacao/identidade")
    public PrestadorResponseDTO atualizarIdentidade(
            @PathVariable Long id,
            @RequestParam VerificacaoStatus status
    ) {
        return prestadorService.atualizarStatusIdentidade(id, status);
    }

    @PatchMapping("/{id}/verificacao/criminal")
    public PrestadorResponseDTO atualizarCriminal(
            @PathVariable Long id,
            @RequestParam VerificacaoStatus status
    ) {
        return prestadorService.atualizarStatusCriminal(id, status);
    }

    // ============================================================
    // STATUS PROFISSIONAL DO PRESTADOR
    // ============================================================

    @PatchMapping("/{id}/ativar")
    public PrestadorResponseDTO ativar(@PathVariable Long id) {
        return prestadorService.ativarPrestador(id);
    }

    @PatchMapping("/{id}/inativar")
    public PrestadorResponseDTO inativar(@PathVariable Long id) {
        return prestadorService.inativarPrestador(id);
    }

    // ============================================================
    // BUSCAS ESPECÍFICAS (OPCIONAIS)
    // ============================================================

    @GetMapping("/verificados")
    public List<PrestadorListDTO> listarVerificados() {
        return prestadorService.listarApenasVerificados();
    }

    @GetMapping("/pendentes-verificacao")
    public List<PrestadorListDTO> listarPendentesVerificacao() {
        return prestadorService.listarPendentesVerificacao();
    }
}
