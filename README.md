# Kitchapp Backend

Backend de la aplicación de hostelería Kitchapp. Gestiona operaciones de restaurante: catálogo de platos y productos, pedidos, cocina, inventario, compras, pagos, reservas y gestión de empleados.

> Este repositorio contiene **solo el backend**. La aplicación de escritorio está en `Kitchapp-Backoffice`.

## Stack técnico

| Tecnología | Versión |
|------------|---------|
| Java | 21 |
| Spring Boot | 4.0.1 |
| PostgreSQL | 17 (pgvector) |
| Flyway | 11.7 |
| Springdoc OpenAPI | 2.8.9 |
| JWT (JJWT) | 0.12.6 |
| MapStruct + Lombok | 1.5.5 / 1.18.30 |
| Docker Compose | — |

## Módulos funcionales

- **Catálogo** — platos, productos, categorías, alérgenos, tipos de unidad, imágenes
- **Personal** — empleados con roles (COOK, WAITER, BARMAN, ADMIN, DEVELOPER)
- **Operaciones** — mesas, ocupaciones de mesa, pedidos, platos de pedido
- **Cocina** — estado de preparación de platos en tiempo real
- **Pagos / caja** — pagos, cajones de efectivo, impuestos
- **Reservas** — reservas de clientes con estado
- **Inventario** — stock, movimientos de inventario
- **Compras** — órdenes de compra con líneas, proveedores

## Requisitos previos

