package com.resolvomt.api.controller.admin;

import com.resolvomt.api.dto.prestador.PrestadorResponseDTO;
import com.resolvomt.api.repository.PrestadorRepository;
import com.resolvomt.api.service.PrestadorService;
import jakarta.persistence.PessimisticLockException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.PropertyResourceBundle;

@RestController
@RequestMapping("/api/admin/prestadores")
@PreAuthorize("hasRole('ADMIN')")
public class AdminPrestadorController {

    private final PrestadorService prestadorService;

    public AdminPrestadorController(PrestadorService prestadorService) {
        this.prestadorService = prestadorService;
    }

    @GetMapping
    public List<PrestadorResponseDTO> listarTodos() {
        return prestadorService.listarTodos()
                .stream()
                .map(PrestadorResponseDTO::new)
                .toList();
    }

    @GetMapping("/pendentes")
    public List<PrestadorResponseDTO> pendentes() {
        return prestadorService.listarPendentesVerificacao()
                .stream()
                .map(PrestadorResponseDTO::new)
                .toList();
    }

    @PatchMapping("/{id}/aprovar")
    public PrestadorResponseDTO aprovar(@PathVariable Long id){
        return new PrestadorResponseDTO(
                prestadorService.aprovarPrestador(id)
        );
    }

    @PatchMapping("/{id}/reprovar")
    public PrestadorResponseDTO reprovar(@PathVariable Long id){
        return new PrestadorResponseDTO(
                prestadorService.reprovarPrestador(id)
        );
    }

    @PatchMapping("/{id}/ativar")
    public PrestadorResponseDTO ativar(@PathVariable Long id){
        return new PrestadorResponseDTO(
                prestadorService.ativarPrestador(id)
        );
    }
    @PatchMapping("/{id}/inativar")
    public PrestadorResponseDTO inativar(@PathVariable Long id){
        return new PrestadorResponseDTO(
                prestadorService.inativarPrestador(id)
        );
    }
}
