package org.elSasen.entities;

import lombok.Data;

@Data
public class ExtraService {
    long serviceId;
    String serviceDescription;
    double price;
    String serviceName;
    int categoryId;
}
