CREATE TABLE pagamentos (
                            id BIGSERIAL PRIMARY KEY,

                            prestador_id BIGINT NOT NULL,

                            valor DECIMAL(10,2) NOT NULL,
                            referencia_externa VARCHAR(255) NOT NULL,
                            status VARCHAR(50) NOT NULL,

                            criado_em TIMESTAMP NOT NULL,
                            pago_em TIMESTAMP,

                            CONSTRAINT fk_pagamentos_prestador
                                FOREIGN KEY (prestador_id)
                                    REFERENCES prestadores(id)
                                    ON DELETE CASCADE
);
