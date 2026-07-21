package edu.StudentmanagementSystem;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.Buffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class StudentServer implements HttpHandler {

    StudentDAO studentDAO = new StudentDAO();
    Gson gson = new Gson();
    

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
        

        if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS"))
        {
            exchange.sendResponseHeaders(204,-1);
            return;
        }
         String method = exchange.getRequestMethod();
        try{

            if (method.equalsIgnoreCase("POST")) {
                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()))) {

                    StringBuilder jsonBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        jsonBuilder.append(line);
                    }

                    Student student = gson.fromJson(jsonBuilder.toString(), Student.class);
                    boolean saved = studentDAO.addStudent(student);

                    String response = saved ? "Student Saved Successfully" : "Failed to Save";
                    int statusCode = saved ? 200 : 500;

                    exchange.sendResponseHeaders(statusCode, response.getBytes().length);

                    try (OutputStream outputStream = exchange.getResponseBody()) {
                        outputStream.write(response.getBytes());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    String errorResponse = "Internal Server Error";
                    exchange.sendResponseHeaders(500, errorResponse.getBytes().length);
                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(errorResponse.getBytes());
                    }
                }
            }





            else if (method.equalsIgnoreCase("GET") && exchange.getRequestURI().getQuery() != null)
            {
                String query = exchange.getRequestURI().getQuery();
                String[] data = query.split("=");
                int id = Integer.parseInt(data[1]);
                Student student = studentDAO.getStudentById(id);
                String response = gson.toJson(student);
                exchange.getResponseHeaders().add("Content-Type","application/json");
                exchange.sendResponseHeaders(200,response.getBytes().length);
                OutputStream outputStream = exchange.getResponseBody();
                outputStream.write(response.getBytes());
                outputStream.close();

            }


            else if (method.equalsIgnoreCase("PUT"))
            {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exchange.getRequestBody(),StandardCharsets.UTF_8));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null)
                {
                    stringBuilder.append(line);
                }
                String reader = stringBuilder.toString();
                Student student = gson.fromJson(reader,Student.class);
                boolean response = studentDAO.updateStudent(student);

                 String result = response? "Student Updated Successfully" :"Student Update Failed";
                 exchange.sendResponseHeaders(200,result.getBytes().length);
                 OutputStream outputStream = exchange.getResponseBody();
                 outputStream.write(result.getBytes());
                 outputStream.close();
            }
            else if (method.equalsIgnoreCase("DELETE")&& (exchange.getRequestURI().getQuery() != null))
            {
                String query = exchange.getRequestURI().getQuery();
                String [] data = query.split("=");
                int id = Integer.parseInt(data[1]);
                boolean result = studentDAO.deleteStudent(id);
                String response = result ? "Student Deleted Successfully" : "Student Delete Failed";
                exchange.sendResponseHeaders(200,response.getBytes().length);
                OutputStream outputStream = exchange.getResponseBody();
                outputStream.write(response.getBytes());
                outputStream.close();


            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }





    }
}
