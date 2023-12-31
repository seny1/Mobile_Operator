package org.elSasen.dao;


import org.elSasen.entities.*;
import org.elSasen.util.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class EmployeeDao {

    private static final EmployeeDao INSTANCE = new EmployeeDao();
    private final DepartmentDao departmentDao = DepartmentDao.getInstance();
    private final CommunicationSalonDao communicationSalonDao = CommunicationSalonDao.getInstance();
    private final PostDao postDao = PostDao.getInstance();
    private final EmployeeContactDao employeeContactDao = EmployeeContactDao.getInstance();
    private final EmployeePassportDao employeePassportDao = EmployeePassportDao.getInstance();
    private final RoleDao roleDao = RoleDao.getInstance();
    private final String DEFAULT_ORDER_BY = "employee_id";

    public Optional<Employee> findByLoginAndPassword(String login, String password) {
        String sql = """
                SELECT *
                FROM employee
                         JOIN public.department d on d.department_id = employee.department_id
                         JOIN public.communication_salon cs on cs.salon_id = employee.salon_id
                         JOIN public.employee_contact ec on ec.contact_id = employee.contact_id
                         JOIN public.employee_passport ep on employee.passport_id = ep.passport_id
                         JOIN public.post p on employee.post_id = p.post_id
                         JOIN public.role r on employee.role_id = r.role_id
                WHERE login = ? AND password = ?;
            """;
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);

            var resultSet = preparedStatement.executeQuery();
            Employee employee = null;
            if (resultSet.next()) {
                employee = buildEmployee(resultSet);
            }
            return Optional.ofNullable(employee);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Employee> getEmployeeTable(String orderBy) {
        if (orderBy == null) {
            orderBy = DEFAULT_ORDER_BY;
        }
        String sql = """
                SELECT employee_id,
                       d.department_id,
                       d.department_name,
                       d.start_time,
                       d.end_time,
                       cs.salon_id,
                       cs.address,
                       cs.employee_number,
                       first_name,
                       last_name,
                       p.post_id,
                       p.post_name,
                       p.post_description,
                       ep.passport_id,
                       ep.series,
                       ep.number,
                       ep.birthday,
                       ep.issue_date,
                       ep.place_code,
                       ec.contact_id,
                       ec.work_number,
                       ec.personal_number,
                       login,
                       password,
                       r.role_id,
                       r.role_name,
                       r.description
                FROM employee
                         JOIN public.department d on d.department_id = employee.department_id
                         JOIN public.communication_salon cs on cs.salon_id = employee.salon_id
                         JOIN public.employee_contact ec on ec.contact_id = employee.contact_id
                         JOIN public.employee_passport ep on employee.passport_id = ep.passport_id
                         JOIN public.post p on employee.post_id = p.post_id
                         JOIN public.role r on employee.role_id = r.role_id
                ORDER BY
                """ + orderBy;
        try (var connection = ConnectionManager.get();
        var preparedStatement = connection.prepareStatement(sql)) {
            var resultSet = preparedStatement.executeQuery();
            Employee employee;
            var employeeList = new ArrayList<Employee>();
            while (resultSet.next()) {
                employee = buildEmployee(resultSet);
                employeeList.add(employee);
            }
            return employeeList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getMetaData() {
        String sql = """
                SELECT *
                FROM employee;
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

    public List<String> getGoodMetaData() {
        String sql = """
                SELECT first_name,
                       last_name,
                       department_name,
                       address,
                       post_name,
                       series,
                       number AS number_of_passport,
                       birthday,
                       issue_date,
                       place_code,
                       work_number,
                       personal_number,
                       login,
                       password,
                       role_name
                FROM employee
                         JOIN public.department d on d.department_id = employee.department_id
                         JOIN public.communication_salon cs on cs.salon_id = employee.salon_id
                         JOIN public.employee_contact ec on ec.contact_id = employee.contact_id
                         JOIN public.employee_passport ep on employee.passport_id = ep.passport_id
                         JOIN public.post p on employee.post_id = p.post_id
                         JOIN public.role r on employee.role_id = r.role_id;
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

    public void insertIntoEmployee(Employee employee) {
        String sql = """
                INSERT INTO employee (department_id, salon_id, first_name, last_name, post_id, passport_id, contact_id, login, password, role_id)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
                """;
        try (var connection = ConnectionManager.get();
        var preparedStatement = connection.prepareStatement(sql)) {
            var tempDepartmentId = departmentDao.getDepartmentIdByDepartmentName(employee.getDepartment().getDepartmentName());
            var tempSalonId = communicationSalonDao.getSalonIdByAddress(employee.getSalon().getAddress());
            var tempPostId = postDao.getPostIdByPostName(employee.getPost().getPostName());
            var tempPassportId = employeePassportDao.insertIntoPassportAndReturnId(employee.getPassport().getSeries(), employee.getPassport().getNumber(), employee.getPassport().getBirthday(), employee.getPassport().getIssueDate(), employee.getPassport().getPlaceCode());
            var tempContactId = employeeContactDao.insertIntoContactAndReturnId(employee.getContact().getWorkNumber(), employee.getContact().getPersonalNumber());
            var tempRoleId = roleDao.getRoleIdByRoleName(employee.getRole().getRoleName());

            preparedStatement.setInt(1, tempDepartmentId);
            preparedStatement.setInt(2, tempSalonId);
            preparedStatement.setString(3, employee.getFirstName());
            preparedStatement.setString(4, employee.getLastName());
            preparedStatement.setInt(5, tempPostId);
            preparedStatement.setInt(6, tempPassportId);
            preparedStatement.setInt(7, tempContactId);
            preparedStatement.setString(8, employee.getLogin());
            preparedStatement.setString(9, employee.getPassword());
            preparedStatement.setInt(10, tempRoleId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteClient(String login) {
        String sql = """
                DELETE
                FROM employee
                WHERE login = ?;
                """;
        try (var connection = ConnectionManager.get();
        var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, login);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Employee buildEmployee(ResultSet resultSet) throws SQLException {
        return Employee.builder()
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

    public static EmployeeDao getInstance() {
        return INSTANCE;
    }

}
