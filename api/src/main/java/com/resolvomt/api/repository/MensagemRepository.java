package com.resolvomt.api.repository;

import com.resolvomt.api.model.Mensagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MensagemRepository extends JpaRepository<Mensagem, Long> {

    List<Mensagem> findByConversaIdOrderByEnviadaEmAsc(Long conversaId);

    @Query("SELECT COUNT(m) FROM Mensagem m WHERE m.conversa.id = :conversaId AND m.lida = false AND m.remetente.id != :usuarioId")
    Long countNaoLidasPorConversa(@Param("conversaId") Long conversaId, @Param("usuarioId") Long usuarioId);

    @Query("SELECT m FROM Mensagem m WHERE m.conversa.id = :conversaId AND m.lida = false AND m.remetente.id != :usuarioId")
    List<Mensagem> findNaoLidasPorConversa(@Param("conversaId") Long conversaId, @Param("usuarioId") Long usuarioId);

    @Modifying
    @Query("UPDATE Mensagem m SET m.lida = true WHERE m.conversa.id = :conversaId AND m.remetente.id != :usuarioId AND m.lida = false")
    int marcarTodasComoLidasPorConversa(@Param("conversaId") Long conversaId, @Param("usuarioId") Long usuarioId);
}
