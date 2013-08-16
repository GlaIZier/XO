package com.model;

import com.model.players.Player;

public interface ModelInterface {

    char[][] getField();

    Field getInstanceField();

    int getFieldSize();

    boolean isGameEnds(int coordI, int coordJ);

    boolean isWin(int coordI, int coordJ);

    boolean isDraw();

    boolean placeFigure(int coordI, int coordJ);

    char getWinner();

    Player getComputer();

    boolean cancelMove();

    void makeComputerStep(int coordI, int coordJ, int fieldSize, Field field);

}
