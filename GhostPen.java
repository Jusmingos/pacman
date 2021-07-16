package pacman;
import java.util.LinkedList;

/**
 * This is the Ghost Pen class. It represents the ghost pen that stores the ghosts.
 */
public class GhostPen {

    private LinkedList<Ghost> _ghostQueue;
    private Ghost _inky;
    private Ghost _pinky;
    private Ghost _clyde;
    private Ghost[] _ghosts;
    private MazeSquare[][] _map;

    /**
     * This is the Ghost Pen constructor. It initiates a linkedlist that represents a queue for the ghosts.
     */
    public GhostPen(LinkedList<Ghost> ghostQueue, Ghost inky, Ghost pinky, Ghost clyde, MazeSquare[][] map, Ghost[] ghosts){
        _ghostQueue = ghostQueue;
        _inky = inky;
        _pinky = pinky;
        _clyde = clyde;
        _ghosts = ghosts;
        this.initialPopulation();
        _map = map;
    }

    /**
     * This method populates the ghost pen at the beginning. Blinky is outside of the ghost pen.
     */
    private void initialPopulation(){
        _ghostQueue.addLast(_pinky);
        _ghostQueue.addLast(_inky);
        _ghostQueue.addLast(_clyde);
    }

    /**
     * This method releases ghosts from the ghost pen.
     */
    public void releaseGhost(){
        if(!_ghostQueue.isEmpty()){
            Ghost currentGhost = _ghostQueue.removeFirst();
            currentGhost.setX(Constants.BLINKY_INITIAL_X);
            currentGhost.setY(Constants.BLINKY_INITIAL_Y);
        }
    }

    /**
     * This method moves the ghost according to the different modes.
     */
    public void moveGhost(){
        if(Ghost.getMode() == Mode.FRIGHTENED){
            for(Ghost ghost: _ghosts){
                ghost.move(ghost.randomDirection());
            }
        }else{
            for(Ghost ghost: _ghosts){
                ghost.move(ghost.BFS(ghost.getTarget()));
            }
        }
    }

    /**
     * This method removes the ghost from the array list of the squares as it moves.
     */
    public void removeGhost(){
        for(Ghost ghost: _ghosts){
            _map[ghost.getCoordinate().getRow()][ghost.getCoordinate().getColumn()].getArrayList().remove(ghost);
        }
    }

    /**
     * This method returns the ghost queue, which indicates which ghosts are in the ghost pen.
     */
    public LinkedList<Ghost> getGhostQueue(){
        return _ghostQueue;
    }

    /**
     * This method adds a ghost to the ghost pen.
     */
    public void setGhostQueue(Ghost ghost){
        _ghostQueue.addLast(ghost);
    }
}

