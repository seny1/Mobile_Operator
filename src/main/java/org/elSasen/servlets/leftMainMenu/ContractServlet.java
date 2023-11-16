package org.elSasen.servlets.leftMainMenu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.elSasen.service.ContractService;

import java.io.IOException;

@WebServlet("/contractTable")
public class ContractServlet extends HttpServlet {

    private final ContractService contractService = ContractService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("contractTable", contractService.getContractTable());
        req.setAttribute("columnNames", contractService.getColumnsOfContract());
        req.getRequestDispatcher("/leftMainMenu/ContractJSP.jsp").forward(req, resp);
    }
}
