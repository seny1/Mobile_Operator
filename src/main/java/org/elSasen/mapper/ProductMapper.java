package org.elSasen.mapper;

import org.elSasen.dto.insert.ProductDtoInsert;
import org.elSasen.dto.select.ProductDto;
import org.elSasen.entities.Product;
import org.elSasen.entities.ProductCategory;

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

    public Product mapFrom(ProductDtoInsert productDtoInsert) {
        return Product.builder()
                .price(productDtoInsert.getPrice())
                .productDescription(productDtoInsert.getProductDescription())
                .productName(productDtoInsert.getProductName())
                .category(ProductCategory.builder()
                        .name(productDtoInsert.getCategoryName())
                        .build())
                .count(productDtoInsert.getCount())
                .build();
    }
    private static final ProductMapper INSTANCE = new ProductMapper();
    public static ProductMapper getInstance() {
        return INSTANCE;
    }
}
