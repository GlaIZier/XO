package com.view;

/**
 * Created with IntelliJ IDEA.
 * User: Кирилл
 * Date: 05.08.13
 * Time: 16:54
 * To change this template use File | Settings | File Templates.
 */
import java.io.*;
import java.net.*;

public class Server {

    private static final int PORT = 5555;

    private ServerSocket serverSocket;

    private Socket client;

    private int inCoordI;

    private int inCoordJ;

    public Server() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Waiting for client... ");
            client = serverSocket.accept();
            System.out.println("Connected.");
        }
        catch (IOException e) {
            System.out.println("Error during opening socket: " + e );
        }
    }

    public void closeSeverSocket() {
        try {
            client.close();
            serverSocket.close();
        }
        catch (IOException e) {
            System.out.println("Error during socket closing! " + e);
        }
    }

    public Socket getClient() {
        return client;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }
}
