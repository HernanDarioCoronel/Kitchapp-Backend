CREATE TABLE auth_users
(
    id            UUID PRIMARY KEY         DEFAULT gen_random_uuid(),
    employee_id   UUID UNIQUE     NOT NULL REFERENCES employees (id),
    username      VARCHAR(100)    NOT NULL UNIQUE,
    password_hash VARCHAR(255)    NOT NULL,
    is_active     BOOLEAN                  DEFAULT true,
    created_at    TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE refresh_tokens
(
    id            UUID PRIMARY KEY         DEFAULT gen_random_uuid(),
    auth_user_id  UUID            NOT NULL REFERENCES auth_users (id),
    token_hash    VARCHAR(128)    NOT NULL UNIQUE,
    expires_at    TIMESTAMP WITH TIME ZONE NOT NULL,
    revoked_at    TIMESTAMP WITH TIME ZONE,
    created_at    TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    replaced_by   UUID REFERENCES refresh_tokens (id)
);

CREATE INDEX idx_refresh_tokens_auth_user_id ON refresh_tokens (auth_user_id);
CREATE INDEX idx_refresh_tokens_expires_at ON refresh_tokens (expires_at);

