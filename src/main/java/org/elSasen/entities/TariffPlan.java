package org.elSasen.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TariffPlan {
    long planId;
    String planName;
    int callMinutes;
    int internetGb;
    int smsNumber;
    int price;
}
