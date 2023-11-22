package org.elSasen.validator;

import org.elSasen.dto.insert.EmployeeDtoInsert;
import org.elSasen.service.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;

public class EmployeeValidator implements Validator<EmployeeDtoInsert> {

    private static final EmployeeValidator INSTANCE = new EmployeeValidator();
    @Override
    public ValidationResult isValid(EmployeeDtoInsert employeeDtoInsert) {
        var validationResult = new ValidationResult();

        if (employeeDtoInsert.getLastName().length() > 128 || employeeDtoInsert.getFirstName().length() > 128) {
            validationResult.add(Error.of("invalid.name", "Слишком длинное имя или слишком длинная фамилия"));
        }

        var employeeService = EmployeeService.getInstance();
        var employeeTable = employeeService.getEmployeeTable(null);
        for (int i = 0; i < employeeTable.size(); i++) {
            String seriesAndNumberOfTable = employeeTable.get(i).getPassport().getSeries() + employeeTable.get(i).getPassport().getNumber();
            String seriesAndNumberOfDto = employeeDtoInsert.getSeries() + employeeDtoInsert.getNumberOfPassport();
            if (seriesAndNumberOfDto.equals(seriesAndNumberOfTable)) {
                validationResult.add(Error.of("invalid.passport", "Такие паспортные данные уже есть в базе"));
                break;
            }
        }

        if (Period.between(employeeDtoInsert.getBirthday(), LocalDate.now()).getYears() < 18) {
            validationResult.add(Error.of("invalid.age", "Сотрудник должен быть совершеннолетним"));
        }

        for (int i = 0; i < employeeTable.size(); i++) {
            var workNumberOfDto = employeeDtoInsert.getWorkNumber();
            var workNumberOfTable = employeeTable.get(i).getContact().getWorkNumber();
            if (workNumberOfDto.equals(workNumberOfTable)) {
                validationResult.add(Error.of("invalid.contact", "Такой рабочий номер телефона уже есть в базе"));
                break;
            }
        }

        for (int i = 0; i < employeeTable.size(); i++) {
            var personalNumberOfDto = employeeDtoInsert.getPersonalNumber();
            var personalNumberOfTable = employeeTable.get(i).getContact().getPersonalNumber();
            if (personalNumberOfDto.equals(personalNumberOfTable)) {
                validationResult.add(Error.of("invalid.contact", "Такой личный номер телефона уже есть в базе"));
                break;
            }
        }

        for (int i = 0; i < employeeTable.size(); i++) {
            var loginOfTable = employeeTable.get(i).getLogin();
            var loginOfDto = employeeDtoInsert.getLogin();
            if (loginOfDto.equals(loginOfTable)) {
                validationResult.add(Error.of("invalid.login", "Такой логин уже существует"));
                break;
            }
        }

        var departmentService = DepartmentService.getInstance();
        var departments = departmentService.getDepartments();
        boolean departmentFlag = false;
        for (int i = 0; i < departments.size(); i++) {
            if (departments.get(i).equals(employeeDtoInsert.getDepartmentName())) {
                departmentFlag = true;
                break;
            }
        }
        if (!departmentFlag) {
            validationResult.add(Error.of("invalid.department", "Такого департамента нет"));
        }

        var salonService = CommunicationSalonService.getInstance();
        var salons = salonService.getSalons();
        boolean salonFlag = false;
        for (int i = 0; i < salons.size(); i++) {
            if (salons.get(i).equals(employeeDtoInsert.getSalonAddress())) {
                salonFlag = true;
                break;
            }
        }
        if (!salonFlag) {
            validationResult.add(Error.of("invalid.salon", "Такого салона нет"));
        }

        var postService = PostService.getInstance();
        var posts = postService.getPosts();
        boolean postFlag = false;
        for (int i = 0; i < posts.size(); i++) {
            if (posts.get(i).equals(employeeDtoInsert.getPostName())) {
                postFlag = true;
                break;
            }
        }
        if (!postFlag) {
            validationResult.add(Error.of("invalid.salon", "Такой должности нет"));
        }

        var roleService = RoleService.getInstance();
        var roles = roleService.getRoles();
        boolean roleFlag = false;
        for (int i = 0; i < roles.size(); i++) {
            if (roles.get(i).equals(employeeDtoInsert.getRoleName())) {
                roleFlag = true;
                break;
            }
        }
        if (!roleFlag) {
            validationResult.add(Error.of("invalid.salon", "Такой должности нет"));
        }

        var departmentPost = new HashMap<String, String[]>();
        departmentPost.put("Склад", new String[] {"Грузчик на складе", "Кладовщик", "Заведующий складом"});
        departmentPost.put("Зал", new String[] {"Младший менеджер", "Старший менеджер"});
        departmentPost.put("Бухгалтерия", new String[] {"Бухгалтер"});
        departmentPost.put("Ремонт", new String[] {"Мастер"});
        departmentPost.put("Начальство", new String[] {"Директор салона"});
        var departmentNameDto = employeeDtoInsert.getDepartmentName();
        var postsOfMap = departmentPost.get(departmentNameDto);
        boolean departmentPostFlag = false;
        for (int i = 0; i < postsOfMap.length; i++) {
            if (employeeDtoInsert.getPostName().equals(postsOfMap[i])) {
                departmentPostFlag = true;
                break;
            }
        }
        if (!departmentPostFlag) {
            validationResult.add(Error.of("invalid.postAndDepartment", "У выбранного департамента нет такой должности"));
        }

        return validationResult;
    }

    public static EmployeeValidator getInstance() {
        return INSTANCE;
    }
}
