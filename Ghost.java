package pacman;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * This is the Ghosts class. It represents the four ghosts in the game.
 */
public class Ghost implements Collideable{
    private Rectangle _rectangle;
    private LinkedList<BoardCoordinate> _list;
    private Pacman _pacman;
    private MazeSquare[][] _map;
    private BoardCoordinate[] _neighbors;
    private Direction[] _neighborDirections;
    private Direction _ghostDirection;
    private int _lives;
    private LinkedList<Ghost> _ghostQueue;
    private static Mode _mode;

    /**
     * This is the Ghost constructor. It instantiates the four ghosts in the game.
     */
    public Ghost(Color color, Pacman pacman, MazeSquare[][] map, LinkedList<Ghost> ghostQueue, int lives){
        _rectangle = new Rectangle(Constants.GHOST_SIZE, Constants.GHOST_SIZE);
        _rectangle.setFill(color);
        _pacman = pacman;
        _map = map;
        _neighbors = new BoardCoordinate[4];
        _neighborDirections = new Direction[4];
        _neighborDirections[0] = Direction.LEFT;
        _neighborDirections[1] = Direction.RIGHT;
        _neighborDirections[2] = Direction.UP;
        _neighborDirections[3] = Direction.DOWN;
        _ghostDirection = Direction.LEFT; //initial direction of the ghost
        _lives = lives;
        _ghostQueue = ghostQueue;
        _mode = Mode.CHASE; //the game starts in chase mode
    }

    /**
     * This method returns the rectangle shape.
     */
    public Rectangle getRectangle(){
        return _rectangle;
    }

    /**
     * This method sets the x location of the ghost.
     */
    public void setX(int x){
        _rectangle.setX(x);
    }

    /**
     * This method sets the y location of the ghost.
     */
    public void setY(int y){
        _rectangle.setY(y);
    }

    /**
     * This method returns the x location of the ghost.
     */
    public int getX(){
        return (int)_rectangle.getX();
    }

    /**
     * This method returns the y location of the ghost.
     */
    public int getY(){
        return (int)_rectangle.getY();
    }

    /**
     * This method handles the collision between pacman and the ghost in different modes.
     */
    @Override
    public void collide() {
        if(_mode == Mode.FRIGHTENED){
            //send the ghost back to the ghost pen and reset it to its starting location
            _ghostQueue.addLast(this);
            this.setX(Game.getGhostLoc(this)[0]);
            this.setY(Game.getGhostLoc(this)[1]);
        }else{
            Game.setGhosts();
        }
    }

    /**
     * This method returns the ghost's score.
     */
    @Override
    public int score() {
        int score = 0;
        if(_mode == Mode.FRIGHTENED){
            score = Constants.GHOST_SCORE;
        }
        return score;
    }

    /**
     * This method returns the remaining lives of pacman.
     */
    public int getLives(){
        return _lives;
    }

    /**
     * This game returns the location of the ghost as board coordinate.
     */
    public BoardCoordinate getCoordinate(){
        int row = this.getY() / Constants.SQUARE_SIDE;
        int column = this.getX() / Constants.SQUARE_SIDE;
        BoardCoordinate coordinate = new BoardCoordinate(row, column, false);
        return coordinate;
    }

    /**
     * This method returns the color of the ghost.
     */
    public Paint getColor(){
        return this.getRectangle().getFill();
    }

    /**
     * This method sets the color of the ghost.
     */
    public void setColor(Color color){
        this.getRectangle().setFill(color);
    }

    /**
     * This method determines the target of different ghosts in different modes.
     */
    public BoardCoordinate getTarget(){
        int row = 0;
        int column = 0;
        Paint ghostColor = this.getColor();
        if(ghostColor == Color.RED){
            if(_mode == Mode.CHASE){
                row = _pacman.getCoordinate().getRow();
                column = _pacman.getCoordinate().getColumn();
            }else if(_mode == Mode.SCATTER){
                row = 0;
                column = 0;
            }
        }else if(ghostColor == Color.LIGHTPINK){
            if(_mode == Mode.CHASE){
                row = _pacman.getCoordinate().getRow();
                column = _pacman.getCoordinate().getColumn() + 2;
            }else if(_mode == Mode.SCATTER){
                row = 0;
                column = Constants.MAX_COLUMN;
            }
        }else if(ghostColor == Color.CYAN){
            if(_mode == Mode.CHASE){
                row = _pacman.getCoordinate().getRow() - 4;
                column = _pacman.getCoordinate().getColumn();
            }else if(_mode == Mode.SCATTER){
                row = Constants.MAX_COLUMN;
                column = 0;
            }
        }else{
            if(_mode == Mode.CHASE){
                row = _pacman.getCoordinate().getRow() + 1;
                column = _pacman.getCoordinate().getColumn() -3;
            }else if(_mode == Mode.SCATTER){
                row = Constants.MAX_ROW;
                column = Constants.MAX_COLUMN;
            }
        }
        BoardCoordinate target = new BoardCoordinate(row, column, true);
        return target;
    }

