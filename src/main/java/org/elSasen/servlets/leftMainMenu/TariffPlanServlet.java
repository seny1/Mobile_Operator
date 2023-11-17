package org.elSasen.servlets.leftMainMenu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.elSasen.service.StatusService;
import org.elSasen.service.TariffPlanService;

import java.io.IOException;

@WebServlet("/tariffPlanTable")
public class TariffPlanServlet extends HttpServlet {
    private final TariffPlanService tariffPlanService = TariffPlanService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("tariffPlanTable", tariffPlanService.getTariffPlanTable(req.getParameter("orderBy")));
        req.setAttribute("columnNames", tariffPlanService.getColumnsOfTariffPlan());
        req.getRequestDispatcher("leftMainMenu/TariffPlanJSP.jsp").forward(req, resp);
    }
}
