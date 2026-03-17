CREATE
EXTENSION IF NOT EXISTS "pgcrypto";


CREATE TYPE category_type AS ENUM ('DISH', 'PRODUCT', 'INGREDIENT');

CREATE TYPE employee_role AS ENUM ('COOK', 'WAITER', 'BARMAN');

CREATE TYPE order_status AS ENUM ('WAITING', 'IN_PREPARATION', 'DONE', 'DELIVERED', 'PAID');

CREATE TYPE supplier_type AS ENUM ('PERISHABLES', 'DRINKS', 'KITCHENWARE', 'APPLIANCES', 'SERVICES');

CREATE TYPE delivery_days AS ENUM ('MON', 'TUE', 'WED', 'THU', 'FRI', 'ORD', 'VAR');

CREATE TYPE purchase_status AS ENUM ('DRAFT', 'SENT', 'RECEIVED', 'VERIFIED', 'CANCELLED');

CREATE TYPE product_type AS ENUM ('INGREDIENT', 'PRODUCT');

CREATE TYPE movement_type AS ENUM ('PURCHASE', 'SALE', 'WASTE', 'ADJUSTMENT');

CREATE TYPE payment_method AS ENUM ('CASH', 'CREDIT_CARD', 'DEBIT_CARD', 'ONLINE', 'TRANSFER');

CREATE TYPE reservation_status AS ENUM ('CONFIRMED', 'PENDING', 'CANCELLED', 'ARRIVED');


CREATE TABLE categories
(
    id          UUID PRIMARY KEY         DEFAULT gen_random_uuid(),
    name        VARCHAR(100)  NOT NULL UNIQUE,
    description TEXT,
    type        category_type NOT NULL   DEFAULT 'INGREDIENT',
    active      BOOLEAN                  DEFAULT true,
    created_at  TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE allergens
(
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name        VARCHAR(100) NOT NULL UNIQUE,
    description TEXT
);

CREATE TABLE unit_types
(
    id           UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name         VARCHAR(50) NOT NULL UNIQUE, -- e.g. 'kg', 'litre', 'unit', 'bags'
    abbreviation VARCHAR(10) NOT NULL UNIQUE
);

CREATE TABLE taxes
(
    id    UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name  varchar(20)   NOT NULL,
    value NUMERIC(2, 3) NOT NULL
)

CREATE TABLE ingredients
(
    id                UUID PRIMARY KEY         DEFAULT gen_random_uuid(),
    sku               VARCHAR(50) UNIQUE,
    name              VARCHAR(100) NOT NULL UNIQUE,
    category_id       UUID         REFERENCES categories (id) ON DELETE SET NULL,
    calories_per_100g NUMERIC(5, 2) CHECK (calories_per_100g > 0),
    unit_type_id      UUID         NOT NULL REFERENCES unit_types (id) ON DELETE RESTRICT,
    is_active         BOOLEAN                  DEFAULT true,
    created_at        TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
);

CREATE TABLE products
(
    id                UUID PRIMARY KEY         DEFAULT gen_random_uuid(),
    sku               VARCHAR(50) UNIQUE,
    name              VARCHAR(100) NOT NULL UNIQUE,
    type              product_type NOT NULL    DEFAULT 'PRODUCT',
    category_id       UUID         NOT NULL REFERENCES categories (id) ON DELETE SET NULL,
    unit_type_id      UUID,
    calories_per_100g NUMERIC(5, 2) CHECK (calories_per_100g > 0),
    is_active         BOOLEAN                  DEFAULT true,
    created_at        TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
);

CREATE TABLE dishes
(
    id               UUID PRIMARY KEY         DEFAULT gen_random_uuid(),
    name             VARCHAR(100)  NOT NULL UNIQUE,
    description      TEXT,
    prep_time        NUMERIC(5, 2) NOT NULL CHECK (prep_time > 0),
    price            NUMERIC(8, 2) CHECK (price > 0),
    dish_category_id UUID          NOT NULL REFERENCES categories (id) ON DELETE SET NULL,
    is_available     BOOLEAN                  DEFAULT true,
    image_url        TEXT,
    created_at       TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE employees
(
    id         UUID PRIMARY KEY         DEFAULT gen_random_uuid(),
    full_name  VARCHAR(200)  NOT NULL,
    role       employee_role NOT NULL   DEFAULT 'WAITER',
    is_active  BOOLEAN                  DEFAULT true,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

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

CREATE TABLE payments
(
    id             UUID PRIMARY KEY         DEFAULT gen_random_uuid(),
    order_id       UUID           NOT NULL REFERENCES orders (id) ON DELETE CASCADE,
    method         payment_method NOT NULL,
    amount         NUMERIC(10, 2) NOT NULL CHECK (amount > 0),
    transaction_id VARCHAR(100), -- payment device reference
    created_at     TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE cash_drawers
(
    id               UUID PRIMARY KEY         DEFAULT gen_random_uuid(),
    opened_at        TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    closed_at        TIMESTAMP WITH TIME ZONE,
    opening_balance  NUMERIC(10, 2) NOT NULL,
    closing_balance  NUMERIC(10, 2) NOT NULL,
    expected_balance NUMERIC(10, 2),
    employee_id      UUID REFERENCES employees (id)
);

CREATE TABLE reservations
(
    id               UUID PRIMARY KEY   DEFAULT gen_random_uuid(),
    customer_name    VARCHAR(100)             NOT NULL,
    customer_phone   VARCHAR(20),
    num_guests       SMALLINT                 NOT NULL CHECK (num_guests > 0),
    reservation_date TIMESTAMP WITH TIME ZONE NOT NULL,
    table_id         UUID REFERENCES tables (id),
    status           reservation_status DEFAULT 'CONFIRMED',
    notes            TEXT
);

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

CREATE TABLE purchase_orders
(
    id           UUID PRIMARY KEY         DEFAULT gen_random_uuid(),
    supplier_id  UUID           NOT NULL REFERENCES suppliers (id) ON DELETE SET NULL,
    order_number VARCHAR(20)    NOT NULL,
    status       order_status   NOT NULL  DEFAULT 'DRAFT',
    created_at   TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    due_date     DATE           NOT NULL,
    updated_at   TIMESTAMP WITH TIME ZONE,
    net_amount   NUMERIC(12, 2) NOT NULL,
    tax_amount   NUMERIC(12, 2) NOT NULL,
    total        NUMERIC(12, 2) NOT NULL,
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

CREATE TABLE product_allergens
(
    product_id  UUID NOT NULL REFERENCES products (id) ON DELETE CASCADE,
    allergen_id UUID NOT NULL REFERENCES allergens (id) ON DELETE CASCADE,
    PRIMARY KEY (product_id, allergen_id)
);

CREATE TABLE dish_ingredients
(
    dish_id     UUID           NOT NULL REFERENCES dishes (id) ON DELETE CASCADE,
    product_id  UUID           NOT NULL REFERENCES products (id) ON DELETE RESTRICT,
    quantity    NUMERIC(10, 3) NOT NULL CHECK (quantity > 0),
    is_optional BOOLEAN DEFAULT false,
    PRIMARY KEY (dish_id, product_id)
)
