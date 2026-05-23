.PHONY: help up down build restart logs clean rebuild watch rebuild-watch logs-all clean ps

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