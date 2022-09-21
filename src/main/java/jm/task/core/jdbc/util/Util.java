package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
        static private final String url = "jdbc:mysql://localhost:3306/mydbtest";
        static private final String name = "root";
        static private final String pass = "12enigmA123!";
        private static Connection connection;

        public static Connection getConnection(){
                try {
                        connection = DriverManager.getConnection(url, name,pass);
                        System.out.println("Соединение установленно");
                        return connection;
                } catch (SQLException e) {
                        throw new RuntimeException(e);
                }

        }
        public static Connection closeConnection(){
                try {
                        getConnection().close();
                        System.out.println("Соединение закрыто");

                } catch (SQLException e) {
                        throw new RuntimeException(e);
                }
                return connection;
        }
}
