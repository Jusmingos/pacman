package pacman;

import cs15.fnl.pacmanSupport.SquareType;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import java.util.LinkedList;
import java.util.List;

/**
 * This is the top level class that contains the major game logic for Pacman.
 */

public class Game {
    private Pane _gamePane;
    private Label _scoreLabel;
    private Label _liveLabel;
    private static Label _gameLabel;
    private MazeSquare[][] _map;
    private static Ghost _blinky;
    private static Ghost _inky;
    private static Ghost _pinky;
    private static Ghost _clyde;
    private static Ghost[] _ghosts; //store the ghosts in an array
    private static Pacman _pacman;
    private int _score;
    private static int _lives;
    private static Timeline _timeline1;
    private static Timeline _timeline2;
    private Direction _pacmanDirection; //keep track of pacman's direction
    private LinkedList<Ghost> _ghostQueue;
    private static GhostPen _ghostPen;
    private static int _fruitCounter; //determine when to the add the fruit

    //each counter keeps track of the duration of a mode
    private static int _counter1;
    private static int _counter2;
    private static int _counter3;
    private static int _num;
    private boolean _paused;

    /**
     * This is the game class constructor. It initiates and sets up the different elements of the game.
     */
    public Game(Pane gamePane, Label scoreLabel, Label liveLabel){
        _gamePane = gamePane;
        _gamePane.addEventHandler(KeyEvent.KEY_PRESSED, new KeyHandler());
        _gamePane.setFocusTraversable(true);
        _scoreLabel = scoreLabel;
        _liveLabel = liveLabel;
        _gameLabel = new Label();
        _score = 0;
        _lives = 3;
        this.setupScoreLabel();
        this.setupLiveLabel();
        this.setupTimeLine1();
        this.setupTimeLine2();
        this.setupGameLabel();

        _map = new MazeSquare[Constants.MAP_SIZE][Constants.MAP_SIZE];
        _pacman = new Pacman(_map);
        _ghostQueue = new LinkedList<Ghost>();

        _pacmanDirection = Direction.LEFT;

        _blinky = new Ghost(Color.RED, _pacman, _map, _ghostQueue, _lives);
        _inky = new Ghost(Color.CYAN, _pacman, _map, _ghostQueue, _lives);
        _pinky = new Ghost(Color.LIGHTPINK, _pacman, _map, _ghostQueue, _lives);
        _clyde = new Ghost(Color.ORANGE, _pacman, _map, _ghostQueue, _lives);
        _ghosts = new Ghost[4];
        this.ghosts();

        _num = 0;
        this.setupMap();
        _ghostPen = new GhostPen(_ghostQueue, _inky, _pinky, _clyde, _map, _ghosts);
        _fruitCounter = 0;
        _counter1 = 0;
        _counter2 = 0;
        _counter3 = 0;
        _paused = false;
    }

