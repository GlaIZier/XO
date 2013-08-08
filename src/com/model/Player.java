package com.model;

/**
 * Created with IntelliJ IDEA.
 * User: Mike
 * Date: 04.08.13
 * Time: 14:27
 * To change this template use File | Settings | File Templates.
 */
public abstract class Player {

    int coordI;

    int coordJ;

    public abstract void makeStep(int coordI, int coordJ, int fieldSize, Field field);

    public abstract int getCoordI();

    public abstract int getCoordJ();
}
