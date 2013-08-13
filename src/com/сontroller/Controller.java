package com.сontroller;

/*
    Controller realize ControllerInterface.
    It translate requests from view to model (game), when user do something with model (field eg): place figure
    or cancel move
 */
import com.model.ModelInterface;
import com.view.*;


public class Controller implements ControllerInterface {

    private ModelInterface game;

    private ViewInterface view;

    public Controller() {
        view = new View(this);
        view.play();
    }

    public void setGame(ModelInterface game) {
        this.game = game;
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

    // cancel 2 moves
    public boolean cancelMove() {
        boolean moveWasCancelled = false;
        int i = 0;
        do {
            moveWasCancelled = game.cancelMove();
            i++;
        } while ( (i < 2) && (moveWasCancelled) );
        view.printField();
        return moveWasCancelled;
    }

}
