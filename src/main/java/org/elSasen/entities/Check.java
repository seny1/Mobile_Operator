package org.elSasen.entities;

import lombok.Data;

@Data
public class Check {
    long productId;
    int productCount;
    long CheckId;
    long clientId;
}
