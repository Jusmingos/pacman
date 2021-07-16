package pacman;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * This is the Dot class. It implements the collideable interface. It represents a dot that pacman can eat and earn score.
 */
public class Dot implements Collideable{
    private Circle _circle;
    private Pane _gamePane;

    /**
     * This is the Dot constructor. It instantiates a circle that represents the dot and keeps track of the number of dots in the game.
     */
    public Dot(Pane gamePane){
        _circle = new Circle(Constants.DOT_RADIUS, Color.WHITE);
        _gamePane = gamePane;
        Game.setNum(1); //increase by 1 every time a dot gets added
    }

    /**
     * This method returns the circle shape.
     */
    public Circle getCircle(){
        return _circle;
    }

    /**
     * This method sets the x location of the dot.
     */
    public void setCenterX(int x){
        _circle.setCenterX(x);
    }

    /**
     * This method sets the y location of the dot.
     */
    public void setCenterY(int y){
        _circle.setCenterY(y);
    }

    /**
     * This method removes the dot when pacman collides with it.
     */
    @Override
    public void collide() {
        _gamePane.getChildren().remove(this.getCircle());
        Game.setNum(-1); //decrease by 1 each time pacman eats a dot
    }

    /**
     * This method returns the dot's score.
     */
    @Override
    public int score() {
        return Constants.DOT_SCORE;
    }
}
