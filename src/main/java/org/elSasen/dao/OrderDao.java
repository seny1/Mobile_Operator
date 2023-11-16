package org.elSasen.dao;

import org.elSasen.entities.*;
import org.elSasen.util.ConnectionManager;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OrderDao {
    private static final OrderDao INSTANCE = new OrderDao();
    public Set<Order> getOrderTable() {
        String sql = """
                SELECT *
                FROM "Order"
                JOIN extra_service on "Order".service_id = extra_service.service_id
                join employee on "Order".employee_id = employee.employee_id
                join client on "Order".client_id = client.client_id
                join status on "Order".status_id = status.status_id
                join client_device on "Order".device_id = client_device.device_id
                join service_category on extra_service.category_id = service_category.category_id
                join department on employee.department_id = department.department_id
                join communication_salon on employee.salon_id = communication_salon.salon_id
                join post on employee.post_id = post.post_id
                join employee_passport on employee.passport_id = employee_passport.passport_id
                join employee_contact on employee.contact_id = employee_contact.contact_id
                join role on employee.role_id = role.role_id
                join client_contact on client.contact_id = client_contact.contact_id
                """;
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(sql)) {
            var resultSet = preparedStatement.executeQuery();
            var orderSet = new HashSet<Order>();
            Order order;
            while (resultSet.next()) {
                order = Order.builder()
                        .service(ExtraService.builder()
                                .serviceId(resultSet.getLong("service_id"))
                                .serviceDescription(resultSet.getString("service_description"))
                                .price(resultSet.getDouble("price"))
                                .serviceName(resultSet.getString("service_name"))
                                .category(ServiceCategory.builder()
                                        .categoryId(resultSet.getInt("category_id"))
                                        .name(resultSet.getString("name"))
                                        .difficulty(resultSet.getString("difficulty"))
                                        .description(resultSet.getString("description"))
                                        .build())
                                .build())
                        .employee(Employee.builder()
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
                                .build())
                        .client(Client.builder()
                                .clientId(resultSet.getLong("client_id"))
                                .passport(ClientPassport.builder()
                                        .passportId(resultSet.getLong("passport_id"))
                                        .birthday(resultSet.getObject("birthday", LocalDate.class))
                                        .number(resultSet.getString("number"))
                                        .series(resultSet.getString("series"))
                                        .build())
                                .firstName(resultSet.getString("first_name"))
                                .lastName(resultSet.getString("last_name"))
                                .contact(ClientContact.builder()
                                        .contactId(resultSet.getLong("contact_id"))
                                        .number(resultSet.getString("number"))
                                        .type(resultSet.getString("type"))
                                        .build())
                                .build())
                        .orderId(resultSet.getInt("order_id"))
                        .status(Status.builder()
                                .statusId(resultSet.getInt("status_id"))
                                .name(resultSet.getString("name"))
                                .description(resultSet.getString("description"))
                                .build())
                        .device(ClientDevice.builder()
                                .deviceId(resultSet.getInt("device_id"))
                                .model(resultSet.getString("model"))
                                .clientProblem(resultSet.getString("client_problem"))
                                .build())
                        .comment(resultSet.getString("comment"))
                        .build();
                orderSet.add(order);
            }
            return orderSet;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<String> getMetaData() {
        String sql = """
                SELECT *
                FROM "Order"
                """;
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(sql)) {
            var resultSet = preparedStatement.executeQuery();
            var columnNames = new ArrayList<String>();
            for (int i = 1; i < resultSet.getMetaData().getColumnCount() + 1; i++) {
                columnNames.add(resultSet.getMetaData().getColumnName(i));
            }
            return columnNames;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static OrderDao getInstance() {
        return INSTANCE;
    }
}
