ALTER TABLE layers
    ADD COLUMN shape JSONB NOT NULL DEFAULT '[]'::jsonb;
