CREATE TABLE agendamentos (
                              id BIGSERIAL PRIMARY KEY,
                              cliente_id BIGINT NOT NULL,
                              prestador_id BIGINT NOT NULL,
                              servico_id BIGINT NOT NULL,

                              data_hora TIMESTAMP NOT NULL,
                              status VARCHAR(20) NOT NULL,
                              criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                              CONSTRAINT fk_agendamento_cliente
                                  FOREIGN KEY (cliente_id)
                                      REFERENCES clientes(id),

                              CONSTRAINT fk_agendamento_prestador
                                  FOREIGN KEY (prestador_id)
                                      REFERENCES prestadores(id),

                              CONSTRAINT fk_agendamento_servico
                                  FOREIGN KEY (servico_id)
                                      REFERENCES servicos(id),

                              CONSTRAINT ck_agendamento_status
                                  CHECK (status IN ('CRIADO', 'CONFIRMADO', 'CANCELADO'))
);

CREATE INDEX idx_agendamento_cliente ON agendamentos(cliente_id);
CREATE INDEX idx_agendamento_prestador ON agendamentos(prestador_id);
CREATE INDEX idx_agendamento_status ON agendamentos(status);
