-- Cambiar el tipo de delivery_days[] a text[] para compatibilidad con Hibernate varchar arrays.
-- El enum delivery_days se mantiene como validación en la capa de aplicación.
ALTER TABLE suppliers
    ALTER COLUMN days TYPE text[] USING days::text[];
