package org.lyuban.technesis.DataBaceConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DB {
    private final String HOST = "localhost";
    private final String PORT = "5432";
    private final String DB_NAME = "TechnesisDB";
    private final String USER = "user";
    private final String PASS = "user";
    private Connection dbConn = null;
    private Connection getDbConnection() throws ClassNotFoundException, SQLException { //метод подключения к базе данных
        String connStr = "jdbc:postgresql://" + HOST + ":" + PORT + "/" + DB_NAME;
        Class.forName("org.postgresql.Driver");//драйвер для подключения к базе данных

        dbConn = DriverManager.getConnection(connStr, USER, PASS); //в переменную помещаем подключение
        return dbConn;
    }

    private Connection getCon() throws SQLException {
        String url = "jdbc:postgresql://" + HOST + "/" + DB_NAME;
        Properties props = new Properties();
        props.setProperty("user", USER);
        props.setProperty("password", PASS);
        props.setProperty("ssl", "true");
        Connection conn = DriverManager.getConnection(url, props);
return conn;
//        String url = "jdbc:postgresql://localhost/test?user=fred&password=secret&ssl=true";
//        Connection conn = DriverManager.getConnection(url);
    }

    public void isConnected() throws SQLException, ClassNotFoundException {
        dbConn = getDbConnection();
        System.out.println(dbConn.isValid(1000));//подключились ли мы в базе данных в течение 1 сек
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        DB db = new DB();
//        db.getDbConnection();
        db.getCon();
        db.isConnected();
    }
}
