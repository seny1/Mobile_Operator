package org.elSasen.entities;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Contract {
    long contractId;
    TariffPlan plan;
    Client client;
    LocalDate date;
}
