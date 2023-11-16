package org.elSasen.entities;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Data
@Builder
public class Contract {
    long contractId;
    TariffPlan plan;
    Client client;
    Date date;
}
