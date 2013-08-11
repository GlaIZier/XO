package com.сontroller;

import com.model.ModelInterface;


public interface ControllerInterface {

    void setGame(ModelInterface game);

    boolean figurePlaced(int coordI, int coordJ);

    char  getWinner();

    boolean cancelMove();
}
