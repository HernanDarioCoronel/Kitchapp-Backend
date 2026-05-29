.PHONY: help up down build restart logs clean rebuild watch rebuild-watch logs-all clean ps db-rollback

COMPOSE = docker compose
APP = backend

up:
	$(COMPOSE) up -d

down:
	$(COMPOSE) down

build:
	$(COMPOSE) build $(APP)

rebuild:
	$(COMPOSE) up -d --build $(APP)

restart:
	$(COMPOSE) restart $(APP)

watch:
	$(COMPOSE) up --watch

rebuild-watch:
	$(COMPOSE) up --watch --build $(APP)

logs:
	$(COMPOSE) logs -f $(APP)

logs-all:
	$(COMPOSE) logs -f

clean:
	$(COMPOSE) down -v --rmi local

ps:
	$(COMPOSE) ps

db-rollback:
	$(COMPOSE) exec postgres sh -c 'psql -U $$POSTGRES_USER -d $$POSTGRES_DB -c "DELETE FROM flyway_schema_history WHERE installed_rank = (SELECT MAX(installed_rank) FROM flyway_schema_history);"'