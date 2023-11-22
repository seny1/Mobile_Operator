package org.elSasen.validator;

import org.elSasen.dto.insert.CheckDtoInsert;
import org.elSasen.service.ClientService;
import org.elSasen.service.ProductService;

public class CheckValidator implements Validator<CheckDtoInsert> {

    private static final CheckValidator INSTANCE = new CheckValidator();
    @Override
    public ValidationResult isValid(CheckDtoInsert checkDto) {
        var clientService = ClientService.getInstance();
        var productService = ProductService.getInstance();
        var validationResult = new ValidationResult();
        var clientDto = clientService.getClientById(checkDto.getClientId());
        var productNames = productService.getProductNames();

        if (clientDto.isEmpty()) {
            validationResult.add(Error.of("invalid.client.clientId", "Клиента с таким ID не существует"));
        }

        boolean productNameFlag = false;
        for (int i = 0; i < checkDto.getProductName().length; i++) {
            for (String productName : productNames) {
                if (checkDto.getProductName()[i].equals(productName)) {
                    productNameFlag = true;
                    break;
                }
            }
        }
        if (!productNameFlag) {
            validationResult.add(Error.of("invalid.product.name", "Такого продукта не существует"));
        }

        boolean productCountFlag = true;
        var productNamesInCheck = checkDto.getProductName();
        var productCountsInCheck = checkDto.getProductCount();
        for (int i = 0; i < productCountsInCheck.length; i++) {
            if (productService.getProductByName(productNamesInCheck[i]).isPresent()) {
                if (productCountsInCheck[i] - productService.getProductByName(productNamesInCheck[i]).get().getCount() > 0) {
                    productCountFlag = false;
                    break;
                }
            } else {
                validationResult.add(Error.of("invalid.product.name", "Такого продукта не существует"));
            }
        }
        if (!productCountFlag) {
            validationResult.add(Error.of("invalid.product.productCount", "Такого количества товара нет в наличии"));
        }

        return validationResult;
    }

    public static CheckValidator getInstance() {
        return INSTANCE;
    }
}