    /**
     * This method sets up the initial layout of the game.
     */
    private void setupMap(){
        SquareType[][] supportMap = cs15.fnl.pacmanSupport.SupportMap.getSupportMap();
        //add all the squares to the map
        for(int i = 0; i<_map.length; i++){
            for(int j = 0; j<_map[0].length; j++){
                _map[i][j] = new MazeSquare();
                if(supportMap[i][j] == SquareType.WALL){
                    _map[i][j].getRectangle().setFill(Color.DARKBLUE);
                }
                else {
                    _map[i][j].setX(j * Constants.SQUARE_SIDE);
                    _map[i][j].setY(i * Constants.SQUARE_SIDE);
                    _map[i][j].getRectangle().setFill(Color.BLACK);
                    _gamePane.getChildren().add(_map[i][j].getRectangle());
                }
            }
        }
        for(int i = 0; i<_map.length; i++){
            for(int j = 0; j<_map[0].length; j++){
                SquareType type = supportMap[i][j];
                switch(type){
                    case DOT:
                        Dot dot = new Dot(_gamePane);
                        _gamePane.getChildren().add(dot.getCircle());
                        dot.setCenterX(j * Constants.SQUARE_SIDE + Constants.SQUARE_SIDE / 2);
                        dot.setCenterY(i * Constants.SQUARE_SIDE + Constants.SQUARE_SIDE / 2);
                        _map[i][j].getArrayList().add(dot);
                        break;
                    case ENERGIZER:
                        Energizer energizer = new Energizer(_gamePane);
                        _gamePane.getChildren().add(energizer.getCircle());
                        energizer.setCenterX(j * Constants.SQUARE_SIDE + Constants.SQUARE_SIDE / 2);
                        energizer.setCenterY(i * Constants.SQUARE_SIDE + Constants.SQUARE_SIDE / 2);
                        _map[i][j].getArrayList().add(energizer);
                        break;
                    case PACMAN_START_LOCATION:
                        _gamePane.getChildren().add(_pacman.getCircle());
                        _pacman.setX(j * Constants.SQUARE_SIDE + Constants.SQUARE_SIDE / 2);
                        _pacman.setY(i * Constants.SQUARE_SIDE + Constants.SQUARE_SIDE / 2);
                        break;
                    case GHOST_START_LOCATION:
                        _blinky.setX(j * Constants.SQUARE_SIDE);
                        _blinky.setY((i-2) * Constants.SQUARE_SIDE);
                        _clyde.setX((j-1) * Constants.SQUARE_SIDE);
                        _clyde.setY(i * Constants.SQUARE_SIDE);
                        _inky.setX(j * Constants.SQUARE_SIDE);
                        _inky.setY(i * Constants.SQUARE_SIDE);
                        _pinky.setX((j+1) * Constants.SQUARE_SIDE);
                        _pinky.setY(i * Constants.SQUARE_SIDE);
                        _gamePane.getChildren().addAll(_blinky.getRectangle(), _pinky.getRectangle(),
                                _clyde.getRectangle(), _inky.getRectangle());
                        _map[i-2][j].getArrayList().add(_blinky);
                        _map[i][j-1].getArrayList().add(_clyde);
                        _map[i][j].getArrayList().add(_inky);
                        _map[i][j+1].getArrayList().add(_pinky);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    /**
     * This method puts all the ghosts in an array so that it's easier when they have common behavior.
     */
    private void ghosts(){
        _ghosts[0] = _blinky;
        _ghosts[1] = _pinky;
        _ghosts[2] = _inky;
        _ghosts[3] = _clyde;
    }

    /**
     * This method controls pacman's collision with different elements in the game.
     */
    public void collide(){
        //pacman collides with dots, energizers and ghosts (all Collideable)
        int row = _pacman.getCoordinate().getRow();
        int column = _pacman.getCoordinate().getColumn();
        List<Collideable> collideables = _map[row][column].getArrayList();
        for(Collideable collideable: collideables){
            collideable.collide();
            _score += collideable.score();
        }
        collideables.clear();
    }

    /**
     * This method sets up the label that displays the score.
     */
    private void setupScoreLabel(){
        _scoreLabel.setText("Score: " + _score);
        _scoreLabel.setFont(Font.font("Verdana", FontWeight.BOLD, Constants.FONT_SIZE));
        _scoreLabel.setTextFill(Color.WHITE);
    }

    /**
     * This method sets up the label that displays the lives of pacman.
     */
    private void setupLiveLabel(){
        _liveLabel.setText("Lives: " + _lives);
        _liveLabel.setFont(Font.font("Verdana", FontWeight.BOLD, Constants.FONT_SIZE));
        _liveLabel.setTextFill(Color.WHITE);
    }

    /**
     * This method sets up the main timeline of the game.
     */
    private void setupTimeLine1(){
        KeyFrame kf = new KeyFrame(Duration.seconds(Constants.KEYFRAME1_DURATION), new MoveHandler());
        _timeline1 = new Timeline(kf);
        _timeline1.setCycleCount(Animation.INDEFINITE);
        _timeline1.play();
    }

    /**
     * This method sets up the ghost pen's timeline.
     */
    private void setupTimeLine2(){
        KeyFrame kf = new KeyFrame(Duration.seconds(Constants.KEYFRAME2_DURATION), new TimeHandler());
        _timeline2 = new Timeline(kf);
        _timeline2.setCycleCount(Animation.INDEFINITE);
        _timeline2.play();
    }

    /**
     * This method updates the score and the lives label.
     */
    private void setLabels(){
        _scoreLabel.setText("Score: " + _score);
        _liveLabel.setText("Lives: " + _lives);
    }

    /**
     * This method sets up the label that displays the status of the game.
     */
    private void setupGameLabel(){
        _gameLabel.setFont(Font.font("Verdana", FontWeight.BOLD, Constants.GAME_LABEL_FONT_SIZE));
        _gameLabel.setTextFill(Color.WHITE);
        _gamePane.getChildren().add(_gameLabel);
        _gameLabel.setVisible(false);
    }

    /**
     * This method brings the ghost visually to the front.
     */
    private void toFront(){
        for(Ghost ghost: _ghosts){
            ghost.getRectangle().toFront();
        }
    }

    /**
     * This method handles pacman's collision with the ghost during chase and scatter mode.
     */
    public static void setGhosts(){
        if(_lives > 0){
            _lives -= 1;
        }
        if(_lives == 0){
            for(Ghost ghost: _ghosts){
                ghost.setGhostDirection(null);
            }
            _timeline1.stop();
            _timeline2.stop();
            _gameLabel.setText("Game Over");
            _gameLabel.setVisible(true);
        }
        for(Ghost ghost: _ghosts){
            if(!_ghostPen.getGhostQueue().contains(ghost) && ghost!=_blinky){
                _ghostPen.setGhostQueue(ghost);
            }
        }
        toInitialLoc();
        //blinky is outside of the pen at the beginning of the game
        if(_ghostPen.getGhostQueue().contains(_blinky)){
            _ghostPen.getGhostQueue().remove(_blinky);
        }
    }

    public static void toInitialLoc(){
        _pacman.setX(Constants.PACMAN_INITIAL_X);
        _pacman.setY(Constants.PACMAN_INITIAL_Y);
        _blinky.setX(Constants.BLINKY_INITIAL_X);
        _blinky.setY(Constants.BLINKY_INITIAL_Y);
        _pinky.setX(Constants.PINKY_INITIAL_X);
        _pinky.setY(Constants.PINKY_INITIAL_Y);
        _inky.setX(Constants.INKY_INITIAL_X);
        _inky.setY(Constants.INKY_INITIAL_Y);
        _clyde.setX(Constants.CLYDE_INITIAL_X);
        _clyde.setY(Constants.CLYDE_INITIAL_Y);
    }

    /**
     * This method checks if the user has won the game.
     */
    private void winGame(){
        if(_num == 0){
            _timeline1.stop();
            _timeline2.stop();
            _gameLabel.setText("Winner");
            _gameLabel.setVisible(true);
            toInitialLoc();
        }
    }

    /**
     * This method switches between chase and scatter mode. The ghosts turn 180 degrees when they switch modes.
     */
    private void mode(){
        //alternate between chase and scatter mode
        if(_counter1 == Constants.CHASE_MODE_DURATION){
            Ghost.setMode(Mode.SCATTER);
            _counter1 = 0;
            for(Ghost ghost: _ghosts){
                ghost.setGhostDirection(ghost.getGhostDirection().getOpposite());
            }
        } if(_counter2 == Constants.SCATTER_MODE_DURATION){
            Ghost.setMode(Mode.CHASE);
            _counter2 = 0;
            for(Ghost ghost: _ghosts){
                ghost.setGhostDirection(ghost.getGhostDirection().getOpposite());
            }
        }
    }

    /**
     * This method puts the ghosts in frightened mode.
     */
    public static void setFrightened(){
        Ghost.setMode(Mode.FRIGHTENED);
        for(Ghost ghost: _ghosts){
            //the ghost turn 180 degrees
            ghost.setGhostDirection(ghost.getGhostDirection().getOpposite());
        }
    }

    /**
     * This method tells the ghosts what to do during frightened mode.
     */
    private void frightened(){
        if(Ghost.getMode() == Mode.FRIGHTENED) {
            for(Ghost ghost: _ghosts){
                ghost.setColor(Color.SKYBLUE);
            }
            //the ghosts blink before frightened mode ends
            if(_counter3 == Constants.FRIGHTENED_MODE_DURATION1) {
                for(Ghost ghost: _ghosts){
                    ghost.setColor(Color.WHITE);
                }
            }
            if(_counter3 == Constants.FRIGHTENED_MODE_DURATION2) {
                //set the ghosts back to chase mode once frightened mode ends
                Ghost.setMode(Mode.CHASE);
                for(Ghost ghost: _ghosts){
                    ghost.setGhostDirection(ghost.getGhostDirection().getOpposite());
                }
                _blinky.setColor(Color.RED);
                _pinky.setColor(Color.LIGHTPINK);
                _inky.setColor(Color.CYAN);
                _clyde.setColor(Color.ORANGE);
                _counter3 = 0;
            }
        }
    }

    /**
     * This method returns the initial location of the ghost passed in. This method is used when pacman eats a ghost in frightened mode, and the ghost needs to be sent back to its original position.
     */
    public static int[] getGhostLoc(Ghost ghost){
        int[] loc = new int[2];
        if(ghost == _blinky){
            loc[0] = Constants.PINKY_INITIAL_X + Constants.SQUARE_SIDE;
            loc[1] = Constants.PINKY_INITIAL_Y;
        } else if(ghost == _pinky){
            loc[0] = Constants.PINKY_INITIAL_X;
            loc[1] = Constants.PINKY_INITIAL_Y;
        } else if(ghost == _inky){
            loc[0] = Constants.INKY_INITIAL_X;
            loc[1] = Constants.INKY_INITIAL_Y;
        } else{
            loc[0] = Constants.CLYDE_INITIAL_X;
            loc[1] = Constants.CLYDE_INITIAL_Y;
        }
        return loc;
    }

    /**
     * This method sets counter one, which keeps track of the duration of chase mode.
     */
    public static void setCounter1(int counter1){
        _counter1 = counter1;
    }

    /**
     * This method sets counter two, which keeps track of the duration of scatter mode.
     */
    public static void setCounter2(int counter2){
        _counter2 = counter2;
    }

    /**
     * This method sets counter three, which keeps track of the duration of frightened mode.
     */
    public static void setCounter3(int counter3){
        _counter3 = counter3;
    }

    /**
     * This method sets the number of dots, energizers and fruits remaining in the game.
     */
    public static void setNum(int num){
        _num += num;
    }

    /**
     * This method increments the counters to keeps track of the duration of the modes.
     */
    private void setCounter(){
        Mode mode = Ghost.getMode();
        switch(mode){
            case CHASE:
                _counter1++;
                break;
            case SCATTER:
                _counter2++;
                break;
            default:
                _counter3++;
                break;
        }
    }

    /**
     * This method adds the fruit at semi-random times during the game.
     */
    private void addFruit(){
        //generate a random number between 1 and 3
        int randomNumber = (int) (Math.random() * 3 + 1);

        //set the time and color of the fruit according to the random number
        int counter = 0;
        Color color = null;
        switch(randomNumber){
            case 1:
                counter = Constants.FRUIT_COUNTER1;
                color = Color.PURPLE;
                break;
            case 2:
                counter = Constants.FRUIT_COUNTER2;
                color = Color.LIGHTGREEN;
                break;
            default:
                counter = Constants.FRUIT_COUNTER3;
                color = Color.HOTPINK;
                break;
        }

        //add the fruit when the time elapsed in the game equals the pre-determined time above
        if(_fruitCounter == counter){
           Fruit fruit = new Fruit(_gamePane, color);
           _gamePane.getChildren().add(fruit.getEllipse());
           fruit.setX(Constants.FRUIT_COLUMN * Constants.SQUARE_SIDE + Constants.SQUARE_SIDE/2);
           fruit.setY(Constants.FRUIT_ROW * Constants.SQUARE_SIDE + Constants.SQUARE_SIDE/2);
           _map[Constants.FRUIT_ROW][Constants.FRUIT_COLUMN].getArrayList().add(fruit);
        }
    }

    /**
     * This method pauses the game.
     */
    private void pause(){
       if(!_paused){
           _timeline1.pause();
           _timeline2.pause();
           _paused = true;
           _gameLabel.setVisible(true);
           _gameLabel.setText("Paused");
       } else{
           _timeline1.play();
           _timeline2.play();
           _paused = false;
           _gameLabel.setVisible(false);
       }
    }

    /**
     * This is the key handler class. It controls the key inputs in the game.
     */
    private class KeyHandler implements EventHandler<KeyEvent>{

        /**
         * This is the overrided handle method. It handles the key inputs.
         */
        @Override
        public void handle(KeyEvent event) {
            KeyCode keyPressed = event.getCode();
            switch(keyPressed){
                //pacman keeps moving in a direction as long as its valid
                case LEFT:
                    if(_pacman.moveValidity(0,-1)){
                        _pacmanDirection = Direction.LEFT;
                    }
                    break;
                case RIGHT:
                    if(_pacman.moveValidity(0,1)){
                        _pacmanDirection = Direction.RIGHT;
                    }
                    break;
                case UP:
                    if(_pacman.moveValidity(-1,0)){
                        _pacmanDirection = Direction.UP;
                    }
                    break;
                case DOWN:
                    if(_pacman.moveValidity(1,0)){
                        _pacmanDirection = Direction.DOWN;
                    }
                    break;
                case P:
                    Game.this.pause();
                    break;
                default:
                    break;
            }
            event.consume();
        }
    }

    /**
     * This is the move handler. It handles the movements of the game.
     */
    private class MoveHandler implements EventHandler<ActionEvent>{

        /**
         * This is the overrided handle method. It controls the game's movement at every time stamp.
         */
        @Override
        public void handle(ActionEvent event) {
            //keep track of the counters and set the mode accordingly
            _fruitCounter ++;
            Game.this.mode();
            Game.this.setCounter();

            //move pacman in its given direction
            _pacman.move(_pacmanDirection);
            Game.this.collide(); //check for collision
            _ghostPen.removeGhost();
            _ghostPen.moveGhost();
            Game.this.frightened();
            Game.this.collide(); //check for collision again
            Game.this.toFront();
            Game.this.setLabels();
            Game.this.addFruit();
            Game.this.winGame();
        }
    }

    /**
     * This is the time handler. It represents the time handler of the ghost pen.
     */
    private class TimeHandler implements EventHandler<ActionEvent>{

        /**
         * This is the overrided handle method. It releases the ghosts from the ghost pen at a regular time interval.
         */
        @Override
        public void handle(ActionEvent event) {
            _ghostPen.releaseGhost();
        }
    }
}
