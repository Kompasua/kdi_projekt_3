import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

/**
 * @author Anton Bubnov
 *
 */

/**
 * TODO 
 * rewrite countDown without input parameter
 *
 */
public abstract class Bonus extends Actor {
    protected Counter counter; // public
    static PaKman game; // private
    private boolean visible = false;
    
    // Steps to appear and to be deleted from grid
    protected int stepsToCome;
    protected int stepsToLive;
    
    
    public Bonus(PaKman game, String filename, int maxSteps, int maxScore, int stepsToLive, int sprites){
        super(false, filename, sprites);
        this.game = game;
        this.counter = new Counter(maxSteps,maxScore);
        this.stepsToCome = maxSteps;
        this.stepsToLive = stepsToLive;
    }
    
    public void makeIteration(int score){
        counter.incrWithNum(score);
    }
    
    public boolean getStatus(){
        if(counter.checkScoreValue() && counter.checkStepValue()){
            return true;
        }else{
            return false;
        }
    }
    
    public boolean updateBonus(int score){
        if(counter.checkScoreValue() && counter.checkStepValue()){
            counter.reset();
            return true;
        }else{
            counter.incrWithNum(score);
            return false;
        }
    }
    
    public void checkBonus(){
        counter.iterate();
        //rewrite more short
        /*
        if (this.getStatus() == true && !this.isVisible()){
            //actually will be reseted in countDown()
            //counter.reset();
            this.countDown(this);
        }
        
        if (this.counter.checkStepValue() && this.isVisible()){
            // actually will be removed in count down
            //this.removeSelf();
            this.countDown(this);
        }
        */
        if ( (this.getStatus() && !this.isVisible()) || (this.counter.checkStepValue() && this.isVisible()) ) {
            this.countDown(this);
        }
        
    }
    
    public void countDown(Actor bonus){
        if (visible){
            //visible = false;
            bonus.removeSelf();
            counter.setStepMax(stepsToCome);
        }else{
            //visible = true;
            game.addActors(bonus);
            counter.setStepMax(stepsToLive);
        }
        visible = !visible; //more short
        counter.reset();
    }

    /**
     * @return the visible
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * @param visible the visible to set
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    
}
//EOF