ALTER TABLE dish_ingredients ADD COLUMN equivalent_in_grams NUMERIC(10, 3) NOT NULL DEFAULT 0;
ALTER TABLE dish_ingredients ALTER COLUMN equivalent_in_grams DROP DEFAULT;
