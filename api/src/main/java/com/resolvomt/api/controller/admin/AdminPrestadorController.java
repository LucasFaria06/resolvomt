package com.resolvomt.api.controller.admin;

import com.resolvomt.api.dto.prestador.PrestadorResponseDTO;
import com.resolvomt.api.service.PrestadorService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/prestadores")
@PreAuthorize("hasRole('ADMIN')")
public class AdminPrestadorController {

    private final PrestadorService prestadorService;

    public AdminPrestadorController(PrestadorService prestadorService) {
        this.prestadorService = prestadorService;
    }

    @GetMapping("/pendentes")
    public List<PrestadorResponseDTO> listarPendentes() {
        return prestadorService.listarPendentesVerificacao();
    }

    @GetMapping
    public List<PrestadorResponseDTO> listarVerificados() {
        return prestadorService.listarVerificados();
    }

    @PatchMapping("/{id}/aprovar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void aprovar(@PathVariable Long id) {
        prestadorService.aprovarPrestador(id);
    }

    @PatchMapping("/{id}/reprovar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void reprovar(@PathVariable Long id) {
        prestadorService.reprovarPrestador(id);
    }

    @PatchMapping("/{id}/ativar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativar(@PathVariable Long id) {
        prestadorService.ativarPrestador(id);
    }

    @PatchMapping("/{id}/inativar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativar(@PathVariable Long id) {
        prestadorService.inativarPrestador(id);
    }
}
