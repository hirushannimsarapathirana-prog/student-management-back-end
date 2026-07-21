package edu.StudentmanagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    public boolean login(User user)
    {
        try
        {
            Connection connection = DBConnection.getConnection();
            String sql = "SELECT * FROM users WHERE username =? AND password=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;

    }

}
