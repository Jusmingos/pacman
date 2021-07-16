package pacman;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the maze square class. The maze square keeps track of the state of the square and contains other functions.
 */
public class MazeSquare {
    //use an arraylist to keep track of the elements in a maze square
    private List<Collideable> _collideables;
    private Rectangle _rectangle;

    /**
     * This is the Maze Square constructor. It instantiates a rectangle that represents a maze square.
     */
    public MazeSquare(){
        _rectangle = new Rectangle(Constants.SQUARE_SIDE, Constants.SQUARE_SIDE);
        //create an arraylist to hold all the squares in the map
        _collideables = new ArrayList<Collideable>();
    }

    /**
     * This method returns the rectangle shape.
     */
    public Rectangle getRectangle(){
        return _rectangle;
    }

    /**
     * This method sets the x location of the maze square.
     */
    public void setX(int x){
        _rectangle.setX(x);
    }

    /**
     * This method returns the x location of the maze square.
     */
    public int getX(){
        return (int)_rectangle.getX();
    }

    /**
     * This method sets the y location of the maze square.
     */
    public void setY(int y){
        _rectangle.setY(y);
    }

    /**
     * This method returns the y location of the maze square.
     */
    public int getY(){
        return (int)_rectangle.getY();
    }

    /**
     * This method returns the color of the maze square.
     */
    public Paint getColor(){
        return _rectangle.getFill();
    }

    /**
     * This method tells a square if it is a wall or not.
     */
    public boolean isWall(){
        boolean isWall = false;
        if(this.getColor() != Color.BLACK){
            isWall = true;
        }
        return isWall;
    }

    /**
     * This method returns the array list of a maze square.
     */
    public List getArrayList(){
        return _collideables;
    }
}
