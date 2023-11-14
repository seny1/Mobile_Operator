package org.elSasen.servlets.leftMainMenu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.elSasen.service.ClientService;

import java.io.IOException;

@WebServlet("/clientTable")
public class ClientServlet extends HttpServlet {

    private final ClientService clientService = ClientService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("clientTable", clientService.getClientTable());
        req.setAttribute("columnNames", clientService.getColumnsOfClient());
        req.getRequestDispatcher("leftMainMenu/ClientJSP.jsp").forward(req, resp);
    }
}