    /**
     * This is the ghost's Breadth-First search method. This allows the ghost to determine which direction to move in order to reach its target fastest.
     */
    public Direction BFS(BoardCoordinate target){
        _list = new LinkedList<BoardCoordinate>(); //initialize the queue

        //set the root to be the ghost's current board location
        BoardCoordinate currentLoc = this.getCoordinate();
        BoardCoordinate closestLoc = currentLoc;

        //initialize the minimum distance
        double minDistance = Double.POSITIVE_INFINITY;
        //create a 2D array that stores all the directions
        Direction[][] directions = new Direction[Constants.MAP_SIZE][Constants.MAP_SIZE];
        directions[currentLoc.getRow()][currentLoc.getColumn()] = _ghostDirection;

        //get the initial valid neighbors of the ghost
        ArrayList<BoardCoordinate> initialNeighbors = this.getValidNeighbors(currentLoc, directions, _ghostDirection, true);
        for(int i = 0; i<initialNeighbors.size(); i++){
            _list.addLast(initialNeighbors.get(i)); //enqueue the valid neighbors
        }

        while(!_list.isEmpty()){
            currentLoc = _list.removeFirst(); //dequeue from the queue
            int x = target.getColumn() - currentLoc.getColumn();
            int y = target.getRow() - currentLoc.getRow();
            double distance = this.getDistance(x, y);
            //update the minimum distance and the closest square
            if(distance < minDistance){
                minDistance = distance;
                closestLoc = currentLoc;
            }
            //get the valid neighbors of the current square
            ArrayList<BoardCoordinate> validNeighbors = this.getValidNeighbors(currentLoc, directions, _ghostDirection, false);

            //enqueue the neighbors
            for(BoardCoordinate neighbor: validNeighbors){
                int nRow = neighbor.getRow();
                int nCol = neighbor.getColumn();
                int currRow = currentLoc.getRow();
                int currCol = currentLoc.getColumn();
                directions[nRow][nCol] = directions[currRow][currCol];
                _list.addLast(neighbor);
            }
            if(minDistance == 0.0) {
                break;
            }
        }
        return directions[closestLoc.getRow()][closestLoc.getColumn()];
    }

    /**
     * This method generates all 4 neighbors of a square.
     */
    public BoardCoordinate[] generateNeighbors(BoardCoordinate currLoc){

        if(currLoc.getRow()>0 && currLoc.getRow()<Constants.MAX_ROW && currLoc.getColumn()>0 && currLoc.getColumn()<Constants.MAX_COLUMN) {
            _neighbors[0] = new BoardCoordinate(currLoc.getRow(), currLoc.getColumn() - 1, false);
            _neighbors[1] = new BoardCoordinate(currLoc.getRow(), currLoc.getColumn() + 1, false);
            _neighbors[2] = new BoardCoordinate(currLoc.getRow() - 1, currLoc.getColumn(), false);
            _neighbors[3] = new BoardCoordinate(currLoc.getRow() + 1, currLoc.getColumn(), false);
        }
        //wrapping left and right
        if(currLoc.getColumn() == 0){
            _neighbors[0] = new BoardCoordinate(currLoc.getRow(), Constants.MAX_COLUMN, false);
            _neighbors[1] = new BoardCoordinate(currLoc.getRow(), currLoc.getColumn() + 1, false);
            _neighbors[2] = new BoardCoordinate(currLoc.getRow() - 1, currLoc.getColumn(), false);
            _neighbors[3] = new BoardCoordinate(currLoc.getRow() + 1, currLoc.getColumn(), false);
        } if(currLoc.getColumn() == Constants.MAX_COLUMN){
            _neighbors[0] = new BoardCoordinate(currLoc.getRow(), currLoc.getColumn() - 1, false);
            _neighbors[1] = new BoardCoordinate(currLoc.getRow(), 0, false);
            _neighbors[2] = new BoardCoordinate(currLoc.getRow() - 1, currLoc.getColumn(), false);
            _neighbors[3] = new BoardCoordinate(currLoc.getRow() + 1, currLoc.getColumn(), false);
        }
        return _neighbors;
    }

