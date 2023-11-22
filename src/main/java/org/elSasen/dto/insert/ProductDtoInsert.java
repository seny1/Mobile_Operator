package org.elSasen.dto.insert;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProductDtoInsert {
    double price;
    String productDescription;
    String productName;
    String categoryName;
    int count;
}
