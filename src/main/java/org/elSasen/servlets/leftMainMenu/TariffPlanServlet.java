package org.elSasen.servlets.leftMainMenu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.elSasen.dto.insert.TariffPlanDtoInsert;
import org.elSasen.exception.ValidationException;
import org.elSasen.service.StatusService;
import org.elSasen.service.TariffPlanService;

import java.io.IOException;
import java.util.HashMap;

@WebServlet("/tariffPlanTable")
public class TariffPlanServlet extends HttpServlet {
    private final TariffPlanService tariffPlanService = TariffPlanService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("tariffPlanTable", tariffPlanService.getTariffPlanTable(req.getParameter("orderBy")));
        req.setAttribute("columnNames", tariffPlanService.getColumnsOfTariffPlan());
        req.getSession().setAttribute("names", tariffPlanService.getPlans());
        req.getRequestDispatcher("leftMainMenu/TariffPlanJSP.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
           try {
               if (req.getParameter("filter") == null && req.getParameter("delete") == null) {
                   var tariffPlanDtoInsert = TariffPlanDtoInsert.builder()
                       .planName(req.getParameter("planName"))
                       .callMinutes(Integer.parseInt(req.getParameter("callMinutes")))
                       .internetGb(Integer.parseInt(req.getParameter("internetGb")))
                       .smsNumber(Integer.parseInt(req.getParameter("smsNumber")))
                       .price(Integer.parseInt(req.getParameter("price")))
                       .build();
                   try {
                       tariffPlanService.insertIntoTariffPlan(tariffPlanDtoInsert);
                       resp.sendRedirect("/tariffPlanTable");
                   } catch (ValidationException exception) {
                       req.setAttribute("errors", exception.getErrors());
                       doGet(req, resp);
                   }
               } else if (req.getParameter("filter") != null) {
                   var filterMap = new HashMap<String, String>();
                   filterMap.put("planName", req.getParameter("planNameFilter"));
                   filterMap.put("callMinutesUp", req.getParameter("callMinutesUpFilter"));
                   filterMap.put("callMinutesDown", req.getParameter("callMinutesDownFilter"));
                   filterMap.put("internetGbUp", req.getParameter("internetGbUpFilter"));
                   filterMap.put("internetGbDown", req.getParameter("internetGbDownFilter"));
                   filterMap.put("smsNumberUp", req.getParameter("smsNumberUpFilter"));
                   filterMap.put("smsNumberDown", req.getParameter("smsNumberDownFilter"));
                   filterMap.put("priceUp", req.getParameter("priceUpFilter"));
                   filterMap.put("priceDown", req.getParameter("priceDownFilter"));
                   req.setAttribute("columnNames", tariffPlanService.getColumnsOfTariffPlan());
                   req.setAttribute("tariffPlanTable", tariffPlanService.getFilterTariffTable(req.getParameter("orderBy"), filterMap));
                   req.getRequestDispatcher("leftMainMenu/TariffPlanJSP.jsp").forward(req, resp);
               } else if (req.getParameter("delete") != null){
                   tariffPlanService.deleteTariff(req.getParameter("nameDelete"));
                   resp.sendRedirect("/tariffPlanTable");
               }
           } catch (ValidationException exception) {
               req.setAttribute("errors", exception.getErrors());
               doGet(req, resp);
           }
    }
}
