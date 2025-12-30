INSERT INTO usuarios (nome_completo, email, senha, tipo_usuario, data_cadastro)
VALUES (
           'Admin Sistema',
           'admin@sistema.com',
           '$2a$10$fVRh.1ry9yuyMR7PoVfAteszuw8aa5Rrqd02ryxCmK1.fQjqqdaWO',
           'ADMIN',
           NOW()
       );