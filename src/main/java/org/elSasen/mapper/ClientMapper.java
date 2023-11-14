package org.elSasen.mapper;

import org.elSasen.dto.ClientDto;
import org.elSasen.entities.Client;

public class ClientMapper implements Mapper<Client, ClientDto> {

    private static final ClientMapper INSTANCE = new ClientMapper();

    @Override
    public ClientDto mapFrom(Client client) {
        return ClientDto.builder()
                .clientId(client.getClientId())
                .passport(client.getPassport())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .contact(client.getContact())
                .build();
    }

    public static ClientMapper getInstance() {
        return INSTANCE;
    }
}
