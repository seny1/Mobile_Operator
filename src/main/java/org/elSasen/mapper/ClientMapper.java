package org.elSasen.mapper;

import org.elSasen.dto.insert.ClientDtoInsert;
import org.elSasen.dto.select.ClientDto;
import org.elSasen.entities.Client;
import org.elSasen.entities.ClientContact;
import org.elSasen.entities.ClientPassport;

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
                .remainMinutes(client.getRemainMinutes())
                .build();
    }

    public Client mapFrom(ClientDtoInsert clientDto) {
        return Client.builder()
                .passport(ClientPassport.builder()
                        .series(clientDto.getSeries())
                        .numberOfPassport(clientDto.getNumberOfPassport())
                        .birthday(clientDto.getBirthday())
                        .build())
                .firstName(clientDto.getFirstName())
                .lastName(clientDto.getLastName())
                .contact(ClientContact.builder()
                        .numberOfContact(clientDto.getNumberOfContact())
                        .type(clientDto.getType())
                        .build())
                .build();
    }

    public static ClientMapper getInstance() {
        return INSTANCE;
    }
}
