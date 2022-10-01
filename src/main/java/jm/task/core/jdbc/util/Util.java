package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

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
        private static SessionFactory sessionFactory;

        public static SessionFactory getSessionFactory() {
                if (sessionFactory == null) {
                        try {
                                Configuration configuration = new Configuration();

                                Properties settings = new Properties();
                                settings.put(Environment.URL, url);
                                settings.put(Environment.USER, name);
                                settings.put(Environment.PASS, pass);
                                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
                                settings.put(Environment.SHOW_SQL, "true");
                                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                                settings.put(Environment.HBM2DDL_AUTO, "create");
                                configuration.setProperties(settings);

                                configuration.addAnnotatedClass(User.class);

                                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                                        .applySettings(configuration.getProperties()).build();

                                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                        } catch (Exception e) {
                                e.printStackTrace();
                        }
                }
                return sessionFactory;

        }
        public static void closeSession(){
                getSessionFactory().close();
        }
}
