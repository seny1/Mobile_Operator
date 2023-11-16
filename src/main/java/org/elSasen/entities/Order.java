package org.elSasen.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Order {
    long serviceId;
    Employee employee;
    Client client;
    long orderId;
    Status status;
    ClientDevice device;
    String comment;
}
