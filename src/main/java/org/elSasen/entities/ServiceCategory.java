package org.elSasen.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServiceCategory {
    int categoryId;
    String name;
    String difficulty;
    String description;
}
