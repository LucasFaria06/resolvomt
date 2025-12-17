package com.resolvomt.api.repository;

import com.resolvomt.api.model.Prestador;
import com.resolvomt.api.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServicoRepository extends JpaRepository<Servico, Long> {
    List<Servico> findByPrestadorId(Long prestadorId);

    List<Servico> findByPrestadorVerificadoTrueAndPrestadorAtivoTrue();
}
