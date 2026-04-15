-- Datos iniciales para desarrollo / base funcional del sistema.
-- El usuario admin queda asociado al empleado "Hernán Coronel".

INSERT INTO categories (id, name, description, type, active)
VALUES
    ('00000000-0000-0000-0000-000000000101', 'Ingredientes base', 'Materias primas y productos de cocina', 'INGREDIENT', true),
    ('00000000-0000-0000-0000-000000000102', 'Productos de venta', 'Bebidas y productos listos para vender', 'PRODUCT', true),
    ('00000000-0000-0000-0000-000000000103', 'Platos', 'Preparaciones para carta', 'DISH', true)
ON CONFLICT (id) DO NOTHING;

INSERT INTO unit_types (id, name, abbreviation)
VALUES
    ('00000000-0000-0000-0000-000000000201', 'Unidad', 'ud'),
    ('00000000-0000-0000-0000-000000000202', 'Kilogramo', 'kg'),
    ('00000000-0000-0000-0000-000000000203', 'Litro', 'l'),
    ('00000000-0000-0000-0000-000000000204', 'Porción', 'por')
ON CONFLICT (id) DO NOTHING;

ALTER TABLE taxes
    ALTER COLUMN value TYPE NUMERIC(5, 3)
    USING value::NUMERIC(5, 3);

INSERT INTO taxes (id, name, value)
VALUES
    ('00000000-0000-0000-0000-000000000301', 'IVA General', 0.210),
    ('00000000-0000-0000-0000-000000000302', 'IVA Reducido', 0.100)
ON CONFLICT (id) DO NOTHING;

INSERT INTO allergens (id, name, description)
VALUES
    ('00000000-0000-0000-0000-000000000401', 'Gluten', 'Cereales con gluten y derivados'),
    ('00000000-0000-0000-0000-000000000402', 'Lacteos', 'Leche y derivados'),
    ('00000000-0000-0000-0000-000000000403', 'Huevo', 'Huevos y productos derivados'),
    ('00000000-0000-0000-0000-000000000404', 'Frutos secos', 'Cacahuetes, nueces y derivados')
ON CONFLICT (id) DO NOTHING;

INSERT INTO employees (id, full_name, role, is_active)
VALUES
    ('00000000-0000-0000-0000-000000000501', 'Hernán Coronel', 'ADMIN', true),
    ('00000000-0000-0000-0000-000000000502', 'Laura Martín', 'COOK', true),
    ('00000000-0000-0000-0000-000000000503', 'Carlos Pérez', 'WAITER', true)
ON CONFLICT (id) DO NOTHING;

INSERT INTO restaurant_tables (id, table_number, capacity, is_active)
VALUES
    ('00000000-0000-0000-0000-000000000601', 1, 2, true),
    ('00000000-0000-0000-0000-000000000602', 2, 2, true),
    ('00000000-0000-0000-0000-000000000603', 3, 4, true),
    ('00000000-0000-0000-0000-000000000604', 4, 4, true),
    ('00000000-0000-0000-0000-000000000605', 5, 6, true),
    ('00000000-0000-0000-0000-000000000606', 6, 8, true)
ON CONFLICT (id) DO NOTHING;

INSERT INTO suppliers (
    id, nif, trade_name, business_name, re_equivalence, type, days, email, phone_1, phone_2, iban, rgseaa_number
)
VALUES
    (
        '00000000-0000-0000-0000-000000000701',
        'B12345678',
        'Frescos del Sur',
        'Frescos del Sur SL',
        false,
        'PERISHABLES',
        ARRAY['MON', 'WED', 'FRI']::delivery_days[],
        'pedidos@frescosdelsur.local',
        '+34 600 111 222',
        '+34 600 111 223',
        'ES22000000000000000000',
        'ES-REG-0001'
    ),
    (
        '00000000-0000-0000-0000-000000000702',
        'B87654321',
        'Bebidas Iberia',
        'Bebidas Iberia SL',
        false,
        'DRINKS',
        ARRAY['TUE', 'THU']::delivery_days[],
        'ventas@bebidasiberia.local',
        '+34 600 222 111',
        '+34 600 222 112',
        'ES22000000000000000001',
        'ES-REG-0002'
    ),
    (
        '00000000-0000-0000-0000-000000000703',
        'B11223344',
        'HostelPro',
        'HostelPro Suministros SL',
        false,
        'KITCHENWARE',
        ARRAY['VAR']::delivery_days[],
        'info@hostelpro.local',
        '+34 600 333 111',
        NULL,
        'ES22000000000000000002',
        'ES-REG-0003'
    )
ON CONFLICT (id) DO NOTHING;

