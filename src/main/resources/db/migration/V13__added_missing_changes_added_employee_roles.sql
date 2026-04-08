ALTER TABLE public.refresh_tokens
    ADD CONSTRAINT uc_refresh_tokens_replaced_by UNIQUE (replaced_by);

ALTER TYPE employee_role ADD VALUE IF NOT EXISTS 'ADMIN, DEVELOPER';