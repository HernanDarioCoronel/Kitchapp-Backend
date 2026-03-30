# Kitchapp Backend

Backend de una aplicacion de hosteleria para gestionar operaciones de restaurante: inventario, pedidos, caja, ordenes de cocina y gestion basica de empleados/roles.

> Este repositorio contiene **solo el backend**.

## Stack tecnico

- Java 21
- Spring Boot (`spring-boot-starter-parent` en `pom.xml`)
- Spring Web + Spring Data JPA + WebSocket
- PostgreSQL
- Flyway (migraciones)
- Maven Wrapper (`mvnw`, `mvnw.cmd`)
- Lombok + MapStruct
- Docker Compose (servicio PostgreSQL)

## Modulos funcionales (segun migraciones)

- Catalogo
- Personal
- Operaciones
- Inventario
- Pagos / caja
- Reservas
- Compras

Migraciones disponibles en `src/main/resources/db/migration`.

## Estructura principal

```text
src/main/java/es/coronelhernan/kitchapp/backend/KitchApp/
  api/
  application/
  domain/
  infrastructure/
```

## Roles de empleado (estado actual)

Actualmente definidos en `src/main/java/es/coronelhernan/kitchapp/backend/KitchApp/domain/enums/EmployeeRole.java`:

- `COOK`
- `WAITER`
- `BARMAN`

## Requisitos

- JDK 21
- Docker Desktop (para base de datos local)
- Maven (opcional, recomendado usar wrapper)

## Configuracion local

La app carga variables de entorno desde `.env` (ver `spring.config.import`).

1. Crear `.env` a partir de `.env.example`.
2. Definir estas variables (la app utiliza las tres):

```dotenv
ENV_POSTGRES_USER=postgres
ENV_POSTGRES_PASSW=postgres
ENV_DATABASE_NAME=kitchapp
```

> Nota: `.env.example` actual incluye usuario y password; agrega tambien `ENV_DATABASE_NAME` para que coincida con `application.yml` y `docker-compose.yml`.

## Levantar base de datos (Docker)

```powershell
docker compose up -d
docker compose ps
```

Por defecto se expone PostgreSQL en `localhost:5432`.

## Ejecutar la aplicacion

```powershell
.\mvnw.cmd clean spring-boot:run
```

## Ejecutar tests

```powershell
.\mvnw.cmd test
```

## Generar JAR

```powershell
.\mvnw.cmd clean package
```

## Flyway (migraciones)

Las migraciones se ejecutan al iniciar la app (segun configuracion de Spring/Flyway).

Comandos utiles:

```powershell
.\mvnw.cmd flyway:info
.\mvnw.cmd flyway:validate
```

Si necesitas ejecutar Flyway Maven plugin fuera del contexto de Spring, puede requerir propiedades explicitas (`flyway.url`, `flyway.user`, `flyway.password`).

## Roadmap corto sugerido

- Exponer APIs REST por modulo (`api/controller`).
- Incorporar autenticacion/autorizacion por rol.
- Ampliar tests de integracion de flujos criticos (pedido -> cocina -> caja -> inventario).
- Documentar contratos de API (OpenAPI/Postman).

## Licencia

Este proyecto incluye archivo `LICENSE` en la raiz del repositorio.
