/**
 * PaKActor.java
 * Used for PaKman
 * @author Anton Bubnov
 * @version 06.02.15
 */
import ch.aplu.jgamegrid.*;

import java.awt.event.KeyEvent;

import javax.swing.plaf.synth.SynthSeparatorUI;

/**
 * The PaKman Actor. It is a keyboard listener and can be directed using the
 * arrow keys.
 */
public class PaKActor extends Actor implements GGKeyRepeatListener {
    private static final int nbSprites = 4;

    private int idSprite;
    private Location next;
    private PaKman game;

    private boolean mode; // Store if features enabled

    /**
     * @return the mode
     */
    public boolean getMode() {
        return mode;
    }

    /**
     * @param mode
     *            the mode to set
     */
    public void setMode(boolean mode) {
        this.mode = mode;
    }

    public PaKActor(PaKman game) {
        super(true, "sprites/pacpix.gif", nbSprites); // Rotatable
        this.game = game;
        mode = false;
        game.addKeyRepeatListener(this);
        reset();
    }

    public void reset() {
        mode = false;
        idSprite = 0;
        next = null;
    }

    /**
     * Called in the game loop. Updates pakman's location and cycles the sprite.
     */
    public void act() {
        if (next != null && !next.equals(getLocation()))
            doMove(next);
        // Check if location contains a pill
        if (game.getLevel().getTile(getLocation()) == Tile.PILL) {
            eatPill(getLocation());
        }
        game.checkBonuses();
        // Cycle sprite
        show(idSprite);
        if (++idSprite == nbSprites)
            idSprite = 0;
    }

    public void toggleMode() {
        mode = !mode;
    }

    /**
     * Move pakman to the given location.
     */
    private void doMove(Location loc) {
        setLocation(loc);
    }

    /**
     * Remove pill from game grid and increase curScore number. If level
     * completed - reset level
     * 
     * @param location
     *            of pill
     */
    // Probably should be moved in PaKman.java
    private void eatPill(Location location) {
        game.getLevel().eat(location); // Remove pill
        game.getScore().addCurScore(1); // Add 1 score for eaten pill
        if (game.getLevel().completed()) { // Check if level done
            game.getScore().saveCurScore(); // Save scores earned in this level
            game.levelDone();
        }
    }

    /**
     * Try to move one step in the given direction. Pakman is always turned in
     * the given direction. If possible, it's next position is set. Also when
     * features mode is enabled then if pacman arrives the end of the map and
     * same position on the other side of the map is not a wall, then he
     * telepotrs on the other side.
     */
    private void tryMove(Location.CompassDirection dir) {
        setDirection(dir);
        Location next = getLocation().getNeighbourLocation(dir);
        if (!gameGrid.isInGrid(next) && mode) {
            switch (dir) {
            case EAST:
                if (Tile.WALL != game.getLevel().getTile(
                        getLocation().getAdjacentLocation(180,
                                getNbHorzCells() - 1)))
                    this.next = getLocation().getAdjacentLocation(180,
                            getNbHorzCells() - 1);
                break;
            case WEST:
                if (Tile.WALL != game.getLevel().getTile(
                        getLocation().getAdjacentLocation(0,
                                getNbHorzCells() - 1)))
                    this.next = getLocation().getAdjacentLocation(0,
                            getNbHorzCells() - 1);
                break;
            case NORTH:
                if (Tile.WALL != game.getLevel().getTile(
                        getLocation().getAdjacentLocation(90,
                                getNbVertCells() - 1)))
                    this.next = getLocation().getAdjacentLocation(90,
                            getNbVertCells() - 1);
                break;
            case SOUTH:
                if (Tile.WALL != game.getLevel().getTile(
                        getLocation().getAdjacentLocation(270,
                                getNbVertCells() - 1)))
                    this.next = getLocation().getAdjacentLocation(270,
                            getNbVertCells() - 1);
                break;
            }
        } else {
            if (next != null && canMove(next))
                this.next = next;
        }
    }

    /**
     * Return true iff the given location is a valid location for this actor
     */
    protected boolean canMove(Location location) {

        return gameGrid.isInGrid(location)
                && game.getLevel().getTile(location) != Tile.WALL;
    }

    // ========================== Keyboard handling ===========================

    /**
     * Called when a key is pressed. This event handler consumes arrow keys and
     * sets the direction of this PaKman according to the key pressed.
     * 
     * @param keyCode
     *            code of the pressed key (java.awt.event.KeyEvent)
     */
    public void keyRepeated(int keyCode) {
        if (isRemoved()) // Actor already removed from gameloop
            return;

        handleKey(keyCode);
    }

    /**
     * If the given key controls pakman, handle it. Currently, the arrow keys
     * are pakman controls.
     * 
     * @returns true iff keyCode is a pakman control (and therefore was
     *          handled).
     */
    private boolean handleKey(int keyCode) {
        switch (keyCode) {
        case KeyEvent.VK_LEFT:
            tryMove(Location.WEST);
            break;
        case KeyEvent.VK_UP:
            tryMove(Location.NORTH);
            break;
        case KeyEvent.VK_RIGHT:
            tryMove(Location.EAST);
            break;
        case KeyEvent.VK_DOWN:
            tryMove(Location.SOUTH);
            break;
        default:
            return false;
        }

        return true;
    }
}
// EOF