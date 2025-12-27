CREATE TABLE conversas (
                           id BIGSERIAL PRIMARY KEY,
                           agendamento_id BIGINT NOT NULL UNIQUE,
                           criada_em TIMESTAMP NOT NULL,

                           CONSTRAINT fk_conversa_agendamento
                               FOREIGN KEY (agendamento_id)
                                   REFERENCES agendamentos (id)
                                   ON DELETE CASCADE
);

CREATE TABLE mensagens (
                           id BIGSERIAL PRIMARY KEY,
                           conversa_id BIGINT NOT NULL,
                           remetente_id BIGINT NOT NULL,
                           conteudo TEXT NOT NULL,
                           enviada_em TIMESTAMP NOT NULL,
                           lida BOOLEAN NOT NULL DEFAULT FALSE,

                           CONSTRAINT fk_mensagem_conversa
                               FOREIGN KEY (conversa_id)
                                   REFERENCES conversas (id)
                                   ON DELETE CASCADE,

                           CONSTRAINT fk_mensagem_remetente
                               FOREIGN KEY (remetente_id)
                                   REFERENCES usuarios (id)
                                   ON DELETE CASCADE
);
