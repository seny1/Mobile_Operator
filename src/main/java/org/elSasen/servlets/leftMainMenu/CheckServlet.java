package org.elSasen.servlets.leftMainMenu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.elSasen.service.CheckService;

import java.io.IOException;

@WebServlet("/checkTable")
public class CheckServlet extends HttpServlet {

    private final CheckService checkService = CheckService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("checkTable", checkService.getCheckTable());
        req.setAttribute("columnNames", checkService.getColumnsOfCheck());
        req.getRequestDispatcher("leftMainMenu/CheckJSP.jsp").forward(req, resp);
    }
}
