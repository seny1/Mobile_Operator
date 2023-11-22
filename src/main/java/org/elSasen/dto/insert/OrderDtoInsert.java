package org.elSasen.dto.insert;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class OrderDtoInsert {
    String serviceName;
    int employeeId;
    int clientId;
    String model;
    String clientProblem;
    String comment;
}
