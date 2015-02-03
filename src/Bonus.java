import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

/**
 * TODO
 * - remove game variable and change constructor
 */

/**
 * @author Anton Bubnov
 *
 */
public abstract class Bonus extends Actor {
    public Counter counter; //protected
    private PaKman game; // static
    private boolean visible = false;
    
    protected int stepsToLive;
    protected int stepsToCome;
    
    
    public Bonus(PaKman game, String filename, int maxSteps, int maxScore, int stepsToLive){
        super(false, filename, 1);
        this.game = game;
        this.counter = new Counter(maxSteps,maxScore);
        this.stepsToCome = maxSteps;
        this.stepsToLive = stepsToLive;
    }
    
    public void makeIteration(int score){
        counter.incrWithNum(score);
    }
    
    public boolean getStatus(){
        System.out.println(this.getClass().getName() + " " + counter.checkScoreValue());
        if(counter.checkScoreValue() && counter.checkStepValue()){
            counter.reset();
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
       // System.out.println(this.getClass().getName() + " " + this.getStatus());
        //System.out.println(this.getClass().getName() + " " + this.isVisible());
        counter.iterate();
        if (this.getStatus() == true && !this.isVisible()){
            this.countDown(this);
        }
        if (this.counter.checkStepValue() && this.isVisible()){
            System.out.println("Remove " + this.getClass().getName());
            this.removeSelf();
            this.countDown(this);
        }
        
    }
    
    public void countDown(Actor bonus){
        //System.out.println(this.getClass().getName() + " " + visible);
        if (visible){
            visible = false;
            bonus.removeSelf();
            counter.setStepMax(stepsToCome);
        }else{
            visible = true;
            game.addActors(bonus);
            counter.setStepMax(stepsToLive);
        }
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