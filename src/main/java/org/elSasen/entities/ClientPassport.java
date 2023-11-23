package org.elSasen.entities;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;


@Data
@Builder
public class ClientPassport {
    long passportId;
    String series;
    String numberOfPassport;
    LocalDate birthday;
}
