package util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnUtil {

    private static final String fileName = "db.properties";

    public static Connection getDbConnection() {
        Connection con = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String connString = DBPropertyUtil.getConnectionString(fileName);
            System.out.println("Connection String: " + connString);

            con = DriverManager.getConnection(connString);
            System.out.println("DB Connection Established");

        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found");
            e.printStackTrace();

        } catch (IOException e) {
            System.out.println("Failed to read DB properties file");
            e.printStackTrace();

        } catch (SQLException e) {
            System.out.println("Error while connecting to the database");
            e.printStackTrace();
        }

        return con;
    }
}