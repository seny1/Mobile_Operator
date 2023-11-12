package org.elSasen.entities;

import lombok.Data;

@Data
public class Client {
    long clientId;
    long passportId;
    String firstName;
    String lastName;
    long contactId;
}
