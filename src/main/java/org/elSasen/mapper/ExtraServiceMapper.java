package org.elSasen.mapper;

import org.elSasen.dto.insert.ExtraServiceDtoInsert;
import org.elSasen.dto.select.ExtraServiceDto;
import org.elSasen.entities.ExtraService;
import org.elSasen.entities.ServiceCategory;

public class ExtraServiceMapper implements Mapper<ExtraService, ExtraServiceDto> {

    private static final ExtraServiceMapper INSTANCE = new ExtraServiceMapper();
    public static ExtraServiceMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public ExtraServiceDto mapFrom(ExtraService extraService) {
        return  ExtraServiceDto.builder()
                .serviceId(extraService.getServiceId())
                .serviceDescription(extraService.getServiceDescription())
                .price(extraService.getPrice())
                .serviceName(extraService.getServiceName())
                .category(extraService.getCategory())
                .build();
    }

    public ExtraService mapFrom(ExtraServiceDtoInsert extraServiceDtoInsert) {
        return ExtraService.builder()
                .serviceName(extraServiceDtoInsert.getServiceName())
                .price(extraServiceDtoInsert.getPrice())
                .category(ServiceCategory.builder()
                        .name(extraServiceDtoInsert.getCategoryName())
                        .build())
                .serviceDescription(extraServiceDtoInsert.getServiceDescription())
                .build();
    }
}
