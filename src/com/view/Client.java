package com.view;

/**
 * Created with IntelliJ IDEA.
 * User: Кирилл
 * Date: 05.08.13
 * Time: 17:24
 * To change this template use File | Settings | File Templates.
 */
import java.io.*;
import java.net.*;

public class Client {

    private static final int PORT = 5555;

    private Socket socket;

    public Client(InetAddress ip) {
        try {
            socket = new Socket(ip, PORT);
        }
        catch (UnknownHostException e)  {
            System.out.println("Wrong ip address. " + e);
        }
        catch (IOException e) {
            System.out.println("IO Error: " + e);
        }
    }

    public void closeSocket() {
        try {
            socket.close();
        }
        catch (IOException e) {
            System.out.println("Error during socket closing! " + e);
        }
    }

    public Socket getSocket() {
        return socket;
    }
}
