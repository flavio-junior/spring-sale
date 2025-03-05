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

INSERT INTO
    tb_category (name)
VALUES
    ('Bebidas'),
    ('Carnes'),
    ('Frutas'),
    ('Verduras'),
    ('Legumes'),
    ('Laticínios'),
    ('Padaria'),
    ('Cereais'),
    ('Doces'),
    ('Salgados'),
    ('Higiene Pessoal'),
    ('Limpeza'),
    ('Congelados'),
    ('Enlatados'),
    ('Temperos'),
    ('Massas'),
    ('Molhos'),
    ('Azeites e Óleos'),
    ('Grãos'),
    ('Farináceos'),
    ('Peixes'),
    ('Embutidos'),
    ('Utensílios Domésticos'),
    ('Bebidas Alcoólicas'),
    ('Snacks'),
    ('Produtos de Bebê'),
    ('Pet Shop'),
    ('Mercearia'),
    ('Chás e Cafés'),
    ('Suplementos');

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
    (1, 15),
    (1, 16),
    (1, 17),
    (1, 18),
    (1, 19),
    (1, 20),
    (1, 21),
    (1, 22),
    (1, 23),
    (1, 24),
    (1, 25),
    (1, 26),
    (1, 27),
    (1, 28),
    (1, 29),
    (1, 30);

INSERT INTO
    tb_product (created_at, name, price, stock_quantity)
