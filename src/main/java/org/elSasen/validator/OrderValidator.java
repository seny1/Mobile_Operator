package org.elSasen.validator;

import org.elSasen.dto.insert.OrderDtoInsert;
import org.elSasen.dto.select.EmployeeDto;
import org.elSasen.service.ClientService;
import org.elSasen.service.EmployeeService;
import org.elSasen.service.ExtraServiceService;

import java.util.List;

public class OrderValidator implements Validator<OrderDtoInsert> {

    private static final OrderValidator INSTANCE = new OrderValidator();
    @Override
    public ValidationResult isValid(OrderDtoInsert orderDtoInsert) {
        var validationResult = new ValidationResult();

        var extraServiceService = ExtraServiceService.getInstance();
        var services = extraServiceService.getServices();
        boolean serviceNameFlag = false;
        for (int i = 0; i < services.size(); i++) {
            if (orderDtoInsert.getServiceName().equals(services.get(i))) {
                serviceNameFlag = true;
                break;
            }
        }
        if (!serviceNameFlag) {
            validationResult.add(Error.of("invalid.serviceName", "Такой услуги нет"));
        }

        var clientService = ClientService.getInstance();
        var clientDto = clientService.getClientById(orderDtoInsert.getClientId());
        if (clientDto.isEmpty()) {
            validationResult.add(Error.of("invalid.client.clientId", "Клиента с таким ID не существует"));
        }

        var employeeService = EmployeeService.getInstance();
        var employeeTable = employeeService.getEmployeeTable(null);
        boolean employeeIdFlag = false;
        for (int i = 0; i < employeeTable.size(); i++) {
            if (employeeTable.get(i).getEmployeeId() == orderDtoInsert.getEmployeeId()) {
                employeeIdFlag = true;
                break;
            }
        }
        if (!employeeIdFlag) {
            validationResult.add(Error.of("invalid.employee.employeeId", "Сотрудника с таким ID не существует"));
        }

        if (orderDtoInsert.getModel().length() > 255 || orderDtoInsert.getClientProblem().length() > 255) {
            validationResult.add(Error.of("invalid.length", "Слишком длинное название модели или описание проблемы клиента"));
        }

        if (orderDtoInsert.getComment().length() > 255) {
            validationResult.add(Error.of("invalid.length", "Слишком длинный комментарий"));
        }
        
        return validationResult;
    }

    public static OrderValidator getInstance() {
        return INSTANCE;
    }
}
