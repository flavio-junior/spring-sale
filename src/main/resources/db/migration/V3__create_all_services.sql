CREATE TABLE IF NOT EXISTS tb_category(
    id SERIAL PRIMARY KEY,
    name VARCHAR(60)
);

CREATE TABLE IF NOT EXISTS tb_user_category (
    fk_user INT REFERENCES tb_user(id),
    fk_category INT REFERENCES tb_category(id),
    PRIMARY KEY (fk_user, fk_category)
);

CREATE TABLE IF NOT EXISTS tb_product (
    id SERIAL PRIMARY KEY,
    created_at TIMESTAMP NOT NULL,
    name VARCHAR(100) NOT NULL,
    price NUMERIC(10, 2) DEFAULT 0.0,
    stock_quantity INT NOT NULL
);

CREATE TABLE IF NOT EXISTS tb_user_product (
    fk_user INT REFERENCES tb_user(id),
    fk_product INT REFERENCES tb_product(id),
    PRIMARY KEY (fk_user, fk_product)
);

CREATE TABLE IF NOT EXISTS tb_product_category (
    fk_product INT REFERENCES tb_product(id),
    fk_category INT REFERENCES tb_category(id),
    PRIMARY KEY (fk_product, fk_category)
);