package org.elSasen.mapper;

import org.elSasen.dto.CheckDto;
import org.elSasen.entities.Check;

public class CheckMapper implements Mapper<Check, CheckDto> {

    private static final CheckMapper INSTANCE = new CheckMapper();

    @Override
    public CheckDto mapFrom(Check check) {
        return CheckDto.builder()
                .product(check.getProduct())
                .productCount(check.getProductCount())
                .checkId(check.getCheckId())
                .client(check.getClient())
                .build();
    }

    public static CheckMapper getInstance() {
        return INSTANCE;
    }
}
