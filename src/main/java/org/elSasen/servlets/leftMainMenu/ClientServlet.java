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

@WebServlet("/clientTable")
public class ClientServlet extends HttpServlet {

    private final ClientService clientService = ClientService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("clientTable", clientService.getClientTable(req.getParameter("orderBy")));
        req.setAttribute("columnNames", clientService.getColumnsOfClient());
        req.setAttribute("goodColumnNames", clientService.getGoodColumnsOfClient());
        req.getRequestDispatcher("leftMainMenu/ClientJSP.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var clientDtoInsert = ClientDtoInsert.builder()
                .firstName(req.getParameter("firstName"))
                .lastName(req.getParameter("lastName"))
                .series(req.getParameter("series"))
                .numberOfPassport(req.getParameter("numberOfPassport"))
                .birthday(LocalDate.parse(req.getParameter("birthday")))
                .numberOfContact(req.getParameter("numberOfContact"))
                .type(req.getParameter("type"))
                .build();
        try {
            clientService.insertIntoClientTable(clientDtoInsert);
            resp.sendRedirect("/clientTable");
        } catch (ValidationException exception) {
            req.setAttribute("errors", exception.getErrors());
            doGet(req, resp);
        }
    }
}
