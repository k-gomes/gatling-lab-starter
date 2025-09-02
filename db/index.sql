-- DELETE INDEX
DROP INDEX IF EXISTS idx_products_category;
DROP INDEX IF EXISTS idx_products_name;

-- CREATE INDEX
CREATE INDEX idx_products_category ON products(category);
CREATE INDEX idx_products_name ON products(name);