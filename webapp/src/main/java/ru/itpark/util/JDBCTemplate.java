package ru.itpark.util;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JDBCTemplate {
    private JDBCTemplate() {
    }

    public static <T> List<T> executeQuery(DataSource dataSource, String sql, RowMapper<T> mapper) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)
        ) {
            List<T> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(mapper.map(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new SQLMappingException(e);
        }
    }

    public static void create(DataSource dataSource, String name, String description, String image) {
        try (Connection connection = dataSource.getConnection()){
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO autos (id, name, description, image) VALUES (?, ?, ?, ?);")){
                statement.setString(1, UUID.randomUUID().toString());
                statement.setString(2, name);
                statement.setString(3, description);
                statement.setString(4, image);
                statement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
