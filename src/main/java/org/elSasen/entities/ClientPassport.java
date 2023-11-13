package org.elSasen.entities;

import lombok.Builder;
import lombok.Data;

import java.util.Date;


@Data
@Builder
public class ClientPassport {
    long passportId;
    String series;
    String number;
    Date birthday;
}
