/**
 * PaKActor.java
 * Used for PaKman
 * @author Anton Bubnov and Tony Stankov
 */

import ch.aplu.jgamegrid.*;

import java.awt.event.KeyEvent;

/**
 * The PaKman Actor. It is a keyboard listener and can be directed using the
 * arrow keys.
 */
public class PaKActor extends Actor implements GGKeyRepeatListener {
    private static final int nbSprites = 4;

    private int idSprite;
    private Location next;
    private PaKman game;

    public PaKActor(PaKman game) {
        super(true, "sprites/pacpix.gif", nbSprites); // Rotatable
        this.game = game;
        game.addKeyRepeatListener(this);
        reset();
    }

    public void reset() {
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
        // Cycle sprite
        show(idSprite);
        if (++idSprite == nbSprites)
            idSprite = 0;
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
     * @param location of pill
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
     * the given direction. If possible, it's next position is set.
     */
    private void tryMove(Location.CompassDirection dir) {
        setDirection(dir);
        Location next = getLocation().getNeighbourLocation(dir);
        if (next != null && canMove(next))
            this.next = next;
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
     * code of the pressed key (java.awt.event.KeyEvent)
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
     * handled).
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