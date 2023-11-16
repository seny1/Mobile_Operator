package org.elSasen.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProductCategoryDto {
    int categoryId;
    String name;
    String description;
}
