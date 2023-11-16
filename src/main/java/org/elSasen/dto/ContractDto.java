package org.elSasen.dto;

import lombok.Builder;
import lombok.Value;
import org.elSasen.entities.Client;
import org.elSasen.entities.TariffPlan;

import java.time.LocalDate;

@Value
@Builder
public class ContractDto {

    long contractId;
    TariffPlan plan;
    Client client;
    LocalDate date;
}
