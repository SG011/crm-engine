package com.crm.write.store;

import org.springframework.data.cassandra.repository.CassandraRepository;
import java.util.List;

public interface EventRepository extends CassandraRepository<EventEntity, String> {
    List<EventEntity> findByEntityIdOrderByVersionAsc(String entityId);
}
