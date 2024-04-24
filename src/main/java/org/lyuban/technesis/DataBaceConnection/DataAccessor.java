package org.lyuban.technesis.DataBaceConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataAccessor {
    private static DataAccessor da;
    static {
        try {
            da = new DataAccessor("jdbc:postgresql://localhost:5432/TechnesisDB", "user", "user");
        } catch (SQLException e) {
            throw new RuntimeException(e + "   Подключение к БД не выполнено");
        }
    }

    private static Connection connection;
    public static DataAccessor getDataAccessor(){
        return da;
    }

    private DataAccessor(String DbUrl, String user, String password) throws SQLException {
        connection = DriverManager.getConnection(DbUrl, user, password);
    }

    public static void isConnected() throws SQLException, ClassNotFoundException {
        Connection dbConn = connection;
        System.out.println(dbConn.isValid(5000));//подключились ли мы в базе данных в течение 1 сек
    }
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        isConnected();
    }
}
