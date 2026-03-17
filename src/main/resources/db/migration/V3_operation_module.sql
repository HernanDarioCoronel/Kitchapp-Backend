CREATE TYPE order_status AS ENUM ('WAITING', 'IN_PREPARATION', 'DONE', 'DELIVERED', 'PAID');

CREATE TYPE payment_method AS ENUM ('CASH', 'CREDIT_CARD', 'DEBIT_CARD', 'ONLINE', 'TRANSFER');

CREATE TABLE tables
(
    id           UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    table_number SMALLINT NOT NULL UNIQUE CHECK (table_number > 0),
    capacity     SMALLINT CHECK (capacity > 0),
    is_active    BOOLEAN          DEFAULT true
);

CREATE TABLE orders
(
    id          UUID PRIMARY KEY         DEFAULT gen_random_uuid(),
    table_id    UUID           REFERENCES tables (id) ON DELETE SET NULL,
    employee_id UUID           REFERENCES employees (id) ON DELETE SET NULL,
    status      order_status   NOT NULL  DEFAULT 'WAITING',
    tip         NUMERIC(10, 2) NOT NULL  DEFAULT 0 CHECK (tip >= 0),
    created_at  TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    closed_at   TIMESTAMP WITH TIME ZONE

);

CREATE TABLE payments
(
    id             UUID PRIMARY KEY         DEFAULT gen_random_uuid(),
    order_id       UUID           NOT NULL REFERENCES orders (id) ON DELETE CASCADE,
    method         payment_method NOT NULL,
    amount         NUMERIC(10, 2) NOT NULL CHECK (amount > 0),
    transaction_id VARCHAR(100), -- payment device reference
    created_at     TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE order_product
(
    id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    order_id   UUID REFERENCES orders (id) ON DELETE CASCADE,
    product_id UUID          REFERENCES products (id) ON DELETE SET NULL,
    count      INT           NOT NULL CHECK ( count > 0 ),
    total      NUMERIC(5, 2) NOT NULL CHECK ( total > 0 )
);

CREATE TABLE order_dish
(
    id       UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    order_id UUID REFERENCES orders (id) ON DELETE CASCADE,
    dish_id  UUID          REFERENCES dishes (id) ON DELETE SET NULL,
    count    INT           NOT NULL CHECK ( count > 0 ),
    total    NUMERIC(5, 2) NOT NULL CHECK ( total > 0 )
);