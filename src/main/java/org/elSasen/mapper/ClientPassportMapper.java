package org.elSasen.mapper;

import org.elSasen.dto.select.ClientPassportDto;
import org.elSasen.entities.ClientPassport;

public class ClientPassportMapper implements Mapper<ClientPassport, ClientPassportDto> {

    private static final ClientPassportMapper INSTANCE = new ClientPassportMapper();
    @Override
    public ClientPassportDto mapFrom(ClientPassport clientPassport) {
        return ClientPassportDto.builder()
                .passportId(clientPassport.getPassportId())
                .series(clientPassport.getSeries())
                .number(clientPassport.getNumberOfPassport())
                .birthday(clientPassport.getBirthday())
                .build();
    }

    public static ClientPassportMapper getInstance() {
        return INSTANCE;
    }
}
