ALTER TABLE restaurant_tables
    ADD COLUMN name VARCHAR(100) NOT NULL DEFAULT 'temp';

UPDATE restaurant_tables
SET name = id::text;

ALTER TABLE restaurant_tables
    ALTER COLUMN name DROP DEFAULT;
