package ru.itpark.service;

import ru.itpark.domain.Auto;
import ru.itpark.util.JDBCTemplate;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class AutoService {
    private final DataSource ds;

    public AutoService() throws NamingException, SQLException {
        var context = new InitialContext();
        ds = (DataSource)context.lookup("java:/comp/env/jdbc/db");
        try (var conn = ds.getConnection()){
            try (var stmt = conn.createStatement()){
                stmt.execute("CREATE TABLE IF NOT EXISTS autos (id TEXT PRIMARY KEY , name TEXT NOT NULL, description TEXT NOT NULL, image TEXT);");
            }
        }
    }

//    public List<Auto> getAll() throws SQLException {
//        try (var conn = ds.getConnection()){
//            try (var stmt = conn.createStatement()){
//                try (var rs = stmt.executeQuery("SELECT id, name, description, image FROM autos;")){
//                    var list = new ArrayList<Auto>();
//                    while (rs.next()) {
//                        list.add(new Auto(
//                                rs.getString("id"),
//                                rs.getString("name"),
//                                rs.getString("description"),
//                                rs.getString("image")
//                        ));
//                    }
//                    return list;
//                }
//            }
//        }
//    }

    public List<Auto> getAll(){
        List<Auto> autos = null;
        try {
            autos = JDBCTemplate.executeQuery(
                    ds,
                    "SELECT id, name, description, image FROM autos;",
                    rs -> new Auto(
                            rs.getString("id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getString("image")
                    ));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return autos;
    }

//    public void create(String name, String description, String image) throws SQLException {
//        try (var conn = ds.getConnection()){
//            try (var stmt = conn.prepareStatement("INSERT INTO autos (id, name, description, image) VALUES (?, ?, ?, ?);")){
//                stmt.setString(1, UUID.randomUUID().toString());
//                stmt.setString(2, name);
//                stmt.setString(3, description);
//                stmt.setString(4, image);
//                stmt.execute();
//            }
//        }
//    }

    public void create(String name, String description, String image) throws SQLException {
        JDBCTemplate.create(ds, name, description, image);
    }
}
