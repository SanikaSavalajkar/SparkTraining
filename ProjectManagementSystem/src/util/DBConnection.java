package util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	    private static Connection conn;

	    static {
	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");  
	            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectmanagementsystem", "root", "Sanika@12345");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    public static Connection getConnection() {
	        return conn;
	    }
	}