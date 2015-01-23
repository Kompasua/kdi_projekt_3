/**
 * @author Anton Bubnov
 *
 */
public class Counter {
    private final int MAX_VALUE; //Maximal value of counter
    private int curValue; //Current value of counter
    
    public Counter(int maxValue, int curValue) {
        this.MAX_VALUE = maxValue;
        this.curValue = curValue;
    }
    
    /**
     * Reset current value of counter to default max. value.
     */
    public void resetToMax(){
        this.curValue = MAX_VALUE;
    }
    
    /**
     * Reset current value of counter to default 0 value.
     */
    public void resetToMin(){
        this.curValue = 0;
    }
    
    /**
     * Check if the counter is in the end. 
     * @return true if counter is 0, else false.
     */
    public boolean checkIfMin(){
        return curValue <= 0 ? true : false;
    }
    
    /**
     * Check if the counter is in the end. 
     * @return true if counter is in maximum value, else false.
     */
    public boolean checkIfMax(){
        return curValue >= MAX_VALUE ? true : false;
    }
    
    /**
     * Decrease current value on 1.
     */
    public void decrement(){
        curValue--;
    }
    
    /**
     * Increase current value on 1.
     */
    public void increment(){
        curValue++;
    }
    
    /**
     * Increase current value with given value.
     */
    public void incrWithNum(int num){
        curValue += num;
    }
    
}
//EOF