CREATE TABLE usuarios (
                          id BIGSERIAL PRIMARY KEY,
                          nome_completo VARCHAR(255) NOT NULL,
                          email VARCHAR(255) NOT NULL UNIQUE,
                          senha VARCHAR(255) NOT NULL,
                          tipo_usuario VARCHAR(20) NOT NULL,
                          data_cadastro TIMESTAMP NOT NULL,

                          CONSTRAINT ck_usuarios_tipo CHECK (tipo_usuario IN ('CLIENTE', 'PRESTADOR', 'ADMIN'))
);

CREATE INDEX idx_usuarios_email ON usuarios(email);
CREATE INDEX idx_usuarios_tipo ON usuarios(tipo_usuario);