-- Actualizar constraint de calories_per_100g para permitir 0

-- Eliminar constraint anterior en tabla products
ALTER TABLE products DROP CONSTRAINT IF EXISTS "products_calories_per_100g_check";

-- Agregar nuevo constraint más permisivo
ALTER TABLE products ADD CONSTRAINT "products_calories_per_100g_check" CHECK (calories_per_100g >= 0);
