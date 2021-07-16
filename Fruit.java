package pacman;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

/**
 * This is the Fruit class. It implements the collideable interface. It represents a fruit that pacman can eat and earn score.
 */
public class Fruit implements Collideable{
    private Ellipse _ellipse;
    private Pane _gamePane;

    /**
     * This is the Fruit constructor. It instantiates an ellipse that represents the fruit. It also keeps track of the number of fruits in the game.
     */
    public Fruit(Pane gamePane, Color color){
        _ellipse = new Ellipse(Constants.FRUIT_RADIUS_X, Constants.FRUIT_RADIUS_Y);
        _ellipse.setFill(color);
        _gamePane = gamePane;
        Game.setNum(1); //increase by 1 every time a fruit gets added
    }

    /**
     * This is the collide method. It removes the fruit when pacman collides with it.
     */
    @Override
    public void collide() {
        _gamePane.getChildren().remove(this.getEllipse());
        Game.setNum(-1); //decrease by 1 every time a fruit gets eaten
    }

    /**
     * This method returns the fruit's score.
     */
    @Override
    public int score() {
        return Constants.FRUIT_SCORE;
    }

    /**
     * This method returns the ellipse shape.
     */
    public Ellipse getEllipse(){
        return _ellipse;
    }

    /**
     * This method sets the x location of the fruit.
     */
    public void setX(int x){
        _ellipse.setCenterX(x);
    }

    /**
     * This method sets the y location of the fruit.
     */
    public void setY(int y){
        _ellipse.setCenterY(y);
    }
}
