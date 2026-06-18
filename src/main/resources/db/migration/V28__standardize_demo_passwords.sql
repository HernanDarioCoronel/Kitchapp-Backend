-- V28: Establece contraseña conocida para todos los usuarios de demo.
-- Hash bcrypt (cost=10) de: admin123
-- Permite login consistente en entorno de demostración.
UPDATE auth_users
SET password_hash = '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy';
