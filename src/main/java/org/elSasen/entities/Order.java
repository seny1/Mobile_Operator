package org.elSasen.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Order {
    ExtraService service;
    Employee employee;
    Client client;
    long orderId;
    Status status;
    ClientDevice device;
    String comment;
}
