package org.elSasen.servlets.leftMainMenu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.elSasen.service.ClientContactService;

import java.io.IOException;

@WebServlet("/clientContactTable")
public class ClientContactServlet extends HttpServlet {

    private final ClientContactService clientContactService = ClientContactService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("clientContactTable", clientContactService.getClientContactTable());
        req.setAttribute("columnNames", clientContactService.getColumnsOfClientContact());
        req.getRequestDispatcher("/leftMainMenu/ClientContactJSP.jsp").forward(req, resp);
    }
}
