package org.elSasen.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Builder
@Value
public class TariffPlanDto {
    int planId;
    String planName;
    int callMinutes;
    int internetGb;
    int smsNumber;
    int price;
}
