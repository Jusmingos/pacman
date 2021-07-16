package pacman;

/**
 * This is the constants class. It contains all the constants used in this game.
 */

public class Constants {
    public static final int STAGE_WIDTH = 690;
    public static final int STAGE_HEIGHT = 750;

    public static final int GAME_PANE_WIDTH = 690;
    public static final int GAME_PANE_HEIGHT = 690;

    public static final int BOTTOM_PANE_WIDTH = 660;
    public static final int BOTTOM_PANE_HEIGHT = 60;

    public static final int MAP_SIZE = 23;

    public static final int SQUARE_SIDE = 30;
    public static final int DOT_RADIUS = 3;
    public static final int ENERGIZER_RADIUS = 8;
    public static final int PACMAN_RADIUS = 13;
    public static final int GHOST_SIZE = SQUARE_SIDE;

    public static final int MAX_X = 22 * Constants.SQUARE_SIDE + Constants.SQUARE_SIDE / 2;
    public static final int MIN_X = Constants.SQUARE_SIDE / 2;

    public static final int LABEL_WIDTH = 150;
    public static final int LABEL_HEIGHT = 50;
    public static final int BUTTON_WIDTH = 80;
    public static final int BUTTON_HEIGHT = 15;
    public static final int SCORE_LABEL_X = 100;
    public static final int LABEL_Y = 690;
    public static final int LIVE_LABEL_X = 300;
    public static final int QUIT_BUTTON_X = 500;
    public static final int QUIT_BUTTON_Y = 700;

    public static final int PACMAN_INITIAL_X = 11 * Constants.SQUARE_SIDE + Constants.SQUARE_SIDE / 2;
    public static final int PACMAN_INITIAL_Y = 17 * Constants.SQUARE_SIDE + Constants.SQUARE_SIDE / 2;

    public static final int BLINKY_INITIAL_X = 11 * Constants.SQUARE_SIDE;
    public static final int BLINKY_INITIAL_Y = 8 * Constants.SQUARE_SIDE;
    public static final int PINKY_INITIAL_X = 12 * Constants.SQUARE_SIDE;
    public static final int PINKY_INITIAL_Y = 10 * Constants.SQUARE_SIDE;
    public static final int INKY_INITIAL_X = 11 * Constants.SQUARE_SIDE;
    public static final int INKY_INITIAL_Y = 10 * Constants.SQUARE_SIDE;
    public static final int CLYDE_INITIAL_X = 10 * Constants.SQUARE_SIDE;
    public static final int CLYDE_INITIAL_Y = 10 * Constants.SQUARE_SIDE;

    public static final int FRUIT_RADIUS_X = 15;
    public static final int FRUIT_RADIUS_Y = 12;
    public static final int FRUIT_ROW = 11;
    public static final int FRUIT_COLUMN = 22;
    public static final int WRAP_ROW = 11;
    public static final int MAX_ROW = 22;
    public static final int MAX_COLUMN = 22;

    public static final int FONT_SIZE = 20;
    public static final int GAME_LABEL_FONT_SIZE = 25;
    public static final int QUIT_FONT_SIZE = 17;
    public static final double KEYFRAME1_DURATION = 0.2;
    public static final double KEYFRAME2_DURATION = 7;

    public static final int CHASE_MODE_DURATION = 80;
    public static final int SCATTER_MODE_DURATION = 50;
    public static final int FRIGHTENED_MODE_DURATION1 = 30;
    public static final int FRIGHTENED_MODE_DURATION2 = 35;

    public static final int FRUIT_COUNTER1 = 25;
    public static final int FRUIT_COUNTER2 = 50;
    public static final int FRUIT_COUNTER3 = 75;

    public static final int GHOST_SCORE = 200;
    public static final int ENERGIZER_SCORE = 100;
    public static final int DOT_SCORE = 10;
    public static final int FRUIT_SCORE = 50;
}
