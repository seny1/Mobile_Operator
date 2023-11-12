package org.elSasen.entities;

import lombok.Data;

@Data
public class ServiceCategory {
    int categoryId;
    String name;
    String difficulty;
    String description;
}
