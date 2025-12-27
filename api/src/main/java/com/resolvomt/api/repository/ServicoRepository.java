package com.resolvomt.api.repository;

import com.resolvomt.api.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServicoRepository extends JpaRepository<Servico, Long> {

    List<Servico> findByPrestadorIdAndAtivoTrue(Long prestadorId);

    List<Servico> findByAtivoTrueAndPrestadorVerificadoTrueAndPrestadorAtivoTrue();

    List<Servico> findByPrestadorId(Long id);
}