- **JDK 21** — [Descargar](https://adoptium.net/)
- **Docker Desktop** — para PostgreSQL y CloudBeaver
- **Maven** opcional (se incluye Maven Wrapper `mvnw` / `mvnw.cmd`)

## Puesta en marcha paso a paso

### 1. Clonar el repositorio

```bash
git clone <url-del-repositorio>
cd Kitchapp-Backend
```

### 2. Configurar variables de entorno

Copia el archivo de ejemplo y rellena los valores:

```bash
cp .env.example .env
```

Edita `.env` con tus valores:

```dotenv
# Base de datos PostgreSQL
ENV_POSTGRES_USER=postgres
ENV_POSTGRES_PASSW=postgres
ENV_DATABASE_NAME=kitchapp

# JWT — usa una cadena larga y aleatoria en producción
ENV_JWT_SECRET=clave-secreta-muy-larga-y-aleatoria-minimo-32-chars
ENV_JWT_ACCESS_MINUTES=480
ENV_JWT_REFRESH_DAYS=7

# Almacenamiento de imágenes
ENV_STORAGE_PATH=uploads
ENV_STORAGE_BASE_URL=http://localhost:8080
```

> Los valores de `ENV_POSTGRES_USER`, `ENV_POSTGRES_PASSW` y `ENV_DATABASE_NAME` deben coincidir con los que Docker usa para crear el contenedor.

### 3. Levantar la base de datos con Docker

```bash
docker compose up -d
```

Esto arranca tres servicios:

| Servicio | Puerto | Descripción |
|----------|--------|-------------|
| `postgres` | 5432 | PostgreSQL 17 con pgvector |
| `backend` | 8080 | API Spring Boot (si usas el Dockerfile) |
| `cloudbeaver` | 8978 | DBeaver web para explorar la BD |

Verifica que los contenedores están sanos:

```bash
docker compose ps
```

> Si prefieres ejecutar el backend con Maven directamente (recomendado en desarrollo), para el servicio `backend` de Docker y usa solo `postgres` y `cloudbeaver`.

### 4. Ejecutar el backend con Maven

```bash
# Windows
.\mvnw.cmd clean spring-boot:run

# macOS / Linux
./mvnw clean spring-boot:run
```

Spring Boot aplica automáticamente las migraciones de Flyway al arrancar. Al finalizar verás:

```
Started KitchAppApplication in X.XXX seconds
```

### 5. Verificar que funciona

Abre el navegador y accede a Swagger UI:

```
http://localhost:8080/swagger-ui/index.html
```

### 6. Primer acceso

El seed de datos crea automáticamente un usuario administrador:

| Campo | Valor |
|-------|-------|
| Email | `admin@kitchapp.local` |
| Contraseña | `Admin123!` |

Para autenticarte en Swagger UI:
1. Haz `POST /api/auth/login` con las credenciales anteriores.
2. Copia el `accessToken` de la respuesta.
3. Pulsa **Authorize** (botón arriba a la derecha) e introduce: `Bearer <tu-token>`.

> Cambia la contraseña del administrador en cuanto levantes el entorno de producción.

## Explorar la base de datos (CloudBeaver)

1. Abre `http://localhost:8978`.
2. Crea una nueva conexión PostgreSQL con:
   - **Host:** `postgres` (nombre del servicio Docker, no `localhost`)
   - **Puerto:** `5432`
   - **Base de datos:** valor de `ENV_DATABASE_NAME`
   - **Usuario / Password:** valores de `ENV_POSTGRES_USER` / `ENV_POSTGRES_PASSW`

## Estructura del proyecto

```
src/main/java/es/coronelhernan/kitchapp/backend/KitchApp/
├── api/              # Controllers REST, DTOs, mappers
├── application/      # Casos de uso / servicios de aplicación
├── domain/           # Entidades, repositorios (interfaces), enums, servicios de dominio
└── infrastructure/   # Implementaciones JPA, seguridad, almacenamiento, config
```

Arquitectura limpia / hexagonal: el dominio no depende de Spring ni de infraestructura.

## API REST

| Módulo | Base path |
|--------|-----------|
| Auth | `/api/auth` |
| Platos | `/api/dishes` |
| Productos | `/api/products` |
| Categorías | `/api/categories` |
| Alérgenos | `/api/allergens` |
| Empleados | `/api/employees` |
| Mesas | `/api/tables` |
| Ocupaciones | `/api/table-occupations` |
| Pedidos | `/api/orders` |
| Reservas | `/api/reservations` |
| Stock | `/api/stock` |
| Movimientos inventario | `/api/inventory-movements` |
| Órdenes de compra | `/api/purchase-orders` |
| Proveedores | `/api/suppliers` |
| Pagos | `/api/payments` |
| Impuestos | `/api/taxes` |
| Tipos de unidad | `/api/unit-types` |
| Cajones de efectivo | `/api/cash-drawers` |
| Imágenes | `/api/images` |

Documentación completa en `http://localhost:8080/swagger-ui/index.html`.

## Migraciones Flyway

Las migraciones se ejecutan automáticamente al arrancar la app (ubicadas en `src/main/resources/db/migration`).

Comandos útiles de Flyway Maven plugin:

```bash
# Windows
.\mvnw.cmd flyway:info      # estado de migraciones
.\mvnw.cmd flyway:validate  # validar esquema

# macOS / Linux
./mvnw flyway:info
./mvnw flyway:validate
```

## Ejecutar tests

```bash
# Windows
.\mvnw.cmd test

# macOS / Linux
./mvnw test
```

Los tests de integración usan **Testcontainers** (requiere Docker activo).

## Generar JAR

```bash
# Windows
.\mvnw.cmd clean package -DskipTests

# macOS / Linux
./mvnw clean package -DskipTests
```

El JAR queda en `target/KitchApp-0.0.1-SNAPSHOT.jar`.

## Variables de entorno — referencia completa

| Variable | Requerida | Descripción | Ejemplo |
|----------|-----------|-------------|---------|
| `ENV_POSTGRES_USER` | Sí | Usuario PostgreSQL | `postgres` |
| `ENV_POSTGRES_PASSW` | Sí | Contraseña PostgreSQL | `postgres` |
| `ENV_DATABASE_NAME` | Sí | Nombre de la base de datos | `kitchapp` |
| `ENV_JWT_SECRET` | Sí | Secreto para firmar JWT (mínimo 32 chars) | `mi-clave-secreta-...` |
| `ENV_JWT_ACCESS_MINUTES` | No | Expiración del access token en minutos | `480` (8h) |
| `ENV_JWT_REFRESH_DAYS` | No | Expiración del refresh token en días | `7` |
| `ENV_STORAGE_PATH` | No | Ruta local para imágenes subidas | `uploads` |
| `ENV_STORAGE_BASE_URL` | No | URL base para servir imágenes | `http://localhost:8080` |

## Licencia

Este proyecto incluye archivo `LICENSE` en la raíz del repositorio.
