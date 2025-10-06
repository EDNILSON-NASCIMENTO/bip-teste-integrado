-- Inserir Beneficio A (ID será 1)
INSERT INTO BENEFICIO (NOME) VALUES ('Beneficio A');
INSERT INTO BENEFICIO_META (BENEFICIO_ID, METAKEY, METAVALUE) VALUES
(1, 'DESCRICAO', 'Descrição detalhada do Benefício A'),
(1, 'VALOR', '1000.00'),
(1, 'ATIVO', 'TRUE');

-- Inserir Beneficio B (ID será 2)
INSERT INTO BENEFICIO (NOME) VALUES ('Beneficio B');
INSERT INTO BENEFICIO_META (BENEFICIO_ID, METAKEY, METAVALUE) VALUES
(2, 'DESCRICAO', 'Descrição detalhada do Benefício B'),
(2, 'VALOR', '500.00'),
(2, 'ATIVO', 'TRUE');