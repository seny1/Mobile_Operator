package org.elSasen.servlets.leftMainMenu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.elSasen.service.CallService;
import org.elSasen.service.EmployeePassportService;

import java.io.IOException;
@WebServlet("/employeePassportTable")
public class EmployeePassportServlet extends HttpServlet {
    private final EmployeePassportService employeePassportService = EmployeePassportService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("employeePassportTable", employeePassportService.getEmployeePassportTable(req.getParameter("orderBy")));
        req.setAttribute("columnNames", employeePassportService.getColumnsOfEmployeePassport());
        req.getRequestDispatcher("leftMainMenu/EmployeePassportJSP.jsp").forward(req, resp);
    }
}
