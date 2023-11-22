package org.elSasen.dto.insert;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class ClientDtoInsert {
    String firstName;
    String lastName;
    String series;
    String numberOfPassport;
    LocalDate birthday;
    String numberOfContact;
    String type;
}
