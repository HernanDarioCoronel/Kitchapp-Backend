
BEGIN;
    CREATE TABLE layers (
        id   UUID         NOT NULL DEFAULT gen_random_uuid() PRIMARY KEY,
        name VARCHAR(100) NOT NULL
    );

    ALTER TABLE restaurant_tables
        ADD COLUMN x NUMERIC(10, 2) NOT NULL DEFAULT 0,
        ADD COLUMN y NUMERIC(10, 2) NOT NULL DEFAULT 0,
        ADD COLUMN layer_id UUID REFERENCES layers (id);

    INSERT INTO layers (name) VALUES ('Interior');
    INSERT INTO layers (name) VALUES ('Terraza');
COMMIT;