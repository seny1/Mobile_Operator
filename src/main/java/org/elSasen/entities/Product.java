package org.elSasen.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product {
    long productId;
    double price;
    String productDescription;
    String productName;
    ProductCategory category;
    int count;
}
