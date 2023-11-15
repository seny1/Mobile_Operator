package org.elSasen.dto;

import lombok.Builder;
import lombok.Value;
import org.elSasen.entities.Client;
import org.elSasen.entities.Product;

@Value
@Builder
public class CheckDto {
    Product product;
    int productCount;
    long checkId;
    Client client;
}
