package com.crm.write.command;
public record LogActivityCommand(String idempotencyKey, String tenantId, String contactId, String activityType, String note) {}
