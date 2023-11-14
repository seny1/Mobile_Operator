package org.elSasen.dao;


import org.elSasen.entities.*;
import org.elSasen.util.ConnectionManager;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public class EmployeeDao {

    private static final EmployeeDao INSTANCE = new EmployeeDao();

    public Optional<Employee> findByLoginAndPassword(String login, String password) {
        String sql = """
                SELECT *
                FROM employee
                         JOIN public.department d on d.department_id = employee.department_id
                         JOIN public.communication_salon cs on cs.salon_id = employee.salon_id
                         JOIN public.employee_contact ec on ec.contact_id = employee.contact_id
                         JOIN public.employee_passport ep on employee.passport_id = ep.passport_id
                         JOIN public.post p on employee.post_id = p.post_id
                         JOIN public.role r on employee.role = r.role_id
                WHERE login = ? AND password = ?;
                                
                """;
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);

            var resultSet = preparedStatement.executeQuery();
            Employee employee = null;
            if (resultSet.next()) {
                employee = Employee.builder()
                        .employeeId(resultSet.getLong("employee_id"))
                        .department(Department.builder()
                                .departmentId(resultSet.getLong("department_id"))
                                .departmentName(resultSet.getString("department_name"))
                                .startTime(resultSet.getObject("start_time", LocalTime.class))
                                .endTime(resultSet.getObject("end_time", LocalTime.class))
                                .build())
                        .salon(CommunicationSalon.builder()
                                .salonId(resultSet.getLong("salon_id"))
                                .address(resultSet.getString("address"))
                                .employeeNumber(resultSet.getInt("employee_number"))
                                .build())
                        .firstName(resultSet.getString("first_name"))
                        .lastName(resultSet.getString("last_name"))
                        .post(Post.builder()
                                .postId(resultSet.getInt("post_id"))
                                .postName(resultSet.getString("post_name"))
                                .postDescription(resultSet.getString("post_description"))
                                .build())
                        .passport(EmployeePassport.builder()
                                .passportId(resultSet.getLong("passport_id"))
                                .series(resultSet.getString("series"))
                                .number(resultSet.getString("number"))
                                .birthday(resultSet.getObject("birthday", LocalDate.class))
                                .issueDate(resultSet.getObject("issue_date", LocalDate.class))
                                .placeCode(resultSet.getString("place_code"))
                                .build())
                        .contact(EmployeeContact.builder()
                                .contactId(resultSet.getInt("contact_id"))
                                .workNumber(resultSet.getString("work_number"))
                                .personalNumber(resultSet.getString("personal_number"))
                                .build())
                        .login(resultSet.getString("login"))
                        .password(resultSet.getString("password"))
                        .role(Role.builder()
                                .roleId(resultSet.getLong("role_id"))
                                .roleName(resultSet.getString("role_name"))
                                .description(resultSet.getString("description"))
                                .build())
                        .build();
            }
            return Optional.ofNullable(employee);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static EmployeeDao getInstance() {
        return INSTANCE;
    }
}
