package org.elSasen.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Check {
    Product product;
    int productCount;
    long checkId;
    Client client;
}
