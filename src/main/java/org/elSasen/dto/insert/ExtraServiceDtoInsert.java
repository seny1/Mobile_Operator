package org.elSasen.dto.insert;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ExtraServiceDtoInsert {

    String serviceDescription;
    double price;
    String serviceName;
    String categoryName;
}
