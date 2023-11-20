package org.elSasen.servlets.leftMainMenu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.elSasen.service.*;

import java.io.IOException;
import java.time.LocalDate;

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
        req.getRequestDispatcher("leftMainMenu/EmployeeJSP.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        employeeService.insertIntoEmployee(
                req.getParameter("department"),
                req.getParameter("salon"),
                req.getParameter("firstName"),
                req.getParameter("lastName"),
                req.getParameter("post"),
                req.getParameter("series"),
                req.getParameter("numberOfPassport"),
                LocalDate.parse(req.getParameter("birthday")),
                LocalDate.parse(req.getParameter("issueDate")),
                req.getParameter("placeCode"),
                req.getParameter("workNumber"),
                req.getParameter("personalNumber"),
                req.getParameter("login"),
                req.getParameter("password"),
                req.getParameter("role")
        );
        resp.sendRedirect("/employeeTable");
    }
}
