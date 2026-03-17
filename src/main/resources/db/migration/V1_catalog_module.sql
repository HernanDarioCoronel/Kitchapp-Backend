CREATE
EXTENSION IF NOT EXISTS "pgcrypto";


CREATE TYPE category_type AS ENUM ('DISH', 'PRODUCT', 'INGREDIENT');

CREATE TYPE product_type AS ENUM ('INGREDIENT', 'PRODUCT');

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
);

CREATE TABLE products
(
    id                UUID PRIMARY KEY         DEFAULT gen_random_uuid(),
    sku               VARCHAR(50) UNIQUE,
    name              VARCHAR(100) NOT NULL UNIQUE,
    type              product_type NOT NULL    DEFAULT 'PRODUCT',
    category_id       UUID         REFERENCES categories (id) ON DELETE SET NULL,
    unit_type_id      UUID         NOT NULL REFERENCES unit_types (id) ON DELETE RESTRICT,
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
