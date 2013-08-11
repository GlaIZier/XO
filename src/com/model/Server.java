package com.model;


import java.io.*;
import java.net.*;

public class Server {

    private static final int PORT = 5555;

    private static final int TIME_OUT = 15000; // 15 s wait for client connection

    private ServerSocket serverSocket;

    private Socket client;

    public Server() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Waiting for client... ");
            serverSocket.setSoTimeout(TIME_OUT);
            client = serverSocket.accept();
            System.out.println("Connected.");
        }
        catch (IOException e) {
            System.out.println("Error during opening socket: " + e );
        }
    }

    public void writeToOutputStream(int out) {
        try {
            client.getOutputStream().write(out);
        }
        catch (UnknownHostException e) {
            System.out.println("Error during sending message! " + e);
        }
        catch (IOException e) {
            System.out.println("Server IO error! " + e);
        }
    }

    public void closeSocket() {
       try{
           client.close();
           serverSocket.close();
           System.out.println("Server session ended!");
       }
       catch (IOException e) {
           System.out.println("Server IO error! " + e);
       }
    }

    public int getToInputStream() {
        try {
            return client.getInputStream().read();
        }
        catch (IOException e) {
            System.out.println("Server IO error! " + e);
            return -1;
        }
    }

    public Socket getClient() {
        return client;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }
}
