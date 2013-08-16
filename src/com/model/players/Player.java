package com.model.players;

import com.model.Field;

public abstract class Player {

    int coordI;

    int coordJ;

    public abstract void makeStep(int coordI, int coordJ, int fieldSize, Field field);

    public abstract int getCoordI();

    public abstract int getCoordJ();
}
