package org.elSasen.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ServiceCategoryDto {
    int categoryId;
    String name;
    String difficulty;
    String description;
}
