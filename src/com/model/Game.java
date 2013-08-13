package com.model;

/*
Game is model. It realizes ModelInterface.
It contains all logic of the game and data (Field, search winner algorithms and etc.,
provides all necessary methods to work with this data.
 */
import java.util.Stack;

public class Game implements ModelInterface{

    private static final char DEFAULT_X = 'X';

    private static final char DEFAULT_O = 'O';

    private static final char DEFAULT_FIELD_VALUE = ' ';

    private static final char DRAW = 'D';

    private Field field;

    private char winner;

    private int countSteps;

    private Player computer;

    private Stack prevMoves;

    // private Computer computer;

    public Game(int fieldSize) {
        winner = ' ';
        countSteps = 0;
        field = new Field(fieldSize);
        computer = new Computer();
        prevMoves = new Stack();
    }

    public char[][] getField() {
        return field.getField();
    }

    public Field getInstanceField() {
        return field;
    }

    public int getFieldSize() {
        return field.getFieldSize();
    }

    public boolean isGameEnds(int coordI, int coordJ) {
        if ( isWin(coordI, coordJ) ) {
            return true;
        }
        if (isDraw()) {
            return true;
        }
        return false;
    }

    public boolean isWin(int coordI, int coordJ) {

        // horizontal
        int j = 1;
        while ( (j < field.getFieldSize()) && (field.getField()[coordI][j - 1] == field.getField()[coordI][j])  ) {
              j++;
        }
        if (j == field.getFieldSize()) {
            winner = field.getField()[coordI][coordJ];
            return true;
        }

        // vertical
        int i = 1;
        while ( (i < field.getFieldSize()) && (field.getField()[i - 1][coordJ] == field.getField()[i][coordJ]) ) {
            i++;
        }
        if (i == field.getFieldSize() ) {
            winner = field.getField()[coordI][coordJ];
            return true;
        }
        // diagonals
        if (coordI == coordJ) {
            i = j = 1;
            while ( (i < field.getFieldSize())&& (j < field.getFieldSize()) && (field.getField()[i - 1][j - 1] == field.getField()[i][j]) ) {
                i++;
                j++;
            }
            if ( (i == field.getFieldSize() ) && (j == field.getFieldSize() ) ) {
                winner = field.getField()[coordI][coordJ];
                return true;
            }
        }

        if (coordJ + coordI == field.getFieldSize() - 1) {
            j = field.getFieldSize() - 2;
            i = 1;
            while ( (i < field.getFieldSize()) && (j > Field.getMinCoord() - 1) && (field.getField()[i - 1][j + 1] == field.getField()[i][j]) ) {
                i++;
                j--;
            }
            if ((i == field.getFieldSize() ) && (j == Field.getMinCoord() - 1)) {
                winner = field.getField()[coordI][coordJ];
                return true;
            }
        }

        return false;

    }

    public boolean isDraw() {
        if ( countSteps >= field.getFieldSize() * field.getFieldSize() ) {
            winner = DRAW; // draw!
            return true;
        }
        return false;
    }

    public boolean placeFigure(int coordI, int coordJ) {
        boolean figurePlaced = false;
        if ( countSteps % 2 == 0) {
            figurePlaced = field.setField(coordI, coordJ, DEFAULT_X );
        }
        else {
            figurePlaced = field.setField(coordI, coordJ, DEFAULT_O );
        }
        if (figurePlaced) {
            countSteps++;
            prevMoves.push(coordI);
            prevMoves.push(coordJ);
            return true;
        }
        else {
            return false;
        }
    }

    public char getWinner() {
        return winner;
    }

    public Player getComputer() {
        return computer;
    }

    public boolean cancelMove() {
        if ( !prevMoves.empty() ) {
            int coordJ = (Integer)prevMoves.pop();
            int coordI = (Integer)prevMoves.pop();
            boolean moveWasCancelled = field.setField(coordI, coordJ, DEFAULT_FIELD_VALUE);
            if (moveWasCancelled) {
                countSteps--;
            }
            return ( moveWasCancelled ) ;
        }
        else {
            return false;
        }
    }

    public void makeComputerStep(int coordI, int coordJ, int fieldSize, Field field) {
        computer.makeStep(coordI, coordJ, fieldSize, field);
    }
}

