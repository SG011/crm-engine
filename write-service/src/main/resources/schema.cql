CREATE KEYSPACE IF NOT EXISTS crm
  WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};

CREATE TABLE IF NOT EXISTS crm.events (
  entity_id   TEXT,
  version     BIGINT,
  tenant_id   TEXT,
  event_type  TEXT,
  payload     TEXT,
  occurred_at TIMESTAMP,
  PRIMARY KEY (entity_id, version)
) WITH CLUSTERING ORDER BY (version ASC);
