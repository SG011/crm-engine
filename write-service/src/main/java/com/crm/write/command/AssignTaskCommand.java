package com.crm.write.command;
import java.time.Instant;
public record AssignTaskCommand(String idempotencyKey, String tenantId, String assigneeId, String title, Instant dueAt) {}
