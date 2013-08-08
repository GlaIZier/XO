package com.model;

/**
 * Created with IntelliJ IDEA.
 * User: Кирилл
 * Date: 01.08.13
 * Time: 13:45
 * To change this template use File | Settings | File Templates.
 */
public class Field {
    private static final int DEFAULT_FIELD_SIZE = 3;

    private static final int MAX_FIELD_SIZE = 10;

    private static final int MIN_FIELD_SIZE = 2;

    private static final char DEFAULT_FIELD_VALUE = ' ';

    private static final int MIN_COORD = 0;


    private final int fieldSize;

    private char[][] field;

    public Field() {
        this(DEFAULT_FIELD_SIZE);
    }

    public Field(int fieldSize) {
        if ( (fieldSize <= MIN_FIELD_SIZE)  || (fieldSize > MAX_FIELD_SIZE) ) {
           this.fieldSize = DEFAULT_FIELD_SIZE;
        }
        else {
            this.fieldSize = fieldSize;
        }
        field = new char[this.fieldSize][this.fieldSize]; // значит поле еще не создавалось
        eraseField();
    }

    public char[][] getField() {
        return field;
    }

    public int getFieldSize() {
        return fieldSize;
    }

    public boolean setField(int coordI, int coordJ, char value) {
        if ( (coordI  >= MIN_COORD) && (coordI < fieldSize) && (coordJ  >= MIN_COORD) && (coordJ < fieldSize)
              && ( (field[coordI][coordJ] == DEFAULT_FIELD_VALUE) ||
                 ( (field[coordI][coordJ] != DEFAULT_FIELD_VALUE) && (value == DEFAULT_FIELD_VALUE ) ) ) ) {   // for cancel move
            field[coordI][coordJ] = value;
            return true;
        }
        else  {
            return false;
        }

    }

    public void eraseField() {
        for(int i = 0; i < fieldSize; i++) {
            eraseLine(i);
        }
    }

    public void eraseLine(int i) {
        for(int j = 0; j < fieldSize; j++) {
            field[i][j] = DEFAULT_FIELD_VALUE;
        }
    }

    public static int getMinCoord() {
        return MIN_COORD;
    }

    public static char getDefaultFieldValue() {
        return DEFAULT_FIELD_VALUE;
    }
}
