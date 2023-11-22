package org.elSasen.mapper;

import org.elSasen.dto.select.ProductCategoryDto;
import org.elSasen.entities.ProductCategory;

public class ProductCategoryMapper implements Mapper<ProductCategory, ProductCategoryDto> {
    @Override
    public ProductCategoryDto mapFrom(ProductCategory productCategory) {
        return ProductCategoryDto.builder()
                .categoryId(productCategory.getCategoryId())
                .name(productCategory.getName())
                .description(productCategory.getDescription())
                .build();
    }
    private static final ProductCategoryMapper INSTANCE = new ProductCategoryMapper();
    public static ProductCategoryMapper getInstance() {
        return INSTANCE;
    }
}
