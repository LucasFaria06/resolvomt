CREATE TABLE servicos (
                          id BIGSERIAL PRIMARY KEY,

                          prestador_id BIGINT NOT NULL,
                          nome VARCHAR(255) NOT NULL,
                          descricao VARCHAR(500) NOT NULL,
                          valor DECIMAL(10,2) NOT NULL,
                          duracao_minutos INTEGER NOT NULL,
                          ativo BOOLEAN NOT NULL DEFAULT TRUE,

                          CONSTRAINT fk_servicos_prestador
                              FOREIGN KEY (prestador_id)
                                  REFERENCES prestadores(id)
                                  ON DELETE CASCADE
);

CREATE INDEX idx_servicos_prestador ON servicos(prestador_id);
CREATE INDEX idx_servicos_ativo ON servicos(ativo);
