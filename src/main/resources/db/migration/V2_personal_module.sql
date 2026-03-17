CREATE TYPE employee_role AS ENUM ('COOK', 'WAITER', 'BARMAN');

CREATE TABLE employees
(
    id         UUID PRIMARY KEY         DEFAULT gen_random_uuid(),
    full_name  VARCHAR(200)  NOT NULL,
    role       employee_role NOT NULL   DEFAULT 'WAITER',
    is_active  BOOLEAN                  DEFAULT true,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);