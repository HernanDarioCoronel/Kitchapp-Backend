-- ============================================================
-- Restaurant Database Schema
-- ============================================================

CREATE
EXTENSION IF NOT EXISTS "pgcrypto";

-- ============================================================
-- ENUMS
-- Defined first so all tables can reference them freely
-- ============================================================

CREATE TYPE category_type AS ENUM ('DISH', 'PRODUCT', 'INGREDIENT');

CREATE TYPE employee_role AS ENUM ('COOK', 'WAITER', 'BARMAN');

-- Spaces in enum values cause issues with some ORMs/drivers; use underscores
CREATE TYPE order_status AS ENUM ('WAITING', 'IN_PREPARATION', 'DONE', 'DELIVERED', 'PAID');


-- ============================================================
-- LOOKUP / REFERENCE TABLES
-- Must exist before tables that reference them via FK
-- ============================================================

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
    name         VARCHAR(50) NOT NULL UNIQUE, -- e.g. 'kg', 'litre', 'unit'
    abbreviation VARCHAR(10) NOT NULL UNIQUE
);


-- ============================================================
-- CORE ENTITY TABLES
-- ============================================================

CREATE TABLE ingredients
(
    id                UUID PRIMARY KEY         DEFAULT gen_random_uuid(),
    sku               VARCHAR(50) UNIQUE,
    name              VARCHAR(100) NOT NULL UNIQUE,
    category_id       UUID,
    calories_per_100g NUMERIC(5, 2) CHECK (calories_per_100g > 0),
    unit_type_id      UUID         NOT NULL,
    is_active         BOOLEAN                  DEFAULT true,
    created_at        TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_ingredient_category
        FOREIGN KEY (category_id)
            REFERENCES categories (id)
            ON DELETE SET NULL,

    CONSTRAINT fk_ingredient_unit_type
        FOREIGN KEY (unit_type_id)
            REFERENCES unit_types (id)
            ON DELETE RESTRICT
);

CREATE TABLE products
(
    id                  UUID PRIMARY KEY         DEFAULT gen_random_uuid(),
    sku                 VARCHAR(50) UNIQUE,
    name                VARCHAR(100) NOT NULL UNIQUE,
    product_category_id UUID,
    is_active           BOOLEAN                  DEFAULT true,
    created_at          TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_product_category
        FOREIGN KEY (product_category_id)
            REFERENCES categories (id)
            ON DELETE SET NULL
);

CREATE TABLE dishes
(
    id               UUID PRIMARY KEY         DEFAULT gen_random_uuid(),
    name             VARCHAR(100)  NOT NULL UNIQUE,
    description      TEXT,
    prep_time        NUMERIC(5, 2) NOT NULL CHECK (prep_time > 0),
    price            NUMERIC(8, 2) CHECK (price > 0),
    dish_category_id UUID,
    is_available     BOOLEAN                  DEFAULT true,
    image_url        TEXT,
    created_at       TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_dish_category
        FOREIGN KEY (dish_category_id)
            REFERENCES categories (id)
            ON DELETE SET NULL
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

CREATE TABLE order_details(
                              id          UUID PRIMARY KEY         DEFAULT gen_random_uuid(),

)


-- ============================================================
-- JUNCTION / RELATIONSHIP TABLES
-- ============================================================

CREATE TABLE ingredient_allergens
(
    ingredient_id UUID NOT NULL REFERENCES ingredients (id) ON DELETE CASCADE,
    allergen_id   UUID NOT NULL REFERENCES allergens (id) ON DELETE CASCADE,
    PRIMARY KEY (ingredient_id, allergen_id)
);

CREATE TABLE dish_ingredients
(
    dish_id       UUID           NOT NULL REFERENCES dishes (id) ON DELETE CASCADE,
    ingredient_id UUID           NOT NULL REFERENCES ingredients (id) ON DELETE RESTRICT,
    quantity      NUMERIC(10, 3) NOT NULL CHECK (quantity > 0),
    is_optional   BOOLEAN DEFAULT false,
    PRIMARY KEY (dish_id, ingredient_id)
);
