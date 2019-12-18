package ru.itpark.service;

import ru.itpark.domain.SearchQuery;
import ru.itpark.util.JDBCTemplate;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class SearchService {
    private final DataSource ds;

    public SearchService() throws NamingException, SQLException {
        var context = new InitialContext();
        ds = (DataSource)context.lookup("java:/comp/env/jdbc/db");
        try (var conn = ds.getConnection()){
            try (var stmt = conn.createStatement()){
                stmt.execute("CREATE TABLE IF NOT EXISTS searches (id TEXT PRIMARY KEY , name TEXT NOT NULL);");
            }
        }
    }

    public List<SearchQuery> getAll(){
        List<SearchQuery> searchQueries = null;
        try {
            searchQueries = JDBCTemplate.executeQuery(
                    ds,
                    "SELECT id, name FROM searches;",
                    rs -> new SearchQuery(
                            rs.getString("id"),
                            rs.getString("name")
                    ));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return searchQueries;
    }

    public void create(String name) throws SQLException {
        JDBCTemplate.create(ds, name);
    }
}
