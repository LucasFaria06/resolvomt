package com.resolvomt.api.repository;

import com.resolvomt.api.enums.StatusAssinatura;
import com.resolvomt.api.model.Assinatura;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AssinaturaRepository extends JpaRepository<Assinatura, Long> {

    List<Assinatura> findByStatusInAndDataFimBefore(
            List<StatusAssinatura> status,
            LocalDate data
    );

    Optional<Assinatura> findByPrestadorId(Long prestadorId);
}