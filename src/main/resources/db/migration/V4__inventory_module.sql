CREATE TYPE movement_type AS ENUM ('PURCHASE', 'SALE', 'WASTE', 'ADJUSTMENT');

CREATE TABLE stock
(
    id          UUID PRIMARY KEY         DEFAULT gen_random_uuid(),
    product_id  UUID REFERENCES products (id) ON DELETE CASCADE,
    current_qty NUMERIC(12, 3)           DEFAULT 0,
    min_stock   NUMERIC(12, 3)           DEFAULT 0,
    updated_at  TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE inventory_movements
(
    id          UUID PRIMARY KEY         DEFAULT gen_random_uuid(),
    product_id  UUID           NOT NULL REFERENCES products (id),
    employee_id UUID REFERENCES employees (id),
    quantity    NUMERIC(12, 3) NOT NULL,
    type        movement_type  NOT NULL,
    reason      TEXT,
    created_at  TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);