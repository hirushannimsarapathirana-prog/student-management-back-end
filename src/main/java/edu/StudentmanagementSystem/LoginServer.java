package edu.StudentmanagementSystem;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class LoginServer implements HttpHandler {


    @Override
    public void handle(HttpExchange exchange) throws IOException {


        System.out.println("Login Request Received");


        exchange.getResponseHeaders()
                .add("Access-Control-Allow-Origin", "*");

        exchange.getResponseHeaders()
                .add("Access-Control-Allow-Headers", "Content-Type");

        exchange.getResponseHeaders()
                .add("Access-Control-Allow-Methods", "POST, OPTIONS");



        // CORS preflight
        if(exchange.getRequestMethod()
                .equalsIgnoreCase("OPTIONS")){


            exchange.sendResponseHeaders(204,-1);

            return;

        }



        if(exchange.getRequestMethod()
                .equalsIgnoreCase("POST")){


            BufferedReader reader =
                    new BufferedReader(
                            new InputStreamReader(
                                    exchange.getRequestBody(),
                                    StandardCharsets.UTF_8));


            String json = reader.readLine();


            System.out.println(json);

            Gson gson = new Gson();
            UserDAO userDAO = new UserDAO();

            User user = gson.fromJson(json, User.class);


            boolean result = userDAO.login(user);


            String response;


            if(result){

                response = "Login Success";

            }
            else{

                response = "Invalid Login";

            }

            exchange.sendResponseHeaders(
                    200,
                    response.getBytes().length);



            OutputStream os =
                    exchange.getResponseBody();


            os.write(response.getBytes());


            os.close();


        }


    }

}