package org.elSasen.dto;

import lombok.Builder;
import lombok.Value;
import org.elSasen.entities.ProductCategory;
@Value
@Builder
public class ProductDto {
    long productId;
    double price;
    String productDescription;
    String productName;
    ProductCategory category;
    int count;
}
