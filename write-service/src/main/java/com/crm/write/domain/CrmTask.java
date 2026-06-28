package com.crm.write.domain;
import java.time.Instant;
public record CrmTask(String taskId, String tenantId, String assigneeId, String title, Instant dueAt) {}
