package org.elSasen.validator;

import org.elSasen.dto.insert.ClientDtoInsert;
import org.elSasen.service.ClientService;

import java.time.LocalDate;
import java.time.Period;

public class ClientValidator implements Validator<ClientDtoInsert> {

    private static final ClientValidator INSTANCE = new ClientValidator();
    @Override
    public ValidationResult isValid(ClientDtoInsert clientDto) {
        var validationResult = new ValidationResult();
        var clientService = ClientService.getInstance();
        var clientTable = clientService.getClientTable(null);

        if (clientDto.getFirstName().length() > 32 || clientDto.getLastName().length() > 32) {
            validationResult.add(Error.of("invalid.length", "Слишком длинное имя или слишком длинная фамилия"));
        }

        if (Period.between(clientDto.getBirthday(), LocalDate.now()).getYears() < 18) {
            validationResult.add(Error.of("invalid.age", "Клиенту нет 18 лет"));
        }

        for (int i = 0; i < clientTable.size(); i++) {
            String seriesAndNumberOfTable = clientTable.get(i).getPassport().getSeries() + clientTable.get(i).getPassport().getNumberOfPassport();
            String seriesAndNumberOfDto = clientDto.getSeries() + clientDto.getNumberOfPassport();
            if (seriesAndNumberOfDto.equals(seriesAndNumberOfTable)) {
                validationResult.add(Error.of("invalid.passport", "Такие паспортные данные уже есть в базе"));
                break;
            }
        }

        for (int i = 0; i < clientTable.size(); i++) {
            var numberOfContactOfDto = clientDto.getNumberOfContact();
            var numberOfContactOfTable = clientTable.get(i).getContact().getNumberOfContact();
            if (numberOfContactOfDto.equals(numberOfContactOfTable)) {
                validationResult.add(Error.of("invalid.contact", "Такой номер телефона уже есть в клиентской базе"));
                break;
            }
        }

        return validationResult;
    }

    public static ClientValidator getInstance() {
        return INSTANCE;
    }
}
