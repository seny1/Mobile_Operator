package org.elSasen.mapper;

import org.elSasen.dao.CallDao;
import org.elSasen.dto.CallDto;
import org.elSasen.dto.EmployeePassportDto;
import org.elSasen.entities.Call;
import org.elSasen.entities.EmployeePassport;

public class EmployeePassportMapper implements Mapper<EmployeePassport, EmployeePassportDto> {

    private static final EmployeePassportMapper INSTANCE = new EmployeePassportMapper();
    public static EmployeePassportMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public EmployeePassportDto mapFrom(EmployeePassport employeePassport) {
        return  EmployeePassportDto.builder()
                .passportId(employeePassport.getPassportId())
                .series(employeePassport.getSeries())
                .number(employeePassport.getNumber())
                .birthday(employeePassport.getBirthday())
                .issueDate(employeePassport.getIssueDate())
                .placeCode(employeePassport.getPlaceCode())
                .build();
    }
}

