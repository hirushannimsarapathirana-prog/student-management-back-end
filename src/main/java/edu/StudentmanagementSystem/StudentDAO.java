package edu.StudentmanagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentDAO {

    public  boolean addStudent(Student student){

        String sql = "INSERT INTO student(name,email,phone,class_name) VALUES(?,?,?,?)";
        try{
            Connection connection =  DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1,student.getName());
            preparedStatement.setString(2,student.getEmail());
            preparedStatement.setString(3,student.getPhone());
            preparedStatement.setString(4,student.getClass_name());

            return preparedStatement.executeUpdate()>0;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return false;



    }
    public ArrayList<Student>  getStudent()
    {
        ArrayList<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM student";
         try
         {
             Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery();

             while (resultSet.next())
             {
                 Student student = new Student( resultSet.getInt("id"),
                         resultSet.getString("name"),
                         resultSet.getString("email"),
                         resultSet.getString("phone"),
                         resultSet.getString("class_name"));
                 students.add(student);
             }
         }
         catch (Exception e)
         {
             e.printStackTrace();
         }
         return students;

         }
         public Student getStudentById(int id)
         {
             String sql = "SELECT * FROM student WHERE id=?";

             try
             {
                 Connection connection = DBConnection.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql);

                 preparedStatement.setInt(1,id);
                 ResultSet resultSet = preparedStatement.executeQuery();

                 if (resultSet.next())
                 {
                     return new Student(resultSet.getInt("id"),
                     resultSet.getString("name"),
                     resultSet.getString("email"),
                     resultSet.getString("phone"),
                     resultSet.getString("class_name"));

                 }
             }
             catch (Exception e)
             {
                 e.printStackTrace();
             }
             return null;

         }

         public boolean updateStudent(Student student)
         {
             String sql = "UPDATE student SET name=?,email=?,phone=?,class_name=? WHERE id=?";

             try
             {
                 Connection connection = DBConnection.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql);

                 preparedStatement.setString(1,student.getName());
                 preparedStatement.setString(2,student.getEmail());
                 preparedStatement.setString(3,student.getPhone());
                 preparedStatement.setString(4,student.getClass_name());
                 preparedStatement.setInt(5,student.getId());

                 int result = preparedStatement.executeUpdate();

                 if (result>0)
                 {
                     return true;
                 }


             }
             catch (Exception e) {
                 e.printStackTrace();
             }
             return false;

         }

         public  boolean deleteStudent (int id)
         {
             String sql = "DELETE FROM student WHERE id=?";

             try
             {
                 Connection connection = DBConnection.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql);

                 preparedStatement.setInt(1,id);

                 int result = preparedStatement.executeUpdate();

                 if (result>0)
                 {
                     return true;

                 }


             }
             catch (Exception e)
             {
                 e.printStackTrace();
             }
             return false;

         }


    }


