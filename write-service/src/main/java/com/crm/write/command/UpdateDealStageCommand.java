package com.crm.write.command;
import com.crm.write.domain.DealStage;
public record UpdateDealStageCommand(String idempotencyKey, String tenantId, String dealId, DealStage newStage) {}
