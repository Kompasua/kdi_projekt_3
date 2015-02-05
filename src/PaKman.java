/**
 * PaKman.java
 * Simple PaKman implementation
 * @author Anton Bubnov
 */

import ch.aplu.jgamegrid.*;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class PaKman extends GameGrid implements GGKeyListener {
    protected PaKActor pacActor;
    // Add ghosts
    private Ghost silly1;
    private Ghost silly2;
    private Ghost randy;
    private Ghost tracy;
    
    private Cherry cherry;
    private PineApple papple;
    private Mine mine;

    private Level theLevel;
    private boolean checkCollisions; // For the collision mechanism below
    private Score score; // Collect score and lives of pacman
    
    private int tCount; //Count 40 steps to toggle bach after cherry was eaten

    public PaKman() {
        // Need to set the winsize, because it cannot be changed.
        super(30, 33, 20, true);
        
        pacActor = new PaKActor(this);
        score = new Score(this, 3); // Create score storage
        tCount = -1;
        
        setSimulationPeriod(100);
        setTitle("PaKman");
        addKeyListener(this);
        addActListener(new CheckerReset());
        setupLevel(new Level(this));

        // Show and activate the game window
        show();
        activate();
    }
    
    /**
     * @return the cherry
     */
    public Cherry getCherry() {
        return cherry;
    }

    /**
     * @return the papple
     */
    public PineApple getPapple() {
        return papple;
    }

    /**
     * @param cherry the cherry to set
     */
    public void setCherry(Cherry cherry) {
        this.cherry = cherry;
    }

    /**
     * @return the score of pacman
     */
    public Score getScore() {
        return score;
    }

    /**
     * @param score
     * the score to set
     */
    public void setScore(Score score) {
        this.score = score;
    }

    public void reset() {
        checkCollisions = true;
        removeAllActors(); // Remove actors from grid
        Ghost.list.clear(); // Remove all created ghosts from array
        setupLevel(new Level(this));
        score.setCurScore(0); // Reset score earned in this level
    }

    /**
     * Toggle hunting/fleeing mode of all ghosts.
     */
    public void toggleHunting() {
        for (Ghost ghost : Ghost.list) {
            ghost.toggleHunting();
        }
    }

    /**
     * Setup the given level:
     * <ul>
     * <li>draw the maze;</li>
     * <li>put pakman at its starting position;</li>
     * <li>create the ghost(s) at their starting positions;</li>
     * <li>initializes internae</li>
     * </ul>
     */
    public void setupLevel(Level level) {
        Ghost.list.clear(); // Remove all created ghosts from array
        
        theLevel = level;
        setNbHorzCells(level.getSize().x);
        setNbVertCells(level.getSize().y);
        level.drawLevel();

        addActor(new PostMovementChecker(), level.getSize());
        addActor(pacActor, level.getPakmanStart());
        addActor(new PreMovementChecker(), level.getSize());

        // Initialize new ghosts
        //silly1 = new Silly(this);
        //silly2 = new Silly(this);
        randy = new Randy(this);
        //tracy = new Tracy(this);
        
        // Initialize bonuses
        cherry = new Cherry(this, 100, 50, 30);
        papple = new PineApple(this, 10, 10, 40); //Second and third values don't matter
        mine = new Mine(this, 0, 20, 30);
        
        // Add all created ghosts on game grid.
        for (Ghost ghost : Ghost.list) {
            addActor(ghost, level.getGhostStart());
        }

        /* 
         * pakman acts after ghosts and between movement checkers, to ensure
         * correct collision detection.
         */
        setActOrder(Ghost.class, PreMovementChecker.class, PaKActor.class,
                PostMovementChecker.class);
    }
    
    /**
     * @return the mine
     */
    public Mine getMine() {
        return mine;
    }

    public void addActors(Actor actor){
        if (actor instanceof Cherry) {
            addActor(actor, this.getLevel().getGhostStart());
        }else if (actor instanceof PineApple) {
            addActor(actor, this.getLevel().getPakmanStart());
        }else if (actor instanceof Mine && pacActor.getMode()) {
            addActor(actor, getRandomLocation());
            System.out.println("Add mine");
        }
        
    }
    
    public Location getRandomLocation(){
        Random rg = new Random();
        int rY = rg.nextInt(this.nbVertCells);
        int rX = rg.nextInt(this.nbHorzCells);
        while (this.getLevel().getTile(new Location(rX,rY)) != Tile.PASSAGE) {
            rY = rg.nextInt(this.nbVertCells);
            rX = rg.nextInt(this.nbHorzCells);
        }
        return new Location(rX,rY);
    }

    /**
     * Called with pac == pacActor when pacActor and some other actor are at the
     * same location.
     * 
     * @return 0
     */
    public int collide(Actor pac, Actor other) {
        pac.collide(pac, other);
        other.collide(other, pac);
        
        if (other instanceof Ghost){
            // return collided ghost on his start position
            other.setLocation(theLevel.getGhostStart());
    
            Ghost ghost = (Ghost) other; // Used to get and set mode of ghost
            // Decrease lives if hunting, +50 if fleeing.
            checkLives(ghost.getMode()); 
            other.setLocation(theLevel.getGhostStart()); // Set ghost on start pos.
        }else if(other instanceof Cherry){
            System.out.println("Collide with cherry");
            toggleHunting();
            tCount = 0;
            other.removeSelf();
            other.reset();
        }else if (other instanceof PineApple) {
            System.out.println("Collide with pineapple");
            mine.countDown(mine);
            pacActor.toggleMode();
            other.removeSelf();
            other.reset();
        }else if (other instanceof Mine) {
            mine.show(1);
            checkLives(true);
        }
        return 0;
    }

    /**
     * Decrease lives number of add points. First check mode. If fleeing - add
     * 50 points, otherwise check if "level failed" or "game over" and reset
     * pacActor score and lives if "game over".
     */
    private void checkLives(boolean mode) {
        // Check mode
        if (mode) {
            if (!score.decrementLives()) {
                score.reset(); // Reset lives and score
                gameOver();
            } else {
                score.setCurScore(0); // Reset score earned this level
                levelFail();
            }
        } else {
            score.addCurScore(50); // If ghost was eaten
        }
    }
    
    public void checkBonuses(){
        System.out.println(cherry.counter.getStepCurValue());
        if (tCount != -1) {
            if (tCount < 40) {
                tCount++;
            } else {
                tCount = -1;
                toggleHunting();
            }
        }
        
        //System.out.println(papple.counter.getStepCurValue());
        //System.out.println( cherry.counter.getStepCurValue());
        papple.checkBonus();
        cherry.checkBonus();
        mine.checkBonus();
        
    }

    /**
     * Return some ghost of type Randy. For testing purposes only
     */
    public Actor getRandy() {
        return randy;
    }

    /**
     * Return some ghost of type Silly.
     */
    public Actor getSilly() {
        return silly1;
    }

    /**
     * Return some ghost of type Tracy.
     */
    public Actor getTracy() {
        return tracy;
    }

    //==================================================================================================
    //=========================== DO NOT CHANGE ANYTHIGN BELOW THIS LINE ===============================
    //==================================================================================================

    /**
     * Return PaKman's location.
     * @returns pakman's location
     */
    public Location wherePakman() {
        return pacActor.getLocation();
    }


    /**
     * Displays the given score string, e.g: "Player 1: 500"
     * @param player the player number (currently ignored)
     * @param score the score of this player
     * @param lives the number of lives of this player
     */
    public void displayScore(int player, int score, int lives) {
        setTitle("Score: "+ score +" lives: "+ lives);
    }
    
    
    /**
     * Return the current level.
     * @return the current level
     */
    public Level getLevel() {
        return theLevel;
    }       
    

    
    /**
     * Display a message and pause the game.
     * @param message the message to display
     */
    private void pause(String message) {
        GGBackground bg = getBg();
        bg.setPaintColor(Color.red);
        bg.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 48));
        bg.drawText(message, new Point(toPoint(new Location(6, 15))));
        refresh();
        doPause();
    }
 
    
    /**
     * Display a game over message and pause the game.
     */
    public void gameOver() {
        pause("Game Over");
    }
 
    
    /**
     * Display a next level message and pause the game.
     */
    public void levelDone() {
        pause("You won!");
    }

    
    /**
     * Display a you died message and pause the game.
     */
    public void levelFail() {
        pause("You died");
    }
    
    
    public static void main(String[] args) {
        new PaKman();
    }

    //===================================== Actor collision detection methods ======================
    /* Because the GameGrid collision detector doesn't detect collision when two actor move
       simultaneously and cross each other, we implement our own detectors. There are two actors
       (so they will be called IN the gameloop), to be called before and after the pacman(s) move.
     */
    private class PreMovementChecker extends Actor {
        public void act() {
            checkCollision();
        }
    }
    
    private class PostMovementChecker extends Actor {
        public void act() {
            checkCollision();
        }
    }
    
    private class CheckerReset implements GGActListener {
        public void act() {
            checkCollisions = true;
        }
    }

    
    /**
     * Check whether pakman and a ghost collide.
     * If so, call the collide() method.
     */
    public void checkCollision() {
        if (!checkCollisions)
            return;
            
        for (Actor a : getActors()) {
            if (a == pacActor)
                continue;
            if (pacActor.getLocation().equals(a.getLocation())) {
                collide(pacActor, a);
                checkCollisions = false;
                return;
            }
        }     
    }
    
    
    
    //========================================== Key listener methods =============================
    
    /** KeyListener method (no function for us) */
    public boolean keyPressed(KeyEvent event) {
        return false;
    }

    /**
     * KeyListener method.
     * Here we act on the (press and) release of keys.
     * Currently implemented:
     * f toggles hunting/fleeing mode.
     */
    public boolean keyReleased(KeyEvent event) {
        switch (event.getKeyChar()) {
            case 'f': cherry.countDown(cherry); return true;
            case 'g': papple.countDown(papple); return true;
        }
        
        return false;
    }
}
