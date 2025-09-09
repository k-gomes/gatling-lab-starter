CREATE TABLE IF NOT EXISTS products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(100) NOT NULL,
    description TEXT,
    price NUMERIC(10,2) NOT NULL
    );

-- JDD : 50 000 produits répartis dans 100 catégories
INSERT INTO products (name, category, description, price)
SELECT
    'Product ' || i,
    'Category-' || (i % 100),
    'Description for product ' || i,
    ROUND((random() * 1000)::numeric, 2)
FROM generate_series(1, 500000) AS s(i);