VALUES
     (DATE_TRUNC('second', NOW()), 'Água Mineral 500ml', 2.50, 100),
     (DATE_TRUNC('second', NOW()), 'Refrigerante Cola 2L', 7.00, 80),
     (DATE_TRUNC('second', NOW()), 'Suco de Laranja 1L', 5.50, 60),
     (DATE_TRUNC('second', NOW()), 'Chá Gelado 900ml', 6.00, 50),
     (DATE_TRUNC('second', NOW()), 'Energético 250ml', 8.00, 40),
     (DATE_TRUNC('second', NOW()), 'Picanha Bovina 1kg', 55.00, 30),
     (DATE_TRUNC('second', NOW()), 'Frango Inteiro 2kg', 18.00, 50),
     (DATE_TRUNC('second', NOW()), 'Costela Suína 1kg', 25.00, 40),
     (DATE_TRUNC('second', NOW()), 'Carne Moída 500g', 15.00, 60),
     (DATE_TRUNC('second', NOW()), 'Linguiça Calabresa 400g', 12.00, 70),
     (DATE_TRUNC('second', NOW()), 'Maçã Gala 1kg', 6.50, 90),
     (DATE_TRUNC('second', NOW()), 'Banana Prata 1kg', 4.00, 100),
     (DATE_TRUNC('second', NOW()), 'Laranja Pera 1kg', 3.50, 80),
     (DATE_TRUNC('second', NOW()), 'Uva Thompson 500g', 8.00, 60),
     (DATE_TRUNC('second', NOW()), 'Melancia Inteira', 15.00, 20),
     (DATE_TRUNC('second', NOW()), 'Alface Crespa', 2.00, 100),
     (DATE_TRUNC('second', NOW()), 'Tomate Italiano 1kg', 5.00, 80),
     (DATE_TRUNC('second', NOW()), 'Cebola Roxa 1kg', 4.50, 70),
     (DATE_TRUNC('second', NOW()), 'Rúcula 100g', 3.00, 90),
     (DATE_TRUNC('second', NOW()), 'Cenoura 1kg', 3.50, 85),
     (DATE_TRUNC('second', NOW()), 'Batata Inglesa 1kg', 4.00, 100),
     (DATE_TRUNC('second', NOW()), 'Abóbora Moranga 1kg', 3.00, 60),
     (DATE_TRUNC('second', NOW()), 'Chuchu 500g', 2.50, 80),
     (DATE_TRUNC('second', NOW()), 'Mandioca 1kg', 5.00, 70),
     (DATE_TRUNC('second', NOW()), 'Beterraba 1kg', 4.00, 90),
     (DATE_TRUNC('second', NOW()), 'Leite Integral 1L', 4.50, 100),
     (DATE_TRUNC('second', NOW()), 'Queijo Mussarela 200g', 12.00, 50),
     (DATE_TRUNC('second', NOW()), 'Iogurte Natural 900g', 8.00, 60),
     (DATE_TRUNC('second', NOW()), 'Manteiga 200g', 10.00, 40),
     (DATE_TRUNC('second', NOW()), 'Requeijão 220g', 6.50, 70),
     (DATE_TRUNC('second', NOW()), 'Pão Francês 50g', 0.50, 200),
     (DATE_TRUNC('second', NOW()), 'Bolo de Chocolate 500g', 15.00, 30),
     (DATE_TRUNC('second', NOW()), 'Pão de Forma 350g', 5.00, 80),
     (DATE_TRUNC('second', NOW()), 'Rosca Doce 400g', 7.00, 50),
     (DATE_TRUNC('second', NOW()), 'Croissant 100g', 3.50, 60),
     (DATE_TRUNC('second', NOW()), 'Arroz Branco 5kg', 25.00, 50),
     (DATE_TRUNC('second', NOW()), 'Feijão Preto 1kg', 8.00, 70),
     (DATE_TRUNC('second', NOW()), 'Milho de Pipoca 500g', 4.00, 90),
     (DATE_TRUNC('second', NOW()), 'Aveia em Flocos 900g', 10.00, 60),
     (DATE_TRUNC('second', NOW()), 'Granola 300g', 12.00, 40),
     (DATE_TRUNC('second', NOW()), 'Chocolate ao Leite 90g', 5.00, 100),
     (DATE_TRUNC('second', NOW()), 'Bala de Goma 200g', 3.50, 80),
     (DATE_TRUNC('second', NOW()), 'Biscoito Recheado 130g', 2.50, 120),
     (DATE_TRUNC('second', NOW()), 'Barra de Cereal 40g', 2.00, 150),
     (DATE_TRUNC('second', NOW()), 'Pé de Moleque 200g', 6.00, 60),
     (DATE_TRUNC('second', NOW()), 'Coxinha Congelada 300g', 10.00, 50),
     (DATE_TRUNC('second', NOW()), 'Pastel de Carne 150g', 4.00, 70),
     (DATE_TRUNC('second', NOW()), 'Esfiha de Queijo 100g', 3.50, 80),
     (DATE_TRUNC('second', NOW()), 'Salgadinho de Milho 100g', 3.00, 100),
     (DATE_TRUNC('second', NOW()), 'Amendoim Torrado 200g', 5.00, 90),
     (DATE_TRUNC('second', NOW()), 'Sabonete Líquido 250ml', 8.00, 60),
     (DATE_TRUNC('second', NOW()), 'Shampoo 300ml', 12.00, 50),
     (DATE_TRUNC('second', NOW()), 'Condicionador 300ml', 12.00, 50),
     (DATE_TRUNC('second', NOW()), 'Desodorante Roll-on 50ml', 10.00, 70),
     (DATE_TRUNC('second', NOW()), 'Pasta de Dente 90g', 4.00, 100),
     (DATE_TRUNC('second', NOW()), 'Detergente Líquido 500ml', 3.00, 120),
     (DATE_TRUNC('second', NOW()), 'Água Sanitária 1L', 5.00, 80),
     (DATE_TRUNC('second', NOW()), 'Sabão em Pó 1kg', 10.00, 60),
     (DATE_TRUNC('second', NOW()), 'Amaciante 2L', 15.00, 50),
     (DATE_TRUNC('second', NOW()), 'Desinfetante 900ml', 7.00, 70),
     (DATE_TRUNC('second', NOW()), 'Pizza Congelada 400g', 18.00, 40),
     (DATE_TRUNC('second', NOW()), 'Nuggets 300g', 12.00, 60),
     (DATE_TRUNC('second', NOW()), 'Hambúrguer 672g', 20.00, 50),
     (DATE_TRUNC('second', NOW()), 'Lasanha Congelada 600g', 25.00, 30),
     (DATE_TRUNC('second', NOW()), 'Sorvete 1.5L', 22.00, 40),
     (DATE_TRUNC('second', NOW()), 'Milho Verde Lata 200g', 4.00, 100),
     (DATE_TRUNC('second', NOW()), 'Ervilha Lata 200g', 4.50, 90),
     (DATE_TRUNC('second', NOW()), 'Atum em Lata 170g', 8.00, 70),
     (DATE_TRUNC('second', NOW()), 'Sardinha em Lata 125g', 5.00, 80),
     (DATE_TRUNC('second', NOW()), 'Palmito em Conserva 300g', 12.00, 50),
     (DATE_TRUNC('second', NOW()), 'Sal Refinado 1kg', 2.00, 150),
     (DATE_TRUNC('second', NOW()), 'Pimenta do Reino 50g', 5.00, 80),
     (DATE_TRUNC('second', NOW()), 'Orégano 20g', 3.00, 100),
     (DATE_TRUNC('second', NOW()), 'Açafrão 50g', 6.00, 60),
     (DATE_TRUNC('second', NOW()), 'Colorau 100g', 4.00, 90),
     (DATE_TRUNC('second', NOW()), 'Macarrão Espaguete 500g', 4.50, 100),
     (DATE_TRUNC('second', NOW()), 'Macarrão Parafuso 500g', 4.50, 100),
     (DATE_TRUNC('second', NOW()), 'Nhoque 500g', 8.00, 60),
     (DATE_TRUNC('second', NOW()), 'Capeletti 400g', 10.00, 50),
     (DATE_TRUNC('second', NOW()), 'Ravioli 400g', 12.00, 40),
     (DATE_TRUNC('second', NOW()), 'Molho de Tomate 340g', 3.50, 120),
     (DATE_TRUNC('second', NOW()), 'Molho Pesto 200g', 15.00, 30),
     (DATE_TRUNC('second', NOW()), 'Molho Branco 300g', 6.00, 70),
     (DATE_TRUNC('second', NOW()), 'Molho Barbecue 200g', 8.00, 50),
     (DATE_TRUNC('second', NOW()), 'Ketchup 400g', 5.00, 90),
     (DATE_TRUNC('second', NOW()), 'Azeite Extra Virgem 500ml', 25.00, 40),
     (DATE_TRUNC('second', NOW()), 'Óleo de Soja 900ml', 7.00, 80),
     (DATE_TRUNC('second', NOW()), 'Óleo de Milho 900ml', 8.00, 70),
     (DATE_TRUNC('second', NOW()), 'Azeite de Oliva 250ml', 15.00, 50),
     (DATE_TRUNC('second', NOW()), 'Óleo de Coco 200ml', 20.00, 30),
     (DATE_TRUNC('second', NOW()), 'Grão de Bico 500g', 6.00, 80),
     (DATE_TRUNC('second', NOW()), 'Lentilha 500g', 5.00, 90),
     (DATE_TRUNC('second', NOW()), 'Feijão Branco 500g', 7.00, 70),
     (DATE_TRUNC('second', NOW()), 'Ervilha Seca 500g', 4.50, 100),
     (DATE_TRUNC('second', NOW()), 'Soja em Grão 500g', 5.50, 85),
     (DATE_TRUNC('second', NOW()), 'Farinha de Trigo 1kg', 5.00, 100),
     (DATE_TRUNC('second', NOW()), 'Farinha de Mandioca 1kg', 6.00, 80),
     (DATE_TRUNC('second', NOW()), 'Fubá 500g', 3.00, 120),
     (DATE_TRUNC('second', NOW()), 'Amido de Milho 200g', 2.50, 150),
     (DATE_TRUNC('second', NOW()), 'Farinha de Rosca 300g', 4.00, 90),
     (DATE_TRUNC('second', NOW()), 'Filé de Tilápia 1kg', 30.00, 40),
     (DATE_TRUNC('second', NOW()), 'Salmão em Posta 500g', 50.00, 20),
     (DATE_TRUNC('second', NOW()), 'Camarão 500g', 45.00, 30),
     (DATE_TRUNC('second', NOW()), 'Merluza Filé 1kg', 25.00, 50),
     (DATE_TRUNC('second', NOW()), 'Bacalhau Desfiado 500g', 40.00, 25),
     (DATE_TRUNC('second', NOW()), 'Presunto Fatiado 200g', 10.00, 60),
     (DATE_TRUNC('second', NOW()), 'Salame Italiano 150g', 15.00, 40),
     (DATE_TRUNC('second', NOW()), 'Mortadela 500g', 8.00, 80),
     (DATE_TRUNC('second', NOW()), 'Peito de Peru 200g', 12.00, 50),
     (DATE_TRUNC('second', NOW()), 'Salsicha 500g', 6.00, 100),
     (DATE_TRUNC('second', NOW()), 'Esponja de Aço', 2.00, 150),
     (DATE_TRUNC('second', NOW()), 'Pano de Chão', 3.50, 100),
     (DATE_TRUNC('second', NOW()), 'Vassoura', 15.00, 40),
     (DATE_TRUNC('second', NOW()), 'Rodo 60cm', 10.00, 50),
     (DATE_TRUNC('second', NOW()), 'Luva de Borracha', 5.00, 80),
     (DATE_TRUNC('second', NOW()), 'Cerveja Lata 350ml', 4.00, 100),
     (DATE_TRUNC('second', NOW()), 'Vinho Tinto 750ml', 30.00, 30),
     (DATE_TRUNC('second', NOW()), 'Vodka 900ml', 50.00, 20),
     (DATE_TRUNC('second', NOW()), 'Whisky 1L', 80.00, 15),
     (DATE_TRUNC('second', NOW()), 'Cachaça 900ml', 20.00, 40),
     (DATE_TRUNC('second', NOW()), 'Batata Chips 100g', 5.00, 90),
     (DATE_TRUNC('second', NOW()), 'Torresmo 150g', 8.00, 60),
     (DATE_TRUNC('second', NOW()), 'Castanha de Caju 200g', 15.00, 40),
     (DATE_TRUNC('second', NOW()), 'Mix de Nuts 150g', 12.00, 50),
     (DATE_TRUNC('second', NOW()), 'Pipoca de Micro-ondas 100g', 3.00, 100),
     (DATE_TRUNC('second', NOW()), 'Fralda Descartável P', 25.00, 40),
     (DATE_TRUNC('second', NOW()), 'Leite em Pó 400g', 20.00, 50),
     (DATE_TRUNC('second', NOW()), 'Papinha de Fruta 120g', 3.50, 80),
     (DATE_TRUNC('second', NOW()), 'Lenço Umedecido 50un', 8.00, 60),
     (DATE_TRUNC('second', NOW()), 'Chupeta', 5.00, 70),
     (DATE_TRUNC('second', NOW()), 'Ração para Cachorro 10kg', 50.00, 30),
     (DATE_TRUNC('second', NOW()), 'Ração para Gato 1kg', 15.00, 50),
     (DATE_TRUNC('second', NOW()), 'Petisco para Cão 100g', 5.00, 80),
     (DATE_TRUNC('second', NOW()), 'Areia Higiênica 4kg', 20.00, 40),
     (DATE_TRUNC('second', NOW()), 'Brinquedo para Pet', 10.00, 60),
     (DATE_TRUNC('second', NOW()), 'Açúcar Refinado 1kg', 4.00, 100),
     (DATE_TRUNC('second', NOW()), 'Macarrão Instantâneo 85g', 2.50, 120),
     (DATE_TRUNC('second', NOW()), 'Gelatina em Pó 30g', 1.50, 150),
     (DATE_TRUNC('second', NOW()), 'Fermento em Pó 100g', 3.00, 90),
     (DATE_TRUNC('second', NOW()), 'Leite Condensado 395g', 6.00, 70),
     (DATE_TRUNC('second', NOW()), 'Chá Preto 50g', 5.00, 80),
     (DATE_TRUNC('second', NOW()), 'Café Solúvel 50g', 8.00, 60),
     (DATE_TRUNC('second', NOW()), 'Chá de Camomila 10un', 4.00, 90),
     (DATE_TRUNC('second', NOW()), 'Café em Grão 500g', 20.00, 40),
     (DATE_TRUNC('second', NOW()), 'Chá Verde 50g', 6.00, 70),
     (DATE_TRUNC('second', NOW()), 'Whey Protein 900g', 90.00, 20),
     (DATE_TRUNC('second', NOW()), 'Creatina 300g', 50.00, 30),
     (DATE_TRUNC('second', NOW()), 'Barra Proteica 40g', 5.00, 80),
     (DATE_TRUNC('second', NOW()), 'Glutamina 300g', 60.00, 25),
     (DATE_TRUNC('second', NOW()), 'BCAA 200g', 70.00, 20);

