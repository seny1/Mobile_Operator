package org.elSasen.validator;

import org.elSasen.dto.insert.CallDtoInsert;
import org.elSasen.service.ClientService;

public class CallValidator implements Validator<CallDtoInsert> {

    private static final CallValidator INSTANCE = new CallValidator();
    @Override
    public ValidationResult isValid(CallDtoInsert callDto) {
        var clientService = ClientService.getInstance();
        var validationResult = new ValidationResult();
        var clientDto = clientService.getClientById(callDto.getClientId());

        if (clientDto.isEmpty()) {
            validationResult.add(Error.of("invalid.client.clientId", "Клиента с таким ID не существует"));
        }

        return validationResult;
    }

    public static CallValidator getInstance() {
        return INSTANCE;
    }
}
