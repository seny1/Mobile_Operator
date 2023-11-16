package org.elSasen.mapper;

import org.elSasen.dto.EmployeePassportDto;
import org.elSasen.dto.ExtraServiceDto;
import org.elSasen.entities.EmployeePassport;
import org.elSasen.entities.ExtraService;

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
}
