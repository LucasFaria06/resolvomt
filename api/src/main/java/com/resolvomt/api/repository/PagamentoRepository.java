package com.resolvomt.api.repository;

import com.resolvomt.api.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Optional;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    Optional<Pagamento> findByReferenciaExterna(String referenciaExterna);

    @Query("""
      SELECT COALESCE(SUM(p.valor), 0) 
      FROM Pagamento p 
      WHERE p.status = 'PAID' 
      AND MONTH(p.pagoEm) = MONTH(CURRENT_DATE)
      AND YEAR(p.pagoEm) = YEAR(CURRENT DATE )
""")
        BigDecimal calcularMRRMensal();
}
