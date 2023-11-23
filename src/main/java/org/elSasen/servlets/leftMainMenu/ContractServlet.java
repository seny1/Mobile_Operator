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

@WebServlet("/contractTable")
public class ContractServlet extends HttpServlet {

    private final ContractService contractService = ContractService.getInstance();
    private final TariffPlanService tariffPlanService = TariffPlanService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("contractTable", contractService.getContractTable(req.getParameter("orderBy")));
        req.setAttribute("columnNames", contractService.getColumnsOfContract());
        req.setAttribute("goodColumnNames", contractService.getGoodColumnsOfContract());
        req.getRequestDispatcher("/leftMainMenu/ContractJSP.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var contractDtoInsert = ContractDtoInsert.builder()
                .clientId(Integer.parseInt(req.getParameter("clientID")))
                .date(LocalDate.parse(req.getParameter("date")))
                .planName(req.getParameter("tariffName"))
                .build();
        try {
            contractService.insertIntoContract(contractDtoInsert);
            resp.sendRedirect("/contractTable");
        } catch (ValidationException exception) {
            req.setAttribute("errors", exception.getErrors());
            doGet(req, resp);
        }
    }
}
