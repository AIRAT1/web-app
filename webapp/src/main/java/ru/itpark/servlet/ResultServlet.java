package ru.itpark.servlet;

import ru.itpark.service.FileService;
import ru.itpark.service.SearchService;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class ResultServlet extends HttpServlet {
    private SearchService searchService;
    private FileService fileService;

    @Override
    public void init() throws ServletException {
        InitialContext context = null;
        try {
            context = new InitialContext();
            searchService = (SearchService) context.lookup("java:/comp/env/bean/search-service");
            fileService = (FileService) context.lookup("java:/comp/env/bean/file-service");
        } catch (NamingException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/result.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            var name = req.getParameter("query");
            System.out.println(name);
            searchService.create(name);
            System.out.println(searchService.getAll());
            req.setAttribute("query", name);
            doGet(req, resp);
        }catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }
}
