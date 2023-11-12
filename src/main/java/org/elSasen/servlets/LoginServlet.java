package org.elSasen.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.elSasen.dto.EmployeeDto;
import org.elSasen.service.EmployeeService;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final EmployeeService employeeService = EmployeeService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("LoginJSP.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        employeeService.findUser(req.getParameter("login"), req.getParameter("password"))
                .ifPresentOrElse(
                    employeeDto -> successLogin(employeeDto, req, resp),
                        () -> failLogin(req, resp)
                );
    }

    private void successLogin(EmployeeDto employeeDto, HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().setAttribute("user", employeeDto);
        try {
            resp.sendRedirect("/success");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void failLogin(HttpServletRequest req, HttpServletResponse resp) {
        try {
            resp.sendRedirect("/login?error&login=" + req.getParameter("login"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
