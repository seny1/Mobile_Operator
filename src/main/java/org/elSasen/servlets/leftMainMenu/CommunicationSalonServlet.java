package org.elSasen.servlets.leftMainMenu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.elSasen.service.CommunicationSalonService;

import java.io.IOException;

@WebServlet("/communicationSalonTable")
public class CommunicationSalonServlet extends HttpServlet {

    private final CommunicationSalonService communicationSalonService = CommunicationSalonService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("communicationSalonTable", communicationSalonService.getClientCommunicationSalonTable());
        req.setAttribute("columnNames", communicationSalonService.getColumnsOfCommunicationSalon());
        req.getRequestDispatcher("leftMainMenu/CommunicationSalonJSP.jsp").forward(req, resp);
    }
}