INSERT INTO
    tb_product_category(fk_product, fk_category)
VALUES
    (1, 1),
    (2, 1),
    (3, 1),
    (4, 1),
    (5, 1),
    (6, 2),
    (7, 2),
    (8, 2),
    (9, 2),
    (10, 2),
    (11, 3),
    (12, 3),
    (13, 3),
    (14, 3),
    (15, 3),
    (16, 4),
    (17, 4),
    (18, 4),
    (19, 4),
    (20, 4),
    (21, 5),
    (22, 5),
    (23, 5),
    (24, 5),
    (25, 5),
    (26, 6),
    (27, 6),
    (28, 6),
    (29, 6),
    (30, 6),
    (31, 7),
    (32, 7),
    (33, 7),
    (34, 7),
    (35, 7),
    (36, 8),
    (37, 8),
    (38, 8),
    (39, 8),
    (40, 8),
    (41, 9),
    (42, 9),
    (43, 9),
    (44, 9),
    (45, 9),
    (46, 10),
    (47, 10),
    (48, 10),
    (49, 10),
    (50, 10),
    (51, 11),
    (52, 11),
    (53, 11),
    (54, 11),
    (55, 11),
    (56, 12),
    (57, 12),
    (58, 12),
    (59, 12),
    (60, 12),
    (61, 13),
    (62, 13),
    (63, 13),
    (64, 13),
    (65, 13),
    (66, 14),
    (67, 14),
    (68, 14),
    (69, 14),
    (70, 14),
    (71, 15),
    (72, 15),
    (73, 15),
    (74, 15),
    (75, 15),
    (76, 16),
    (77, 16),
    (78, 16),
    (79, 16),
    (80, 16),
    (81, 17),
    (82, 17),
    (83, 17),
    (84, 17),
    (85, 17),
    (86, 18),
    (87, 18),
    (88, 18),
    (89, 18),
    (90, 18),
    (91, 19),
    (92, 19),
    (93, 19),
    (94, 19),
    (95, 19),
    (96, 20),
    (97, 20),
    (98, 20),
    (99, 20),
    (100, 20),
    (101, 21),
    (102, 21),
    (103, 21),
    (104, 21),
    (105, 21),
    (106, 22),
    (107, 22),
    (108, 22),
    (109, 22),
    (110, 22),
    (111, 23),
    (112, 23),
    (113, 23),
    (114, 23),
    (115, 23),
    (116, 24),
    (117, 24),
    (118, 24),
    (119, 24),
    (120, 24),
    (121, 25),
    (122, 25),
    (123, 25),
    (124, 25),
    (125, 25),
    (126, 26),
    (127, 26),
    (128, 26),
    (129, 26),
    (130, 26),
    (131, 27),
    (132, 27),
    (133, 27),
    (134, 27),
    (135, 27),
    (136, 28),
    (137, 28),
    (138, 28),
    (139, 28),
    (140, 28),
    (141, 29),
    (142, 29),
    (143, 29),
    (144, 29),
    (145, 29),
    (146, 30),
    (147, 30),
    (148, 30),
    (149, 30),
    (150, 30);

