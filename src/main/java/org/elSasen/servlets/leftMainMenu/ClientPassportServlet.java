package org.elSasen.servlets.leftMainMenu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.elSasen.service.ClientPassportService;

import java.io.IOException;

@WebServlet("/clientPassportTable")
public class ClientPassportServlet extends HttpServlet {

    private final ClientPassportService clientPassportService = ClientPassportService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("clientPassportTable", clientPassportService.getClientContactTable());
        req.setAttribute("columnNames", clientPassportService.getColumnsOfClientPassport());
        req.getRequestDispatcher("leftMainMenu/ClientPassportJSP.jsp").forward(req, resp);
    }
}
