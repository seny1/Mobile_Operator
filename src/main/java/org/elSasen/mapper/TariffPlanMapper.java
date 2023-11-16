package org.elSasen.mapper;

import org.elSasen.dto.TariffPlanDto;
import org.elSasen.entities.TariffPlan;

public class TariffPlanMapper implements Mapper<TariffPlan, TariffPlanDto> {
    @Override
    public TariffPlanDto mapFrom(TariffPlan tariffPlan) {
        return TariffPlanDto.builder()
                .planId(tariffPlan.getPlanId())
                .planName(tariffPlan.getPlanName())
                .callMinutes(tariffPlan.getCallMinutes())
                .internetGb(tariffPlan.getInternetGb())
                .smsNumber(tariffPlan.getSmsNumber())
                .price(tariffPlan.getPrice())
                .build();
    }
    private static final TariffPlanMapper INSTANCE = new TariffPlanMapper();
    public static TariffPlanMapper getInstance() {
        return INSTANCE;
    }
}
