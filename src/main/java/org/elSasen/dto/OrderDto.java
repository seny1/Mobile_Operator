package org.elSasen.dto;

import lombok.Builder;
import lombok.Value;
import org.elSasen.entities.*;
@Value
@Builder
public class OrderDto {
    ExtraService service;
    Employee employee;
    Client client;
    long orderId;
    Status status;
    ClientDevice device;
    String comment;
}
