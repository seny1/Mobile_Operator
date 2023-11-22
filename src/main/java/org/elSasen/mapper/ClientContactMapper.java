package org.elSasen.mapper;

import org.elSasen.dto.select.ClientContactDto;
import org.elSasen.entities.ClientContact;

public class ClientContactMapper implements Mapper<ClientContact, ClientContactDto> {

    private static final ClientContactMapper INSTANCE = new ClientContactMapper();
    @Override
    public ClientContactDto mapFrom(ClientContact clientContact) {
        return ClientContactDto.builder()
                .contactId(clientContact.getContactId())
                .number(clientContact.getNumber())
                .type(clientContact.getType())
                .build();
    }

    public static ClientContactMapper getInstance() {
        return INSTANCE;
    }
}
