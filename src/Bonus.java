import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

/**
 * 
 */

/**
 * @author Anton Bubnov
 *
 */
public abstract class Bonus extends Actor {
    protected Counter counter;
    private static PaKman game;
    
    public Bonus(PaKman game, String filename, int maxSteps, int maxScore){
        super(false, filename, 1);
        this.game = game;
        this.counter = new Counter(maxSteps,maxScore);
    }
    
    public void makeIteration(int score){
        counter.incrWithNum(score);
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
    
}
//EOF