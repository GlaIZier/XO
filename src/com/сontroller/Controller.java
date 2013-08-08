package com.сontroller;

import com.model.Game;
import com.view.*;

/**
 * Created with IntelliJ IDEA.
 * User: Mike
 * Date: 01.08.13
 * Time: 20:19
 * To change this template use File | Settings | File Templates.
 */
public class Controller implements ControllerInterface {

    private Game game;

    private ViewInterface view;

    public Controller() {
        view = new View(this);
        view.play();
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public boolean figurePlaced(int coordI, int coordJ) {
        if ( game.placeFigure(coordI,coordJ) ) {
            if (game.isGameEnds(coordI, coordJ)) {
                view.printWinner(game.getWinner());
            }
            view.printField();
            return true;
        }
        else {
            view.wrongCoords();
            view.printField();
            return false;
        }
    }
    public char  getWinner() {
        return game.getWinner();
    }

}
