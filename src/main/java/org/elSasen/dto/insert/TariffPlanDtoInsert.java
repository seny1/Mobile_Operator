package org.elSasen.dto.insert;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TariffPlanDtoInsert {
    String planName;
    int callMinutes;
    int internetGb;
    int smsNumber;
    int price;
}
