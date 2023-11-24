package org.elSasen.servlets.leftMainMenu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.elSasen.dto.insert.ClientDtoInsert;
import org.elSasen.exception.ValidationException;
import org.elSasen.service.ClientService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;

@WebServlet("/clientTable")
public class ClientServlet extends HttpServlet {

    private final ClientService clientService = ClientService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("columnNames", clientService.getColumnsOfClient());
        req.setAttribute("goodColumnNames", clientService.getGoodColumnsOfClient());
        req.setAttribute("clientTable", clientService.getClientTable(req.getParameter("orderBy")));
        req.getRequestDispatcher("leftMainMenu/ClientJSP.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (req.getParameter("filter") == null) {
                var clientDtoInsert = ClientDtoInsert.builder()
                        .firstName(req.getParameter("firstName"))
                        .lastName(req.getParameter("lastName"))
                        .series(req.getParameter("series"))
                        .numberOfPassport(req.getParameter("numberOfPassport"))
                        .birthday(LocalDate.parse(req.getParameter("birthday")))
                        .numberOfContact(req.getParameter("numberOfContact"))
                        .type(req.getParameter("type"))
                        .build();
                clientService.insertIntoClientTable(clientDtoInsert);
                resp.sendRedirect("/clientTable");
            } else if (req.getParameter("filter") != null) {
                var filterMap = new HashMap<String, String>();
                filterMap.put("firstName", req.getParameter("firstNameFilter"));
                filterMap.put("lastName", req.getParameter("lastNameFilter"));
                filterMap.put("series", req.getParameter("seriesFilter"));
                filterMap.put("numberOfPassport", req.getParameter("numberOfPassportFilter"));
                filterMap.put("birthdayUp", req.getParameter("birthdayUpFilter"));
                filterMap.put("birthdayDown", req.getParameter("birthdayDownFilter"));
                filterMap.put("numberOfContact", req.getParameter("numberOfContactFilter"));
                filterMap.put("type", req.getParameter("typeFilter"));
                req.setAttribute("goodColumnNames", clientService.getGoodColumnsOfClient());
                req.setAttribute("clientTable", clientService.getFilterClientTable(req.getParameter("orderBy"), filterMap));
                req.getRequestDispatcher("leftMainMenu/ClientJSP.jsp").forward(req, resp);
            }
        } catch (ValidationException exception) {
            req.setAttribute("errors", exception.getErrors());
            doGet(req, resp);
        }
    }
}
