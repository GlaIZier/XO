package com.model;

public class Computer extends Player {

    public Computer() {
        coordI = 0;
        coordJ = 0;
    }

     // find line where human's figures  max => place on that line. If there's no place, then random
    public void makeStep(int coordI, int coordJ, int fieldSize, Field field) {
        int i;
        int j;
        int maxHumanFigures = 0;
        char maxPosition = 'i';
        int curMax = 0;
        // incidental diagonal
        if (coordJ + coordI == fieldSize - 1) {
            j = fieldSize - 1;
            curMax = 0;
            for (i = 0; i < fieldSize; i++) {
                if (field.getField()[i][j] ==  field.getField()[coordI][coordJ]) {
                    curMax++;
                }
                j--;
            }
            if (maxHumanFigures < curMax) {
                maxHumanFigures = curMax;
                maxPosition = 'i';
            }
        }
        // main diagonal
        if (coordI == coordJ) {
            j = 0;
            curMax = 0;
            for (i = 0; i < fieldSize; i++) {
                if (field.getField()[i][j] ==  field.getField()[coordI][coordJ]) {
                    curMax++;
                }
                j++;
            }
            if (maxHumanFigures < curMax) {
                maxHumanFigures = curMax;
                maxPosition = 'd';
            }
        }
        // horizontal
        curMax = 0;
        for(j = 0; j < fieldSize; j++) {
            if (field.getField()[coordI][j] ==  field.getField()[coordI][coordJ]) {
                curMax++;
            }
        }
        if (maxHumanFigures < curMax) {
            maxHumanFigures = curMax;
            maxPosition = 'h';
        }
        //vertical
        curMax = 0;
        for(i = 0; i < fieldSize; i++) {
            if (field.getField()[i][coordJ] ==  field.getField()[coordI][coordJ]) {
                curMax++;
            }
        }
        if (maxHumanFigures < curMax) {
            maxHumanFigures = curMax;
            maxPosition = 'v';
        }

        boolean figurePlaced = false; // all cells are occupied => random
        switch (maxPosition) {
            case 'i':
                j = fieldSize - 1;
                for (i = 0; i < fieldSize; i++) {
                    if (field.getField()[i][j] ==  Field.getDefaultFieldValue() ) {
                        this.coordI = i;
                        this.coordJ = j;
                        figurePlaced = true;
                        break;
                    }
                    j--;
                }
                break;
            case 'd':
                j = 0;
                for (i = 0; i < fieldSize; i++) {
                    if (field.getField()[i][j] ==  Field.getDefaultFieldValue() ) {
                        this.coordI = i;
                        this.coordJ = j;
                        figurePlaced = true;
                        break;
                    }
                    j++;
                }
                break;
            case 'h':
                for(j = 0; j < fieldSize; j++) {
                    if (field.getField()[coordI][j] ==  Field.getDefaultFieldValue()) {
                        this.coordI = coordI;
                        this.coordJ = j;
                        figurePlaced = true;
                        break;
                    }
                }
                break;
            case 'v':
                for(i = 0; i < fieldSize; i++) {
                    if (field.getField()[i][coordJ] ==  Field.getDefaultFieldValue() ) {
                        this.coordI = i;
                        this.coordJ = coordJ;
                        figurePlaced = true;
                        break;
                    }
                }
                break;
        }
        if (!figurePlaced) {
            randomCoords(field);
        }
    }

    public int getCoordI() {
        return coordI;
    }

    public int getCoordJ() {
        return coordJ;
    }

    private void randomCoords(Field field) {
        do {
            this.coordI = (int)(Math.random()*3); // int from [0,2]
            this.coordJ = (int)(Math.random()*3);
        } while (field.getField()[this.coordI][this.coordJ] !=  Field.getDefaultFieldValue());
    }
}
