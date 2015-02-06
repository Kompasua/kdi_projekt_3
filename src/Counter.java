/**
 * This class create counter. It can be used for counting steps and score. It
 * counts from 0 to maximal given value.
 * 
 * @author Anton Bubnov
 * @version 06.02.15
 */
public class Counter {
    private int stepMaxValue; // Limit steps value
    private int scoreMaxValue; // Limit score value
    private int stepCurValue; // Current steps number
    private int scoreCurValue; // Current score value

    /**
     * Initialize and save main counter parameters.
     * 
     * @param stepMaxValue
     *            Limit steps value
     * @param scoreMaxValue
     *            Limit score value
     */
    public Counter(int stepMaxValue, int scoreMaxValue) {
        this.stepMaxValue = stepMaxValue;
        this.scoreMaxValue = scoreMaxValue;
        this.stepCurValue = 0;
        this.scoreCurValue = 0;
    }

    /**
     * 
     * @param num
     *            set limit steps value
     */
    public void setStepMax(int num) {
        this.stepMaxValue = num;
    }

    /**
     * @return the stepMaxValue
     */
    public int getStepMaxValue() {
        return stepMaxValue;
    }

    /**
     * Reset current value of counter to default minimal value.
     */
    public void reset() {
        this.stepCurValue = 0;
        this.scoreCurValue = 0;
    }

    /**
     * Check if steps is in or more then limit value.
     * 
     * @return true if counter is in maximum value, else false.
     */
    public boolean checkStepValue() {
        return stepCurValue >= stepMaxValue ? true : false;
    }

    /**
     * Check if score number is in or more then limit value.
     * 
     * @return true if counter is in maximum value, else false.
     */
    public boolean checkScoreValue() {
        return scoreCurValue >= scoreMaxValue ? true : false;
    }

    /**
     * @return the stepCurValue
     */
    public int getStepCurValue() {
        return stepCurValue;
    }

    /**
     * @param stepCurValue
     *            the stepCurValue to set
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
     * @param scoreCurValue
     *            the scoreCurValue to set
     */
    public void setScoreCurValue(int scoreCurValue) {
        this.scoreCurValue = scoreCurValue;
    }

    /**
     * Increase current steps number on 1.
     */
    public void iterate() {
        stepCurValue++;
    }

    /**
     * Increase current score value with given value.
     */
    public void incrWithNum(int num) {
        scoreCurValue += num;
    }

}
// EOF