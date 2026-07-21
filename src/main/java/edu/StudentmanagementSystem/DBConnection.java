package edu.StudentmanagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;


public class DBConnection {

    private static String url ="jdbc:mysql://127.0.0.1:3306/studentdb";
    private static String user ="root";
    private static String pass="admin";


    public static Connection getConnection()
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url,user,pass);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }


}


