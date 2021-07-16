package pacman;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * This is the Energizer class. It implements the collideable interface. It represents the energizer that pacman can eat.
 */
public class Energizer implements Collideable{
    private Circle _circle;
    private Pane _gamePane;

    /**
     * This is the Energizer constructor. It instantiates a circle that represents the energizer. It also keeps track of the number of energizers in the game.
     */
    public Energizer(Pane gamePane){
        _circle = new Circle(Constants.ENERGIZER_RADIUS, Color.WHITE);
        _gamePane = gamePane;
        Game.setNum(1); //increase by 1 each time an energizer gets added
    }

    /**
     * This method returns the circle shape.
     */
    public Circle getCircle(){
        return _circle;
    }

    /**
     * This method sets the x location of the energizer.
     */
    public void setCenterX(int x){
        _circle.setCenterX(x);
    }

    /**
     * This method sets the y location of the energizer.
     */
    public void setCenterY(int y){
        _circle.setCenterY(y);
    }

    /**
     * This method handles pacman's collision with the energizer.
     */
    @Override
    public void collide() {
        _gamePane.getChildren().remove(this.getCircle());
        Game.setFrightened(); //enter frightened mode when pacman eats an energizer
        //re-set the counters
        Game.setCounter1(0);
        Game.setCounter2(0);
        Game.setCounter3(0);
        Game.setNum(-1); //decrease by 1 each time an energizer gets eaten
    }

    /**
     * This method returns the energizer's score.
     */
    @Override
    public int score() {
        return Constants.ENERGIZER_SCORE;
    }
}