INSERT INTO
    tb_company_product (fk_product, fk_company)
VALUES
    (1, 1),
    (2, 1),
    (3, 1),
    (4, 1),
    (5, 1),
    (6, 1),
    (7, 1),
    (8, 1),
    (9, 1),
    (10, 1),
    (11, 1),
    (12, 1),
    (13, 1),
    (14, 1),
    (15, 1),
    (16, 1),
    (17, 1),
    (18, 1),
    (19, 1),
    (20, 1),
    (21, 1),
    (22, 1),
    (23, 1),
    (24, 1),
    (25, 1),
    (26, 1),
    (27, 1),
    (28, 1),
    (29, 1),
    (30, 1),
    (31, 1),
    (32, 1),
    (33, 1),
    (34, 1),
    (35, 1),
    (36, 1),
    (37, 1),
    (38, 1),
    (39, 1),
    (40, 1),
    (41, 1),
    (42, 1),
    (43, 1),
    (44, 1),
    (45, 1),
    (46, 1),
    (47, 1),
    (48, 1),
    (49, 1),
    (50, 1),
    (51, 1),
    (52, 1),
    (53, 1),
    (54, 1),
    (55, 1),
    (56, 1),
    (57, 1),
    (58, 1),
    (59, 1),
    (60, 1),
    (61, 1),
    (62, 1),
    (63, 1),
    (64, 1),
    (65, 1),
    (66, 1),
    (67, 1),
    (68, 1),
    (69, 1),
    (70, 1),
    (71, 1),
    (72, 1),
    (73, 1),
    (74, 1),
    (75, 1),
    (76, 1),
    (77, 1),
    (78, 1),
    (79, 1),
    (80, 1),
    (81, 1),
    (82, 1),
    (83, 1),
    (84, 1),
    (85, 1),
    (86, 1),
    (87, 1),
    (88, 1),
    (89, 1),
    (90, 1),
    (91, 1),
    (92, 1),
    (93, 1),
    (94, 1),
    (95, 1),
    (96, 1),
    (97, 1),
    (98, 1),
    (99, 1),
    (100, 1),
    (101, 1),
    (102, 1),
    (103, 1),
    (104, 1),
    (105, 1),
    (106, 1),
    (107, 1),
    (108, 1),
    (109, 1),
    (110, 1),
    (111, 1),
    (112, 1),
    (113, 1),
    (114, 1),
    (115, 1),
    (116, 1),
    (117, 1),
    (118, 1),
    (119, 1),
    (120, 1),
    (121, 1),
    (122, 1),
    (123, 1),
    (124, 1),
    (125, 1),
    (126, 1),
    (127, 1),
    (128, 1),
    (129, 1),
    (130, 1),
    (131, 1),
    (132, 1),
    (133, 1),
    (134, 1),
    (135, 1),
    (136, 1),
    (137, 1),
    (138, 1),
    (139, 1),
    (140, 1),
    (141, 1),
    (142, 1),
    (143, 1),
    (144, 1),
    (145, 1),
    (146, 1),
    (147, 1),
    (148, 1),
    (149, 1),
    (150, 1);