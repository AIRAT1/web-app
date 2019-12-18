package ru.itpark.servlet;

import ru.itpark.service.SearchService;
import ru.itpark.service.FileService;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class SearchServlet extends HttpServlet {
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
        req.setAttribute("items", searchService.getAll()); // TODO delete?
        req.getRequestDispatcher("/WEB-INF/search.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        try {
//            var name = req.getParameter("name");
//            var status = req.getParameter("status");
            var part = req.getPart("query"); //fixme

            var image = fileService.writeFile(part);

//            searchService.create(name, status, image);
            resp.sendRedirect(String.join("/", req.getContextPath(), req.getServletPath()));
//        }catch (SQLException e) {
//            e.printStackTrace();
//            throw new ServletException(e);
//        }
    }
}
