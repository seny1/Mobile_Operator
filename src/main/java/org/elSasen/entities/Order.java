package org.elSasen.entities;

import lombok.Data;

@Data
public class Order {
    long serviceId;
    long employeeId;
    long clientId;
    long orderId;
    int statusId;
    int deviceId;
    String comment;
}
