package com.view;

import com.model.Game;
import com.сontroller.Controller;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created with IntelliJ IDEA.
 * User: Кирилл
 * Date: 01.08.13
 * Time: 16:05
 * To change this template use File | Settings | File Templates.
 */
public class View implements ViewInterface {

    public static final String GREETING = "\n******New Game******\nThis is simple XO play. Please, choose field size > 2 and < 10! Input q to quit.";

    public static final String RULE = "X and O takes turns. You should input column number, press Enter and then input row number.\nPress \"q\" to quit.";

    public static final String QUIT = "q";

    public static final String X = "X";

    public static final String O = "O";

    public final BufferedReader reader;

    private Game game;

    private Controller controller;

    private String inString;

    private int coordI;

    private int coordJ;

    private Server server;

    private Client client;

    public View (Controller controller) {
        this.controller = controller;
        coordI = 0;
        coordJ = 0;
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public void printField() {
        for (int i = 0; i < game.getField().getFieldSize(); i++) {
            printLine(i);
        }
    }

    public void printLine(int i) {
        for (int j = 0; j < game.getField().getFieldSize(); j++) {
            System.out.print("[" + game.getField().getField()[i][j] + "] ");
        }
        System.out.println("");
    }

    public void play() {

        do {
            System.out.println(GREETING);
            if ( !tryInput() ) {
                break;
            }
            int answer = tryParse();
            if (answer != -1) {
                controller.setGame( new Game(answer) );
                game = controller.getGame();
            }
            else {
                controller.setGame(null);
                game = controller.getGame();
                continue;
            }
            printChooseGameMode();
            if ( !tryInput() ) {
                continue;
            }
            answer = tryParse();
            switch (answer) {
                    case 1 :   pvpGameOffline();
                              continue;

                    case 2 :   pvpGameOnline();
                              continue;

                    case 3 :   pvcGame();
                              continue;

                    default:
                              System.out.println("You have inputted wrong number!");
                              controller.setGame(null);
                              game = controller.getGame();
                              continue;

            }

        } while (inString == inString);

    }

    public void pvpGameOffline() {
        System.out.println(RULE);
        printField();
        do {
            int answer = humanInput();
            if ( answer == 1 ) {
                continue;
            }
            else if ( answer == 2 ) {
                break;
            }
            controller.figurePlaced(coordI, coordJ);
        } while ( controller.getWinner() == ' ' );

    }

    public void pvpGameOnline() {
        int answer;
        do {
            System.out.println("Choose: \n1- Server;\n2- Client;\nPress 'q' to quit;");
            if (!tryInput()) {
                return;
            }
            answer = tryParse();
            if (answer == 1) {
                serverPlay();
                break;
            }

            else if (answer == 2) {
                clientPlay();
                break;
            }
            else {
                System.out.println("You have chosen wrong number!");
            }
        } while ( (answer <= -1) || (answer > 2) );
    }

    public void pvcGame() {
        System.out.println(RULE);
        printField();
        String humanFigure;
        do {
            System.out.println("Input your figure. Press 'x' or 'o'. Press 'q' to quit.");
            if ( !tryInput() ) {
                return;
            }
        } while ( (!inString.equals(X) ) && (!inString.equals(O) ));
        humanFigure = inString;
        int answer;
        if ( humanFigure.equals(X) ) {
            boolean placed = false;
            do {
                do {
                    answer = humanInput();
                    if ( answer == 1 ) {
                     continue;
                    }
                     else if ( answer == 2 ) {
                        return;
                    }
                    placed = controller.figurePlaced(coordI, coordJ);
                 } while (!placed);

                if (controller.getWinner() != ' ') {
                    break;
                }

                // here goes computer
                System.out.println("Computer goes to...");
                game.getComputer().makeStep(coordI, coordJ, game.getField().getFieldSize(), game.getField());
                controller.figurePlaced(game.getComputer().getCoordI(), game.getComputer().getCoordJ());
            } while ( controller.getWinner() == ' ' );
        }
        else {
            do {
                // here goes computer
                System.out.println("Computer goes to...");
                game.getComputer().makeStep(coordI, coordJ, game.getField().getFieldSize(), game.getField());
                controller.figurePlaced(game.getComputer().getCoordI(), game.getComputer().getCoordJ());
                if (controller.getWinner() != ' ') {
                    break;
                }
                boolean placed = false;
                do {
                    answer =  humanInput();
                    if ( answer == 1 ) {
                        continue;
                    }
                    else if ( answer == 2 ) {
                        return;
                    }
                    placed = controller.figurePlaced(coordI, coordJ);
                } while (!placed);
            }  while ( controller.getWinner() == ' ' );
        }
    }

    public void printChooseGameMode() {
        System.out.println("Choose the game mode:");
        System.out.println("1 - PvP offline");
        System.out.println("2 - PvP online");
        System.out.println("3 - PvC");
    }

    // return false if user inputs 'q' or if there was input error
    private boolean tryInput() {
        try {
            inString = reader.readLine();
            if (inString.equals(QUIT)) {
                return false;
            }
            else {
                if ( inString.equals("x") ) {
                    inString = X;
                }
                else if ( inString.equals("o") ) {
                    inString = O;
                }
                return true;
            }
        }
        catch  (IOException e) {
            System.out.println("Input ERROR!");
            return false;
        }
    }

    private int tryParse() {
        int answer;
        try {
            answer = Integer.parseInt(inString);
        }
        catch (NumberFormatException e) {
            System.out.println("You have inputted wrong number or not a number!");
            return -1;
        }
        return answer;
    }

    // 0 - OK
    // 1 - input row and column again
    // 2 - quit
    private int humanInput() {
        System.out.println("Input row:");
        if ( !tryInput() ) {
            return 2;  //
        }
        coordI = tryParse();
        if (coordI == -1) {
            return 1;
        }
        System.out.println("Input column:");
        if ( !tryInput() ) {
            return 2;
        }
        coordJ = tryParse();
        if (coordJ == -1) {
            return 1;
        }
        coordI--; // приведение к координатам массива
        coordJ--;
        return 0;
    }

    public void printWinner(char winner) {
        switch (winner) {
            case 'X':    System.out.println("**********X is win!**********");
                         break;
            case 'O':    System.out.println("**********O is win!**********");
                         break;
            case 'D':    System.out.println("***********Draw!************");
                         break;
            default:     System.out.println("Error occurred in printWinner method!");
        }
    }

    public void wrongCoords() {
        System.out.println("You  have chosen wrong coordinates!");
    }

    public void serverPlay() {
        server = new Server();
        try {
            server.getClient().getOutputStream().write(game.getField().getFieldSize());
            int answer;
            do {
                boolean okCoords = false;
                do {
                    answer = humanInput();
                    if ( answer == 1 ) {
                         continue;
                    }
                    else if ( answer == 2 ) {
                        server.getClient().close();
                        server.getServerSocket().close();
                        server = null;
                        System.out.println("Server session ended!");
                        return;
                     }
                    okCoords = controller.figurePlaced(coordI, coordJ); // server place X
                } while (!okCoords);
                server.getClient().getOutputStream().write(coordI);
                server.getClient().getOutputStream().write(coordJ);

                if (controller.getWinner() != ' ') {
                    break;
                }
                System.out.println("O goes to...");
                coordI = server.getClient().getInputStream().read();
                coordJ = server.getClient().getInputStream().read();
                controller.figurePlaced(coordI, coordJ); // client place O
            }while ( controller.getWinner() == ' ' );

            server.getClient().close();
            server.getServerSocket().close();
            System.out.println("Server session ended!");
        }
        catch (UnknownHostException e) {
            System.out.println("Error during sending message! " + e);
        }
        catch (IOException e) {
            System.out.println("Server IO error! " + e);
        }
    }

    public void clientPlay() {
        System.out.println("Input server ip-address");
        if (!tryInput()) {
            return;
        }
        try {
            client = new Client(InetAddress.getByName(inString));
            int in  = client.getSocket().getInputStream().read();
            controller.setGame( new Game(in) ); // force new game to server field.
            game = controller.getGame();
            int answer;
            do {
                System.out.println("X goes to...");
                coordI = client.getSocket().getInputStream().read();
                coordJ = client.getSocket().getInputStream().read();
                controller.figurePlaced(coordI, coordJ); // server place X
                if (controller.getWinner() != ' ') {
                    break;
                }
                boolean okCoords = false;
                do {
                    answer = humanInput();
                    if ( answer == 1 ) {
                        continue;
                    }
                    else if ( answer == 2 ) {
                        client.getSocket().close();
                        client = null;
                        System.out.println("Server session ended!");
                        return;
                    }
                    okCoords = controller.figurePlaced(coordI, coordJ); // client place O
                } while (!okCoords);
                client.getSocket().getOutputStream().write(coordI);
                client.getSocket().getOutputStream().write(coordJ);
            } while ( controller.getWinner() == ' ' );

            client.getSocket().close();
            System.out.println("Client session ended!");
        }
        catch (UnknownHostException e) {
            System.out.println("Error during client construction! " + e);
        }
        catch (IOException e) {
            System.out.println("Client IO error! " + e);
        }
    }
}
