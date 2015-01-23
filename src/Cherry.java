
public class Cherry extends Bonus{
    private boolean visible = false;
    
    public Cherry(PaKman game, int maxSteps, int maxScore) {
        super(game, "sprites/cherry.gif", maxSteps, maxScore);
    }
    
    public void countDown(){
        if (isVisible()){
            visible = false;
            super.counter.setStepMax(100);
        }else{
            visible = true;
            super.counter.setStepMax(20);
        }
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
