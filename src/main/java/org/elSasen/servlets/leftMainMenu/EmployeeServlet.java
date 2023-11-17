package org.elSasen.servlets.leftMainMenu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.elSasen.service.EmployeeService;

import java.io.IOException;

@WebServlet("/employeeTable")
public class EmployeeServlet extends HttpServlet {

    private final EmployeeService employeeService = EmployeeService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("employeeTable", employeeService.getEmployeeTable(req.getParameter("orderBy")));
        req.setAttribute("columnNames", employeeService.getColumnsOfEmployee());
        req.getRequestDispatcher("leftMainMenu/EmployeeJSP.jsp").forward(req, resp);
    }
}
