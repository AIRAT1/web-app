package ru.itpark.servlet;

import ru.itpark.domain.SearchQuery;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

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
        List<String> queries = searchService.getAll().stream()
                .map(SearchQuery::getName)
                .collect(Collectors.toList());
        req.setAttribute("userQueries", queries);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/result.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            var name = req.getParameter("query");
            searchService.create(name);
            req.setAttribute("query", name);
            fileService.readAllFiles(name);
            Path path = Paths.get(System.getenv("RESULT_PATH")).resolve(name);
            if (FileService.writeResult) {
                System.out.println(path.toUri() + ".txt");
                req.setAttribute("path", path);
            }
            FileService.writeResult = false;
            doGet(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }
}
