CREATE TABLE clientes (
                          id BIGSERIAL PRIMARY KEY,
                          usuario_id BIGINT NOT NULL UNIQUE,
                          cpf VARCHAR(11) UNIQUE,
                          telefone VARCHAR(20),

                          CONSTRAINT fk_clientes_usuario
                              FOREIGN KEY (usuario_id)
                                  REFERENCES usuarios(id)
                                  ON DELETE CASCADE
);

CREATE INDEX idx_clientes_usuario ON clientes(usuario_id);
