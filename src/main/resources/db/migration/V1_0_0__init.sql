CREATE TABLE product (
    id SERIAL PRIMARY KEY,
    code CHAR(10) NOT NULL UNIQUE CHECK (LENGTH(code) = 10),
    name VARCHAR(255) NOT NULL,
    price_eur NUMERIC(10,2) NOT NULL CHECK (price_eur >= 0),
    is_available BOOLEAN DEFAULT TRUE
);

CREATE INDEX idx_product_code ON product (code);
