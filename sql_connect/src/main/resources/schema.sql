CREATE TABLE IF NOT EXISTS shopper (
                                       shopper_id SERIAL PRIMARY KEY,
                                       full_name  VARCHAR(120) NOT NULL,
                                       email      VARCHAR(120) UNIQUE NOT NULL,
                                       phone      VARCHAR(30),
                                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS e_commerce (
                                          product_id SERIAL PRIMARY KEY,
                                          name       VARCHAR(120) NOT NULL,
                                          price      NUMERIC(10,2) NOT NULL CHECK (price >= 0),
                                          stock      INT NOT NULL CHECK (stock >= 0),
                                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

TRUNCATE TABLE e_commerce RESTART IDENTITY;
TRUNCATE TABLE shopper RESTART IDENTITY;
