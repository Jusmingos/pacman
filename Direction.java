package pacman;

/**
 * This is the Direction enum. It represents the four directions.
 */
public enum Direction {
    LEFT, RIGHT, UP, DOWN;

    /**
     * This method returns the opposite of a given direction.
     */
    public Direction getOpposite(){
        switch (this){
            case LEFT:
                return RIGHT;
            case RIGHT:
                return LEFT;
            case UP:
                return DOWN;
            case DOWN:
                return UP;
        }
        return this;
    }
}
