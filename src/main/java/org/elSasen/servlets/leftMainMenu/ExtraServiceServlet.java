package org.elSasen.servlets.leftMainMenu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.elSasen.service.ExtraServiceService;

import java.io.IOException;

@WebServlet("/extraServiceTable")
public class ExtraServiceServlet extends HttpServlet {
    private final ExtraServiceService extraServiceService = ExtraServiceService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("extraServiceTable", extraServiceService.getExtraServiceTable());
        req.setAttribute("columnNames", extraServiceService.getColumnsOfExtraService());
        req.getRequestDispatcher("leftMainMenu/ExtraServiceJSP.jsp").forward(req, resp);
    }
}