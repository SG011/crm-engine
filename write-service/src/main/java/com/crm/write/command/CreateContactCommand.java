package com.crm.write.command;
public record CreateContactCommand(String idempotencyKey, String tenantId, String name, String email) {}
