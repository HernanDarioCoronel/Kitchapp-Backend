.PHONY: help up down build restart logs clean rebuild watch

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

logs:
	$(COMPOSE) logs -f $(APP)

logs-all:
	$(COMPOSE) logs -f

clean:
	$(COMPOSE) down -v --rmi local

ps:
	$(COMPOSE) ps