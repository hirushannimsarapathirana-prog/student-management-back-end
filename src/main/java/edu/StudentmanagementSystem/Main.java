package edu.StudentmanagementSystem;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;

public class Main {

    public static void main(String[] args) {

        try {

            System.out.println("Main Started");

            HttpServer server = HttpServer.create(
                    new InetSocketAddress(8080),
                    0
            );

            System.out.println("Port Created");

            server.createContext("/login", new LoginServer());
            server.createContext("/student", new StudentServer());

            System.out.println("Routes Created");

            server.start();

            System.out.println("Server Started on port 8080");

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
}