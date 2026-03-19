ALTER TABLE public.order_product
    DROP CONSTRAINT order_product_order_id_fkey;

ALTER TABLE public.order_product
    DROP CONSTRAINT order_product_product_id_fkey;

CREATE TABLE order_consumable_item
(
    id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    order_id   UUID REFERENCES orders (id) ON DELETE CASCADE,
    product_id UUID          REFERENCES products (id) ON DELETE SET NULL,
    count      INT           NOT NULL CHECK ( count > 0 ),
    total      NUMERIC(5, 2) NOT NULL CHECK ( total > 0 )
);

DROP TABLE public.order_product CASCADE;