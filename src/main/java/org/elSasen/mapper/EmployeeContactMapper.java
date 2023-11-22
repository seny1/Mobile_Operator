package org.elSasen.mapper;

import org.elSasen.dto.select.EmployeeContactDto;
import org.elSasen.entities.EmployeeContact;

public class EmployeeContactMapper implements Mapper<EmployeeContact, EmployeeContactDto> {

    private static final EmployeeContactMapper INSTANCE = new EmployeeContactMapper();

    @Override
    public EmployeeContactDto mapFrom(EmployeeContact employeeContact) {
        return EmployeeContactDto.builder()
                .contactId(employeeContact.getContactId())
                .workNumber(employeeContact.getWorkNumber())
                .personalNumber(employeeContact.getPersonalNumber())
                .build();
    }

    public static EmployeeContactMapper getInstance() {
        return INSTANCE;
    }
}
