package org.elSasen.entities;

import lombok.Data;

@Data
public class TariffPlan {
    long planId;
    String planName;
    int callMinutes;
    int internetGb;
    int smsNumber;
    int price;
}
