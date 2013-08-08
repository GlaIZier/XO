package com.model;

/**
 * Created with IntelliJ IDEA.
 * User: Кирилл
 * Date: 31.07.13
 * Time: 15:54
 * To change this template use File | Settings | File Templates.
 */
public class Game {

    private static final char DEFAULT_X = 'X';

    private static final char DEFAULT_O = 'O';

    private Field field;

    private char winner;

    private int countSteps;

    private Player computer;

    // private Computer computer;

    public Game(int fieldSize) {
        winner = ' ';
        countSteps = 0;
        field = new Field(fieldSize);
        computer = new Computer();
    }

    public Game(){
        winner = ' ';
        countSteps = 0;
        field = new Field();
    }

    public Field getField() {
        return field;
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
            winner = 'D'; // draw!
            return true;
        }
        return false;
    }

    public boolean placeFigure(int coordI, int coordJ) {
        if ( countSteps % 2 == 0) {
           if ( field.setField(coordI, coordJ, DEFAULT_X) ) {
               countSteps++;
               return true;
           }
        }
        else {
           if ( field.setField(coordI, coordJ, DEFAULT_O) ) {
               countSteps++;
               return true;
           }
        }
        return false;
    }

    public char getWinner() {
        return winner;
    }

    public int getCountSteps() {
        return countSteps;
    }

    public Player getComputer() {
        return computer;
    }
}

