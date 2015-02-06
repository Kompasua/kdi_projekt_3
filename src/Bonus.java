import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

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
        //System.out.println(this.getClass().getName() + " " + counter.checkScoreValue());
        if(counter.checkScoreValue() && counter.checkStepValue()){
            
            return true;
        }else{
            return false;
        }
    }
    
    public boolean updateBonus(int score){
        if(counter.checkScoreValue() && counter.checkStepValue()){
            counter.reset();
            System.out.println("RESET");
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
        /*if (this instanceof Mine){
            System.out.println(this.getClass().getName() + " " + this.counter.getStepMaxValue() + " " + this.counter.checkStepValue());
            System.out.println(this.getClass().getName() + " " + this.counter.getStepCurValue() + " " + this.isVisible());
            }*/
        if (this.getStatus() == true && !this.isVisible()){
            //actually will be reseted in countDown()
            counter.reset();
            //System.out.println("RESET");
            this.countDown(this);
        }
        
        if (this.counter.checkStepValue() && this.isVisible()){
            //System.out.println("Remove " + this.getClass().getName());
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
        //System.out.println("RESET");
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