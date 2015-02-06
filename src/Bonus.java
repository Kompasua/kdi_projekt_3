/**
 * This class is used to create bonuses in game.
 * All bonuses appears, existing and disappear due to some rules.
 * This class controls when bonus appears, how long it lives and removes it from game.
 * 
 * @author Anton Bubnov
 * @version 06.02.15
 * 
 */

import ch.aplu.jgamegrid.Actor;

public abstract class Bonus extends Actor {
    protected Counter counter; // Counts steps and score to add\delete bonus
    static PaKman game;
    private boolean visible = false; // True if should! be visible, else false

    // Steps to appear and to be deleted from grid
    protected int stepsToCome;
    protected int stepsToLive;

    public Bonus(PaKman game, String filename, int maxSteps, int maxScore,
            int stepsToLive, int sprites) {
        super(false, filename, sprites);
        this.game = game;
        this.counter = new Counter(maxSteps, maxScore);
        this.stepsToCome = maxSteps;
        this.stepsToLive = stepsToLive;
    }

    /**
     * Check if bonus can be added according to score and steps values
     * 
     * @return true if can, else false
     */
    public boolean getStatus() {
        if (counter.checkScoreValue() && counter.checkStepValue()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check if bonus should be added on grid. If yes, then reset counter. If
     * not, add to bonus score counter given score value.
     * 
     * @param score
     *            to add to counter
     * @return true if bonus should be added, else false
     */
    public boolean updateBonus(int score) {
        if (getStatus()) {
            counter.reset();
            return true;
        } else {
            counter.incrWithNum(score);
            return false;
        }
    }

    /**
     * (Should) Runs every iteration. Increase number of steps and check if it
     * is time to add bonus.
     */
    public void checkBonus() {
        counter.iterate(); // Increment steps in counter
        // Check if bonus must be added
        if ((this.getStatus() && !this.isVisible())
                || (this.counter.checkStepValue() && this.isVisible())) {
            this.countDown();
        }

    }

    /**
     * Add bonus in game, toggle it status and started count down for bonus
     * removing. If bonus is visible - remove it and change steps counter with
     * "steps to add" number, if not - add bonus in grid and change steps
     * counter with "steps to be removed" number.
     */
    public void countDown() {
        if (visible) {
            this.removeSelf();
            // Set steps number to be added in game
            counter.setStepMax(stepsToCome);
        } else {
            game.addActors(this); // Add bonus on grid
            // Set steps number to be removed from game
            counter.setStepMax(stepsToLive);
        }
        visible = !visible; // Toggle visibility
        counter.reset();
    }

    /**
     * @return true if (should be) visible, else false
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * @param visibility
     *            of bonus
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

}
// EOF