package org.elSasen.dto.select;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class ClientPassportDto {
    long passportId;
    String series;
    String number;
    LocalDate birthday;
}