INSERT INTO products (
    id, sku, name, type, category_id, unit_type_id, calories_per_100g, is_active
)
VALUES
    (
        '00000000-0000-0000-0000-000000000801',
        'ING-001',
        'Tomate natural',
        'INGREDIENT',
        '00000000-0000-0000-0000-000000000101',
        '00000000-0000-0000-0000-000000000202',
        18.00,
        true
    ),
    (
        '00000000-0000-0000-0000-000000000802',
        'ING-002',
        'Pechuga de pollo',
        'INGREDIENT',
        '00000000-0000-0000-0000-000000000101',
        '00000000-0000-0000-0000-000000000202',
        165.00,
        true
    ),
    (
        '00000000-0000-0000-0000-000000000803',
        'ING-003',
        'Queso cheddar',
        'INGREDIENT',
        '00000000-0000-0000-0000-000000000101',
        '00000000-0000-0000-0000-000000000202',
        403.00,
        true
    ),
    (
        '00000000-0000-0000-0000-000000000804',
        'ING-004',
        'Pan de hamburguesa',
        'INGREDIENT',
        '00000000-0000-0000-0000-000000000101',
        '00000000-0000-0000-0000-000000000201',
        270.00,
        true
    ),
    (
        '00000000-0000-0000-0000-000000000805',
        'PROD-001',
        'Agua mineral 0,5L',
        'PRODUCT',
        '00000000-0000-0000-0000-000000000102',
        '00000000-0000-0000-0000-000000000201',
        NULL,
        true
    )
ON CONFLICT (id) DO NOTHING;

INSERT INTO product_allergens (id, product_id, allergen_id)
VALUES
    ('00000000-0000-0000-0000-000000000901', '00000000-0000-0000-0000-000000000804', '00000000-0000-0000-0000-000000000401'),
    ('00000000-0000-0000-0000-000000000902', '00000000-0000-0000-0000-000000000803', '00000000-0000-0000-0000-000000000402')
ON CONFLICT (id) DO NOTHING;

INSERT INTO dishes (
    id, name, description, prep_time, price, dish_category_id, is_available, image_url
)
VALUES
    (
        '00000000-0000-0000-0000-000000000901',
        'Hamburguesa de pollo',
        'Hamburguesa casera con pollo, queso y tomate',
        18.00,
        9.50,
        '00000000-0000-0000-0000-000000000103',
        true,
        NULL
    ),
    (
        '00000000-0000-0000-0000-000000000902',
        'Ensalada fresca',
        'Ensalada ligera con tomate y aliño suave',
        10.00,
        7.20,
        '00000000-0000-0000-0000-000000000103',
        true,
        NULL
    ),
    (
        '00000000-0000-0000-0000-000000000903',
        'Tostada de queso',
        'Tostada caliente con queso y tomate',
        8.00,
        5.90,
        '00000000-0000-0000-0000-000000000103',
        true,
        NULL
    )
ON CONFLICT (id) DO NOTHING;

INSERT INTO dish_ingredients (id, dish_id, product_id, quantity, is_optional)
VALUES
    ('00000000-0000-0000-0000-000000001001', '00000000-0000-0000-0000-000000000901', '00000000-0000-0000-0000-000000000804', 1.000, false),
    ('00000000-0000-0000-0000-000000001002', '00000000-0000-0000-0000-000000000901', '00000000-0000-0000-0000-000000000802', 0.180, false),
    ('00000000-0000-0000-0000-000000001003', '00000000-0000-0000-0000-000000000901', '00000000-0000-0000-0000-000000000803', 0.040, false),
    ('00000000-0000-0000-0000-000000001004', '00000000-0000-0000-0000-000000000901', '00000000-0000-0000-0000-000000000801', 0.050, false),
    ('00000000-0000-0000-0000-000000001005', '00000000-0000-0000-0000-000000000902', '00000000-0000-0000-0000-000000000801', 0.150, false),
    ('00000000-0000-0000-0000-000000001006', '00000000-0000-0000-0000-000000000903', '00000000-0000-0000-0000-000000000804', 1.000, false),
    ('00000000-0000-0000-0000-000000001007', '00000000-0000-0000-0000-000000000903', '00000000-0000-0000-0000-000000000803', 0.060, false),
    ('00000000-0000-0000-0000-000000001008', '00000000-0000-0000-0000-000000000903', '00000000-0000-0000-0000-000000000801', 0.030, false)
ON CONFLICT (id) DO NOTHING;

INSERT INTO stock (id, product_id, current_qty, min_stock)
VALUES
    ('00000000-0000-0000-0000-000000001101', '00000000-0000-0000-0000-000000000801', 15.000, 5.000),
    ('00000000-0000-0000-0000-000000001102', '00000000-0000-0000-0000-000000000802', 8.000, 3.000),
    ('00000000-0000-0000-0000-000000001103', '00000000-0000-0000-0000-000000000803', 4.000, 2.000),
    ('00000000-0000-0000-0000-000000001104', '00000000-0000-0000-0000-000000000804', 20.000, 10.000),
    ('00000000-0000-0000-0000-000000001105', '00000000-0000-0000-0000-000000000805', 30.000, 12.000)
ON CONFLICT (id) DO NOTHING;

INSERT INTO auth_users (id, employee_id, username, password_hash, is_active)
VALUES
    (
        '00000000-0000-0000-0000-000000001201',
        '00000000-0000-0000-0000-000000000501',
        'admin@kitchapp.local',
        '$2a$10$6sRt32pLhgFb48gXXzvjZO4OgNaiwcTFtBQz0.tNStoVsoDKlRwjK',
        true
    )
ON CONFLICT (id) DO NOTHING;


