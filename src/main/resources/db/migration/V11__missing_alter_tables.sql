
ALTER TABLE orders
    DROP COLUMN restaurant_tables_id;

ALTER TABLE orders
    ADD COLUMN table_occupation_id UUID
        REFERENCES table_occupations(id);