package org.elSasen.entities;

import lombok.Data;

@Data
public class Product {
    long productId;
    double price;
    String productDescription;
    String productName;
    int categoryId;
    int count;
}
