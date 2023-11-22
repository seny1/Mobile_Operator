package org.elSasen.dto.select;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class StatusDto {
    int statusId;
    String name;
    String description;
}
