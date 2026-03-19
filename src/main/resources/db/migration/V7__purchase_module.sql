CREATE TYPE supplier_type AS ENUM ('PERISHABLES', 'DRINKS', 'KITCHENWARE', 'APPLIANCES', 'SERVICES');

CREATE TYPE delivery_days AS ENUM ('MON', 'TUE', 'WED', 'THU', 'FRI', 'ORD', 'VAR');

CREATE TYPE purchase_status AS ENUM ('DRAFT', 'SENT', 'RECEIVED', 'VERIFIED', 'CANCELLED');

CREATE TABLE suppliers
(
    id             UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nif            VARCHAR(9)    NOT NULL,
    trade_name     VARCHAR(100),
    business_name  varchar(100)  NOT NULL,
    RE_equivalence BOOLEAN          DEFAULT FALSE,
    type           supplier_type NOT NULL,
    days           delivery_days[] DEFAULT '{VAR}',
    email          VARCHAR(100),
    phone_1        VARCHAR(20),
    phone_2        VARCHAR(20),
    iban           char(22),
    rgseaa_number  TEXT
);

CREATE TABLE purchase_orders
(
    id           UUID PRIMARY KEY         DEFAULT gen_random_uuid(),
    supplier_id  UUID            NOT NULL REFERENCES suppliers (id) ON DELETE SET NULL,
    order_number VARCHAR(20)     NOT NULL,
    status       purchase_status NOT NULL DEFAULT 'DRAFT',
    created_at   TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    due_date     DATE            NOT NULL,
    updated_at   TIMESTAMP WITH TIME ZONE,
    net_amount   NUMERIC(12, 2)  NOT NULL,
    tax_amount   NUMERIC(12, 2)  NOT NULL,
    total        NUMERIC(12, 2)  NOT NULL,
    notes        TEXT
);

CREATE TABLE purchase_order_line
(
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    order_id      UUID           NOT NULL REFERENCES purchase_orders (id) ON DELETE CASCADE,
    product_id    UUID           NOT NULL REFERENCES products (id) ON DELETE SET NULL,
    description   TEXT           NOT NULL,
    quantity      NUMERIC(12, 3) NOT NULL,
    unit_price    NUMERIC(12, 4) NOT NULL,
    tax_id        UUID           NOT NULL REFERENCES taxes (id) ON DELETE SET NULL,
    line_subtotal NUMERIC(12, 2) GENERATED ALWAYS AS (quantity * unit_price) STORED
);