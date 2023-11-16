package org.elSasen.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExtraService {
    long serviceId;
    String serviceDescription;
    double price;
    String serviceName;
    ServiceCategory category;
}
