/**
 * This class is used to create bonus with type cherry in game. All bonuses
 * appears, existing and disappear due to some rules. Cherry will appear on a
 * fixed position in the board, when player has at least 50 points and at least
 * a 100 moves have passed since the last time a cherry was visible. This cherry
 * will be visible for 20 moves, or until the player eats the cherry, whichever
 * happens first. When the player eats the cherry, hunting/fleeing mode is
 * toggled. After 40 moves, hunting/fleeing mode toggles back again.
 * 
 * @author Anton Bubnov
 * @version 06.02.15
 * 
 */

public class Cherry extends Bonus {

    public Cherry(PaKman game, int maxSteps, int maxScore, int stepsToLive) {
        super(game, "sprites/cherry.gif", maxSteps, maxScore, stepsToLive, 1);
    }

}
