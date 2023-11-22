package org.elSasen.dto.select;

import lombok.Builder;
import lombok.Value;
import org.elSasen.entities.ClientContact;
import org.elSasen.entities.ClientPassport;

@Value
@Builder
public class ClientDto {
    long clientId;
    ClientPassport passport;
    String firstName;
    String lastName;
    ClientContact contact;
}
