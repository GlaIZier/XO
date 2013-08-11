package com.model;

import java.io.*;
import java.net.*;

public class Client {

    private static final int PORT = 5555;

    private Socket socket;

    public Client(String ip) {
        try {
            socket = new Socket(InetAddress.getByName(ip), PORT);
        }
        catch (UnknownHostException e)  {
            System.out.println("Wrong ip address. " + e);
        }
        catch (IOException e) {
            System.out.println("IO Error: " + e);
        }
    }


    public int getToInputStream() {
        try {
            return socket.getInputStream().read();
        }
        catch (IOException e) {
            System.out.println("Client IO error! " + e);
            return -1;
        }
    }

    public void closeSocket() {
        try{
            socket.close();
            System.out.println("Client session ended!");
        }
        catch (IOException e) {
            System.out.println("Client IO error! " + e);
        }
    }

    public void writeToOutputStream(int out) {
        try {
            socket.getOutputStream().write(out);
        }
        catch (UnknownHostException e) {
            System.out.println("Error during sending message! " + e);
        }
        catch (IOException e) {
            System.out.println("Server IO error! " + e);
        }
    }

    public Socket getSocket() {
        return socket;
    }
}
