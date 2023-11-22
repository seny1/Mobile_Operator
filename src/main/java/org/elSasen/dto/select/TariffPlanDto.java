package org.elSasen.dto.select;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class TariffPlanDto {
    long planId;
    String planName;
    int callMinutes;
    int internetGb;
    int smsNumber;
    int price;
}
