package org.elSasen.servlets.leftMainMenu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.elSasen.dto.insert.ContractDtoInsert;
import org.elSasen.exception.ValidationException;
import org.elSasen.service.ContractService;
import org.elSasen.service.TariffPlanService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;

@WebServlet("/contractTable")
public class ContractServlet extends HttpServlet {

    private final ContractService contractService = ContractService.getInstance();
    private final TariffPlanService tariffPlanService = TariffPlanService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("contractTable", contractService.getContractTable(req.getParameter("orderBy")));
        req.setAttribute("columnNames", contractService.getColumnsOfContract());
        req.getSession().setAttribute("planNames", tariffPlanService.getPlans());
        req.setAttribute("goodColumnNames", contractService.getGoodColumnsOfContract());
        req.getRequestDispatcher("/leftMainMenu/ContractJSP.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (req.getParameter("filter") == null) {
                var contractDtoInsert = ContractDtoInsert.builder()
                        .clientId(Integer.parseInt(req.getParameter("clientID")))
                        .date(LocalDate.parse(req.getParameter("date")))
                        .planName(req.getParameter("tariffName"))
                        .build();
                contractService.insertIntoContract(contractDtoInsert);
                resp.sendRedirect("/contractTable");
            } else if (req.getParameter("filter") != null) {
                var filterMap = new HashMap<String, String>();
                filterMap.put("planName", req.getParameter("planNameFilter"));
                filterMap.put("dateUp", req.getParameter("dateUpFilter"));
                filterMap.put("dateDown", req.getParameter("dateDownFilter"));
                filterMap.put("number", req.getParameter("numberFilter"));
                filterMap.put("firstName", req.getParameter("firstNameFilter"));
                filterMap.put("lastName", req.getParameter("lastNameFilter"));
                req.setAttribute("goodColumnNames", contractService.getGoodColumnsOfContract());
                req.setAttribute("contractTable", contractService.getFilterContractTable(req.getParameter("orderBy"), filterMap));
                req.getRequestDispatcher("leftMainMenu/ContractJSP.jsp").forward(req, resp);
            }
        } catch (ValidationException exception) {
            req.setAttribute("errors", exception.getErrors());
            doGet(req, resp);
        }
    }
}
