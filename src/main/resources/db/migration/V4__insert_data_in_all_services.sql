INSERT INTO
    tb_company (identifier, date, hour, name)
VALUES
    (1741166726157, CURRENT_DATE, CURRENT_TIME, 'Lanchonete Frango Assado'),
    (1741166736984, CURRENT_DATE, CURRENT_TIME, 'Polly Açai');

INSERT INTO
    tb_user_company (fk_company, fk_user)
VALUES
    (1, 1),
    (2, 2);
