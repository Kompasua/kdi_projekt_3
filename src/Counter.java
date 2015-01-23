/**
 * @author Anton Bubnov
 *
 */
public class Counter {
    private int stepMaxValue;
    private int scoreMaxValue; 
    private int stepCurValue;
    private int scoreCurValue;
    
    
    
    public Counter(int stepMaxValue, int scoreMaxValue) {
        this.stepMaxValue = stepMaxValue;
        this.scoreMaxValue = scoreMaxValue;
        this.stepCurValue = 0;
        this.scoreCurValue = 0;
    }
    
    public void setStepMax(int num){
        this.stepMaxValue = num;
    }

    /**
     * Reset current value of counter to default max. value.
     */
    public void reset(){
        this.stepCurValue = 0;
        this.scoreCurValue = 0;
    }
    
    /**
     * Check if steps is in the end. 
     * @return true if counter is in maximum value, else false.
     */
    public boolean checkStepValue(){
        return stepCurValue >= stepMaxValue ? true : false;
    }
    
    public boolean checkScoreValue(){
        return scoreCurValue >= scoreMaxValue ? true : false;
    }

    /**
     * Increase current value on 1.
     */
    public void increment(){
        scoreCurValue++;
        stepCurValue++;
    }
    
    /**
     * @return the stepCurValue
     */
    public int getStepCurValue() {
        return stepCurValue;
    }

    /**
     * @param stepCurValue the stepCurValue to set
     */
    public void setStepCurValue(int stepCurValue) {
        this.stepCurValue = stepCurValue;
    }

    /**
     * @return the scoreCurValue
     */
    public int getScoreCurValue() {
        return scoreCurValue;
    }

    /**
     * @param scoreCurValue the scoreCurValue to set
     */
    public void setScoreCurValue(int scoreCurValue) {
        this.scoreCurValue = scoreCurValue;
    }

    /**
     * Increase current value with given value.
     */
    public void incrWithNum(int num){
        stepCurValue++;
        scoreCurValue += num;
    }
    
}
//EOF