CREATE TABLE prestadores (
                             id BIGSERIAL PRIMARY KEY,
                             nome VARCHAR(255) NOT NULL,
                             usuario_id BIGINT NOT NULL UNIQUE,
                             cnpj VARCHAR(14) UNIQUE,
                             telefone VARCHAR(20),
                             verificado BOOLEAN NOT NULL DEFAULT FALSE,
                             ativo BOOLEAN NOT NULL DEFAULT TRUE,

                             plano VARCHAR(20) NOT NULL,
                             status_assinatura VARCHAR(20) NOT NULL,
                             trial_ate DATE,
                             assinatura_ate DATE,

                             CONSTRAINT fk_prestadores_usuario
                                 FOREIGN KEY (usuario_id)
                                     REFERENCES usuarios(id)
                                     ON DELETE CASCADE,

                             CONSTRAINT ck_prestadores_plano
                                 CHECK (plano IN ('FREE', 'BASIC', 'PREMIUM')),

                             CONSTRAINT ck_prestadores_status
                                 CHECK (status_assinatura IN ('TRIAL', 'ATIVA', 'EXPIRADA', 'CANCELADA'))
);

CREATE INDEX idx_prestadores_usuario ON prestadores(usuario_id);
CREATE INDEX idx_prestadores_verificado ON prestadores(verificado);
CREATE INDEX idx_prestadores_status ON prestadores(status_assinatura);
