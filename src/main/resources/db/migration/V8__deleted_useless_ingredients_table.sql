ALTER TABLE public.ingredients
    DROP CONSTRAINT ingredients_category_id_fkey;

DROP TABLE public.ingredients CASCADE;