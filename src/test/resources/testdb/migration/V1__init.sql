CREATE
    TABLE
    product(
               id BIGSERIAL PRIMARY KEY,
               code VARCHAR(10) NOT NULL UNIQUE CHECK(
                   LENGTH(code)= 10
                   ),
               name VARCHAR(255) NOT NULL,
               price_eur NUMERIC(
                      10,
                      2
                      ) NOT NULL CHECK(
                   price_eur >= 0
                   ),
               is_available BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE
    INDEX idx_product_code ON
    product(code);

CREATE
    TABLE
    users(
             id BIGSERIAL PRIMARY KEY,
             username VARCHAR(255) NOT NULL UNIQUE,
             password VARCHAR(255) NOT NULL
);
