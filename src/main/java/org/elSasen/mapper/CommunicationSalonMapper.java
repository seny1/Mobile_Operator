package org.elSasen.mapper;

import org.elSasen.dto.CommunicationSalonDto;
import org.elSasen.entities.CommunicationSalon;

public class CommunicationSalonMapper implements Mapper<CommunicationSalon, CommunicationSalonDto> {

    private static final CommunicationSalonMapper INSTANCE = new CommunicationSalonMapper();
    @Override
    public CommunicationSalonDto mapFrom(CommunicationSalon communicationSalon) {
        return CommunicationSalonDto.builder()
                .salonId(communicationSalon.getSalonId())
                .address(communicationSalon.getAddress())
                .employeeNumber(communicationSalon.getEmployeeNumber())
                .build();
    }

    public static CommunicationSalonMapper getInstance() {
        return INSTANCE;
    }
}
