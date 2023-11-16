package org.elSasen.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductCategory {
    int categoryId;
    String name;
    String description;
}
