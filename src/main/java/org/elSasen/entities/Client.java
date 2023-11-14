package org.elSasen.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Client {
    long clientId;
    ClientPassport passport;
    String firstName;
    String lastName;
    ClientContact contact;
}
