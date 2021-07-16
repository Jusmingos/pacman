package pacman;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * This is the Pacman class. It represents the pacman.
 */
public class Pacman{
    private Circle _circle;
    private MazeSquare[][] _map;

    /**
     * This is the pacman constructor. It instantiates a circle that represents the pacman.
     */
    public Pacman(MazeSquare[][] map){
        _circle = new Circle(Constants.PACMAN_RADIUS, Color.YELLOW);
        _map = map;
    }

    /**
     * This method returns the circle shape of pacman.
     */
    public Circle getCircle(){
        return _circle;
    }

    /**
     * This method sets the x location of the circle.
     */
    public void setX(int x){
        _circle.setCenterX(x);
    }

    /**
     * This method returns the x location of the circle.
     */
    public int getX(){
        return (int)_circle.getCenterX();
    }

    /**
     * This method sets the y location of the circle.
     */
    public void setY(int y){
        _circle.setCenterY(y);
    }

    /**
     * This method returns the y location of the circle.
     */
    public int getY(){
        return (int)_circle.getCenterY();
    }

    /**
     * This method determines the move validity of pacman in different directions.
     */
    public boolean moveValidity(int x, int y){
        boolean movable = true;
        int row = this.getCoordinate().getRow();
        int column = this.getCoordinate().getColumn();
        if(row>0 && row<(Constants.MAX_COLUMN) && column>0 && column<(Constants.MAX_COLUMN)){
            //pacman cannot move into the wall
            if(_map[row + x][column + y].isWall()) {
                movable = false;
            }
        }
        //pacman cannot move up and down in its wrapping positions
         if(column == 0 || column == (Constants.MAX_COLUMN)){
            if((x == 1 && y == 0)|| (x == -1 && y == 0)){
                movable = false;
            }
        }
        return movable;
    }

    /**
     * This method moves pacman in the direction that's passed in.
     */
    public void move(Direction direction){
        switch(direction){
            case LEFT:
                if(this.moveValidity(0,-1)){
                    this.setX(this.getX() - Constants.SQUARE_SIDE);
                    //wrapping
                    if(this.getX() < 0){
                        this.setX(Constants.MAX_X);
                    }
                }
                break;
            case RIGHT:
                if(this.moveValidity(0,1)){
                    this.setX(this.getX() + Constants.SQUARE_SIDE);
                    //wrapping
                    if(this.getX() > Constants.GAME_PANE_WIDTH){
                        this.setX(Constants.MIN_X);
                    }
                }
                break;
            case UP:
                if(this.moveValidity(-1,0)){
                    this.setY(this.getY() - Constants.SQUARE_SIDE);
                }
                break;
            default:
                if(this.moveValidity(1,0)){
                    this.setY(this.getY() + Constants.SQUARE_SIDE);
                }
                break;
        }
    }

    /**
     * This method returns pacman's location as board coordinate.
     */
    public BoardCoordinate getCoordinate(){
        int row = this.getY() / Constants.SQUARE_SIDE;
        int column = this.getX() / Constants.SQUARE_SIDE;
        BoardCoordinate coordinate = new BoardCoordinate(row, column, false);
        return coordinate;
    }
}
