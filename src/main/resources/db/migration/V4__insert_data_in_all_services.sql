INSERT INTO
    tb_company (identifier, date, hour, name)
VALUES
    (1741166726157, CURRENT_DATE, CURRENT_TIME, 'Lanchonete Frango Assado'),
    (1741166736984, CURRENT_DATE, CURRENT_TIME, 'Polly Açai');

INSERT INTO
    tb_user_company (fk_company, fk_user)
VALUES
    (1, 1);

INSERT INTO tb_category (name) VALUES
   ('Hortifrúti'),
   ('Carnes e Aves'),
   ('Peixaria'),
   ('Padaria'),
   ('Frios e Laticínios'),
   ('Bebidas'),
   ('Mercearia'),
   ('Limpeza'),
   ('Higiene Pessoal'),
   ('Pet Shop'),
   ('Enlatados e Conservas'),
   ('Biscoitos e Snacks'),
   ('Congelados'),
   ('Produtos Naturais'),
   ('Bebê e Infantil');

INSERT INTO
    tb_company_category (fk_company, fk_category)
VALUES
    (1, 1),
    (1, 2),
    (1, 3),
    (1, 4),
    (1, 5),
    (1, 6),
    (1, 7),
    (1, 8),
    (1, 9),
    (1, 10),
    (1, 11),
    (1, 12),
    (1, 13),
    (1, 14),
    (1, 15);