    /**
     * This method returns the valid neighbors of a square.
     */
    public ArrayList<BoardCoordinate> getValidNeighbors(BoardCoordinate currLoc, Direction[][] directions, Direction currDirection, boolean first){
        ArrayList<BoardCoordinate> validNeighbors = new ArrayList<BoardCoordinate>();
        BoardCoordinate[] neighbors = this.generateNeighbors(currLoc);
        for(int i = 0; i<neighbors.length; i++){
            int row = neighbors[i].getRow();
            int column = neighbors[i].getColumn();
            if(!_map[row][column].isWall() && currDirection!= null){
                if(first){ //generating neighbors for the first time
                    if(_neighborDirections[i] != currDirection.getOpposite()){
                        validNeighbors.add(neighbors[i]);
                        directions[row][column] = _neighborDirections[i];
                    }
                }else{
                    //enqueue the valid neighbors if they haven't been visited before
                    if(directions[row][column] == null){
                        validNeighbors.add(neighbors[i]);
                    }
                }
            }
        }
        return validNeighbors;
    }

    /**
     * This method calculates the distance between two squares.
     */
    public double getDistance(double x, double y){
        //use the pythagorean theorem
        double distanceSquared = (Math.pow(x, 2)) + (Math.pow(y, 2));
        double minDistance = Math.pow(distanceSquared, (0.5));
        return minDistance;
    }

    /**
     * This method determines a random direction for the ghost in frightened mode.
     */
    public Direction randomDirection(){
        BoardCoordinate[] neighbors = this.generateNeighbors(this.getCoordinate());
        //keep track of the possible directions
        ArrayList<Direction> validDirection = new ArrayList<Direction>();
        Direction direction = Direction.LEFT;
        for(int i = 0; i<neighbors.length; i++){
            int row = neighbors[i].getRow();
            int column = neighbors[i].getColumn();

            //check for neighbor validity
            if(!_map[row][column].isWall() && _neighborDirections[i] != _ghostDirection.getOpposite()){
                validDirection.add(_neighborDirections[i]);
            }
        }
        if(validDirection.size() == 1){ //only one valid direction
            direction = validDirection.get(0);
        } else{
            //generate a random number between 1 and 2
            int randomNumber = (int) (Math.random() * 2 + 1);
            if(randomNumber == 1){
                direction = validDirection.get(0);
            }else{
                direction = validDirection.get(1);
            }
        }
        return direction;
    }

    /**
     * This method moves the ghost in the given direction.
     */
    public void move(Direction direction){
        _ghostDirection = direction; //update the ghost's current direction
        if(direction != null){
            switch(direction){
                case LEFT:
                    this.setX(this.getX() - Constants.SQUARE_SIDE);
                    //wrap the ghosts
                    if(this.getCoordinate().getRow() == Constants.WRAP_ROW && this.getCoordinate().getColumn() == 0){
                        this.setX(Constants.MAX_X - Constants.SQUARE_SIDE / 2);
                    }
                    break;
                case RIGHT:
                    this.setX(this.getX() + Constants.SQUARE_SIDE);
                    //wrap the ghosts
                    if(this.getCoordinate().getRow() == Constants.WRAP_ROW && this.getCoordinate().getColumn() == (Constants.MAX_COLUMN)){
                        this.setX(Constants.MIN_X + Constants.SQUARE_SIDE / 2);
                    }
                    break;
                case UP:
                    this.setY(this.getY() - Constants.SQUARE_SIDE);
                    break;
                default:
                    this.setY(this.getY() + Constants.SQUARE_SIDE);
                    break;
            }
        }
        int row = this.getCoordinate().getRow();
        int column = this.getCoordinate().getColumn();
        //add the ghost to the array list of its current square
        _map[row][column].getArrayList().add(this);
    }

    /**
     * This method sets the mode of the game.
     */
    public static void setMode(Mode mode){
        _mode = mode;
    }

    /**
     * This method returns the mode of the game.
     */
    public static Mode getMode(){
        return _mode;
    }

    /**
     * This method returns the ghost's current direction.
     */
    public Direction getGhostDirection(){
        return _ghostDirection;
    }

    /**
     * This method sets the ghost's direction.
     */
    public void setGhostDirection(Direction direction){
        _ghostDirection = direction;
    }
}
