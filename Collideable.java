package pacman;

/**
 * This is the collideable interface. It represents all the elements that pacman collides with in the game.
 */
public interface Collideable {
    /**
     * This is the collide method that handles collision.
     */
    public void collide();

    /**
     * This is the score method that returns the scores of different elements in the game.
     */
    public int score();
}
