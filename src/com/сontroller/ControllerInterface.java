package com.сontroller;

import com.model.Game;

/**
 * Created with IntelliJ IDEA.
 * User: Mike
 * Date: 03.08.13
 * Time: 17:48
 * To change this template use File | Settings | File Templates.
 */
public interface ControllerInterface {

    void setGame(Game game);

    Game getGame();

    boolean figurePlaced(int coordI, int coordJ);

    char  getWinner();

}
