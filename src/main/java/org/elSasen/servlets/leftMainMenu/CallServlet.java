package org.elSasen.servlets.leftMainMenu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.elSasen.service.CallService;
import org.elSasen.service.ClientService;

import java.io.IOException;

@WebServlet("/callTable")
public class CallServlet extends HttpServlet {

    private final CallService callService = CallService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("callTable", callService.getCallTable());
        req.setAttribute("columnNames", callService.getColumnsOfCall());
        req.getRequestDispatcher("leftMainMenu/CallJSP.jsp").forward(req, resp);
    }
}
