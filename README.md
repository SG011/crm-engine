# crm-engine

Distributed, event-sourced CRM backend built for 10M TPS. Java 25, Spring Boot 3.4, Kafka, Cassandra, Redis.

## Architecture

```
Write path: Client → REST (8080) → CommandHandler → Kafka → Cassandra (event store)
Read path:  Client → REST (8081) → QueryController → Redis (materialized views)
Projection: Kafka → ProjectionEngine → Redis
```

- **Event sourcing:** Cassandra stores append-only domain events; current state is always rebuildable
- **CQRS:** Write and read services are independently deployable and scalable
- **Idempotency:** All commands carry an idempotency key; duplicates are silent no-ops
- **Multi-tenancy:** All data scoped to `tenantId`; Kafka partitioned by tenant for ordering

## Running Locally

```bash
docker-compose up -d          # starts Kafka, Cassandra, Redis
mvn spring-boot:run -pl write-service   # port 8080
mvn spring-boot:run -pl read-service    # port 8081
```

## API

| Method | Path | Description |
|--------|------|-------------|
| POST | `/api/contacts` | Create a contact |
| PATCH | `/api/deals/{id}/stage` | Update deal stage |
| GET | `/api/contacts/{tenantId}/{contactId}` | Get contact |

## Tech Stack

Java 25 · Spring Boot 3.4 · Apache Kafka · Apache Cassandra 5 · Redis 7 · Testcontainers · Gatling
