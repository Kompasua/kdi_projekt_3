/**
 * 
 */

/**
 * @author Anton Bubnov
 *
 */
public abstract class Bonus {
    private Counter stepsCounter;
    private Counter scoreCounter;
    private static PaKman game;
    
    public Bonus(PaKman game, int maxSteps, int maxScore){
        this.game = game;
        this.stepsCounter = new Counter(maxSteps,0);
        this.scoreCounter = new Counter(maxScore,0);
    }
    
    public void makeIteration(int score){
        stepsCounter.increment();
        scoreCounter.incrWithNum(score);
    }
    
    public void updateBonus(int score){
        if(stepsCounter.checkIfMax() && scoreCounter.checkIfMax()){
            setOnLocation();
            stepsCounter.resetToMin();
            scoreCounter.resetToMin();
        }else{
            stepsCounter.increment();
            scoreCounter.incrWithNum(score);
        }
    }
    
    public void setOnLocation(){
        
    }
    
    public void remove(){
        
    }
}
//EOF