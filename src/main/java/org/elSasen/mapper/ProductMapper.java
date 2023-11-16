package org.elSasen.mapper;

import org.elSasen.dto.PostDto;
import org.elSasen.dto.ProductDto;
import org.elSasen.entities.Product;

public class ProductMapper implements Mapper<Product, ProductDto> {

    @Override
    public ProductDto mapFrom(Product product) {
        return ProductDto.builder()
                .productId(product.getProductId())
                .price(product.getPrice())
                .productDescription(product.getProductDescription())
                .productName(product.getProductName())
                .category(product.getCategory())
                .count(product.getCount())
                .build();
    }
    private static final ProductMapper INSTANCE = new ProductMapper();
    public static ProductMapper getInstance() {
        return INSTANCE;
    }
}
