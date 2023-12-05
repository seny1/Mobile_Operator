package org.elSasen.servlets.leftMainMenu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.elSasen.dto.insert.EmployeeDtoInsert;
import org.elSasen.exception.ValidationException;
import org.elSasen.service.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;

@WebServlet("/employeeTable")
public class EmployeeServlet extends HttpServlet {

    private final EmployeeService employeeService = EmployeeService.getInstance();
    private final DepartmentService departmentService = DepartmentService.getInstance();
    private final CommunicationSalonService communicationSalonService = CommunicationSalonService.getInstance();
    private final PostService postService = PostService.getInstance();
    private final RoleService roleService = RoleService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("employeeTable", employeeService.getEmployeeTable(req.getParameter("orderBy")));
        req.setAttribute("columnNames", employeeService.getColumnsOfEmployee());
        req.getSession().setAttribute("departments", departmentService.getDepartments());
        req.getSession().setAttribute("salons", communicationSalonService.getSalons());
        req.getSession().setAttribute("posts", postService.getPosts());
        req.getSession().setAttribute("roles", roleService.getRoles());
        req.setAttribute("goodColumnNames", employeeService.getGoodColumnsOfEmployee());
        req.getRequestDispatcher("leftMainMenu/EmployeeJSP.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (req.getParameter("filter") == null && req.getParameter("delete") == null) {
                var employeeDtoinsert = EmployeeDtoInsert.builder()
                        .departmentName(req.getParameter("department"))
                        .salonAddress(req.getParameter("salon"))
                        .firstName(req.getParameter("firstName"))
                        .lastName(req.getParameter("lastName"))
                        .postName(req.getParameter("post"))
                        .series(req.getParameter("series"))
                        .numberOfPassport(req.getParameter("numberOfPassport"))
                        .birthday(LocalDate.parse(req.getParameter("birthday")))
                        .issueDate(LocalDate.parse(req.getParameter("issueDate")))
                        .placeCode(req.getParameter("placeCode"))
                        .workNumber(req.getParameter("workNumber"))
                        .personalNumber(req.getParameter("personalNumber"))
                        .login(req.getParameter("login"))
                        .password(req.getParameter("password"))
                        .roleName(req.getParameter("role"))
                        .build();
                employeeService.insertIntoEmployee(employeeDtoinsert);
                resp.sendRedirect("/employeeTable");
            } else if (req.getParameter("filter") != null) {
                var filterMap = new HashMap<String, String>();
                filterMap.put("firstName", req.getParameter("firstNameFilter"));
                filterMap.put("lastName", req.getParameter("lastNameFilter"));
                filterMap.put("department", req.getParameter("departmentFilter"));
                filterMap.put("address", req.getParameter("addressFilter"));
                filterMap.put("post", req.getParameter("postFilter"));
                filterMap.put("series", req.getParameter("seriesFilter"));
                filterMap.put("numberOfPassport", req.getParameter("numberOfPassportFilter"));
                filterMap.put("birthdayUp", req.getParameter("birthdayUpFilter"));
                filterMap.put("birthdayDown", req.getParameter("birthdayDownFilter"));
                req.setAttribute("goodColumnNames", employeeService.getGoodColumnsOfEmployee());
                req.setAttribute("employeeTable", employeeService.getFilterEmployeeTable(req.getParameter("orderBy"), filterMap));
                req.getRequestDispatcher("leftMainMenu/EmployeeJSP.jsp").forward(req, resp);
            } else if (req.getParameter("delete") != null) {
                employeeService.deleteClient(req.getParameter("loginDelete"));
                resp.sendRedirect("/employeeTable?good=good");
            }
        } catch (ValidationException exception) {
            req.setAttribute("errors", exception.getErrors());
            doGet(req, resp);
        }
    }
}
