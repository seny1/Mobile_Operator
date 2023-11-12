package org.elSasen.entities;

import lombok.Data;

import java.util.Date;


@Data
public class ClientPassport {
    long passportId;
    String series;
    String number;
    Date birthday;
}
