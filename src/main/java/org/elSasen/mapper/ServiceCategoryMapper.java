package org.elSasen.mapper;

import org.elSasen.dto.select.ServiceCategoryDto;
import org.elSasen.entities.ServiceCategory;

public class ServiceCategoryMapper implements Mapper<ServiceCategory, ServiceCategoryDto> {
    @Override
    public ServiceCategoryDto mapFrom(ServiceCategory serviceCategory) {
        return ServiceCategoryDto.builder()
                .categoryId(serviceCategory.getCategoryId())
                .name(serviceCategory.getName())
                .difficulty(serviceCategory.getDifficulty())
                .description(serviceCategory.getDescription())
                .build();
    }
    private static final ServiceCategoryMapper INSTANCE = new ServiceCategoryMapper();
    public static ServiceCategoryMapper getInstance() {
        return INSTANCE;
    }
}
