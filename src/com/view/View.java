package com.view;
/*
     View realizes Viewinterface, provides user interface.
     It can appeal to model to know the state of field, print it, winner or something
     When user wants to interract with model (for example place figure) it asks controller to do it

 */


import com.model.Client;
import com.model.Game;
import com.model.ModelInterface;
import com.model.Server;
import com.сontroller.ControllerInterface;

import java.io.*;



public class View implements ViewInterface {

    public static final String GREETING = "\n******New Game******\nThis is simple XO play. Please, choose field size > 2 and < 10! Input q to quit.";

    public static final String RULE = "X and O takes turns. You should input column number, press Enter and then input row number.\nPress \"q\" to quit.";

    public static final String QUIT = "q";

    public static final String X = "X";

    public static final String O = "O";

    public static final String CANCEL_MOVE = "z";

    public final BufferedReader reader;

    private ModelInterface game;

    private ControllerInterface controller;

    private String inString;

    private int coordI;

    private int coordJ;

    private Server server;

    private Client client;

    public View (ControllerInterface controller) {
        this.controller = controller;
        coordI = 0;
        coordJ = 0;
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public void printField() {
        for (int i = 0; i < game.getFieldSize(); i++) {
            printLine(i);
        }
    }

    public void printLine(int i) {
        for (int j = 0; j < game.getFieldSize(); j++) {
            System.out.print("[" + game.getField()[i][j] + "] ");
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
                game = new Game(answer);
                controller.setGame(game);
            }
            else {
                game = null;
                controller.setGame(null);
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
                              game = null;
                              controller.setGame(null);
                              continue;
            }
        } while (true);

    }

    public void printChooseGameMode() {
        System.out.println("Choose the game mode:");
        System.out.println("1 - PvP offline");
        System.out.println("2 - PvP online");
        System.out.println("3 - PvC");
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
        } while ( game.getWinner() == ' ' );

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

    public void serverPlay() {
        server = new Server();
        if ( (server.getClient() == null) || (server.getServerSocket() == null)) {
            System.out.println("Error during establishing connection!");
            return;
        }
        server.writeToOutputStream(game.getFieldSize());
        int answer;
        do {
            boolean okCoords = false;
            do {
                answer = humanInput();
                if ( answer == 1 ) {
                    continue;
                }
                else if ( answer == 2 ) {
                    server.closeSocket();
                    server = null;
                    return;
                }
                okCoords = controller.figurePlaced(coordI, coordJ); // server place X
            } while (!okCoords);
            server.writeToOutputStream(coordI);
            server.writeToOutputStream(coordJ);
            if (game.getWinner() != ' ') {
                break;
            }

            System.out.println("O goes to...");
            coordI = server.getToInputStream();
            coordJ = server.getToInputStream();
            if ( (coordI != -1) && (coordJ != -1) ) {
                controller.figurePlaced(coordI, coordJ); // client place O
            }
            else {
                System.out.println("Network connection error!");
                server.closeSocket();
                server = null;
                return;
            }
        }while ( game.getWinner() == ' ' );
        server.closeSocket();
    }

    public void clientPlay() {
        System.out.println("Input server ip-address");
        if (!tryInput()) {
            return;
        }
        client = new Client(inString);
        if ( client.getSocket() == null ) {
            System.out.println("Can't connect to the server. It could be due to bad ip-address.");
            return;
        }
        int in  = client.getToInputStream();
        if ( in != -1) {
            game = new Game(in);   // force new game to server field.
            controller.setGame(game);
        }
        else {
            System.out.println("Network connection error!");
            client.closeSocket();
            client = null;
            return;
        }

        int answer;
        do {
            System.out.println("X goes to...");
            coordI = client.getToInputStream();
            coordJ = client.getToInputStream();
            if ( (coordI != -1) && (coordJ != -1) ) {
                controller.figurePlaced(coordI, coordJ); // server place X
            }
            else {
                System.out.println("Network connection error!");
                client.closeSocket();
                client = null;
                return;
            }
            if (game.getWinner() != ' ') {
                break;
            }

            boolean okCoords = false;
            do {
                answer = humanInput();
                if ( answer == 1 ) {
                    continue;
                }
                else if ( answer == 2 ) {
                    client.closeSocket();
                    client = null;
                    System.out.println("Server session ended!");
                    return;
                }
                okCoords = controller.figurePlaced(coordI, coordJ); // client place O
            } while (!okCoords);
            client.writeToOutputStream(coordI);
            client.writeToOutputStream(coordJ);
        } while ( game.getWinner() == ' ' );

        client.closeSocket();
        System.out.println("Client session ended!");
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
        if ( humanFigure.equals(X) ) {
            do {
                if ( pvcHumanGoes() == 2) {
                    return;
                }
                if (game.getWinner() != ' ') {
                    break;
                }
                pvcCompGoes();
            } while ( game.getWinner() == ' ' );
        }
        else {
            do {
                pvcCompGoes();
                if (game.getWinner() != ' ') {
                    break;
                }
                if ( pvcHumanGoes() == 2) {
                    return;
                }
            }  while ( game.getWinner() == ' ' );
        }
    }

    private int pvcHumanGoes() {
        boolean placed = false;
        int answer;
        do {
            answer = humanInput();
            if ( answer == 1 ) {
                continue;
            }
            else if ( answer == 2 ) {
                return 2;
            }
            else if ( answer == 3) {
                System.out.println("Field after cancel:");
                if ( !controller.cancelMove() ) {
                    System.out.println("Error during cancel!");
                }
                placed = false;
                continue;
            }
            placed = controller.figurePlaced(coordI, coordJ);
        } while (!placed);
        return 0;
    }

    private void pvcCompGoes() {
        System.out.println("Computer goes to...");
        game.makeComputerStep(coordI, coordJ, game.getFieldSize(), game.getInstanceField());
        controller.figurePlaced(game.getComputer().getCoordI(), game.getComputer().getCoordJ());
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
    // 3 - cancel last move
    private int humanInput() {
        System.out.println("Input row. Press 'z' to cancel your last move (PvC game only).");
        if ( !tryInput() ) {
            return 2;  //
        }
        else if ( inString.equals(CANCEL_MOVE) ) {
            return 3;
        }
        coordI = tryParse();
        if (coordI == -1) {
            return 1;
        }

        System.out.println("Input column. Press 'z' to cancel your last move (PvC game only).");
        if ( !tryInput() ) {
            return 2;
        }
        else if ( inString.equals(CANCEL_MOVE) ) {
            return 3;
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
}
