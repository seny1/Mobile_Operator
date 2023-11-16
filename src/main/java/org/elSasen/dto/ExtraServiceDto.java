package org.elSasen.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Value;
import org.elSasen.entities.ServiceCategory;

@Value
@Builder
public class ExtraServiceDto {
    long serviceId;
    String serviceDescription;
    double price;
    String serviceName;
    ServiceCategory category;
}
