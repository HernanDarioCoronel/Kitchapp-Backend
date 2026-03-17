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