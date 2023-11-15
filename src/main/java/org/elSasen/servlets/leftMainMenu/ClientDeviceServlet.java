package org.elSasen.servlets.leftMainMenu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.elSasen.service.ClientDeviceService;

import java.io.IOException;

@WebServlet("/clientDeviceTable")
public class ClientDeviceServlet extends HttpServlet {

    private final ClientDeviceService clientDeviceService = ClientDeviceService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("clientDeviceTable", clientDeviceService.getClientDeviceTable());
        req.setAttribute("columnNames", clientDeviceService.getColumnsOfClientDevice());
        req.getRequestDispatcher("leftMainMenu/ClientDeviceJSP.jsp").forward(req, resp);
    }
}
