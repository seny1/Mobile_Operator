package org.elSasen.entities;

import lombok.Data;

import java.sql.Date;

@Data
public class Contract {
    long contractId;
    long planId;
    long clientId;
    Date date;
}
