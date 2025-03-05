CREATE TABLE IF NOT EXISTS tb_company(
    id SERIAL PRIMARY KEY,
    identifier BIGINT NOT NULL,
    date DATE NOT NULL,
    hour TIME NOT NULL,
    name VARCHAR(60) NOT NULL,
    main_image VARCHAR(300)
);

CREATE TABLE IF NOT EXISTS tb_user_company (
    fk_user INT REFERENCES tb_user(id),
    fk_company INT REFERENCES tb_company(id),
    PRIMARY KEY (fk_user, fk_company)
);

CREATE TABLE IF NOT EXISTS tb_category(
    id SERIAL PRIMARY KEY,
    name VARCHAR(60)
);

CREATE TABLE IF NOT EXISTS tb_company_category (
    fk_company INT REFERENCES tb_company(id),
    fk_category INT REFERENCES tb_category(id),
    PRIMARY KEY (fk_company, fk_category)
);

CREATE TABLE IF NOT EXISTS tb_product (
    id SERIAL PRIMARY KEY,
    created_at TIMESTAMP NOT NULL,
    name VARCHAR(100) NOT NULL,
    price NUMERIC(10, 2) DEFAULT 0.0,
    stock_quantity INT NOT NULL
);

CREATE TABLE IF NOT EXISTS tb_company_product (
    fk_company INT REFERENCES tb_company(id),
    fk_product INT REFERENCES tb_product(id),
    PRIMARY KEY (fk_company, fk_product)
);

CREATE TABLE IF NOT EXISTS tb_product_category (
    fk_product INT REFERENCES tb_product(id),
    fk_category INT REFERENCES tb_category(id),
    PRIMARY KEY (fk_product, fk_category)
);

CREATE TABLE IF NOT EXISTS tb_employee (
    id SERIAL PRIMARY KEY,
    created_at TIMESTAMP NOT NULL,
    name VARCHAR(30) NOT NULL,
    function varchar(30) check (function in ('ATTENDANT', 'BOX', 'WAITER')),
    status varchar(30) check (status in ('ENABLED', 'DISABLED'))
);

CREATE TABLE IF NOT EXISTS tb_company_employee (
    fk_employee INT REFERENCES tb_employee(id),
    fk_company INT REFERENCES tb_company(id),
    PRIMARY KEY (fk_employee, fk_company)
);

CREATE TABLE IF NOT EXISTS tb_user_employee (
    fk_user INT REFERENCES tb_user(id),
    fk_employee INT REFERENCES tb_employee(id),
    PRIMARY KEY (fk_user, fk_employee)
);