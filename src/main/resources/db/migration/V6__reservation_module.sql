CREATE TYPE reservation_status AS ENUM ('CONFIRMED', 'PENDING', 'CANCELLED', 'ARRIVED');

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