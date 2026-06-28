package com.crm.write.domain;
import java.math.BigDecimal;
public record Deal(String dealId, String tenantId, String title, BigDecimal value, DealStage stage, String ownerId) {}
