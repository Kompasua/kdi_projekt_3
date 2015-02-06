/**
 * This class is used to create bonus with type mine in game. All bonuses
 * appears, existing and disappear due to some rules. During the game, after
 * every 20 Points that player earned, on the map appears a mine. Mine stays on
 * grid only for 30 iterations. 
 * 
 * @author Anton Bubnov
 * @version 06.02.15
 * 
 */

public class Mine extends Bonus {

    public Mine(PaKman game, int maxSteps, int maxScore, int stepsToLive) {
        super(game, "sprites/mine.gif", maxSteps, maxScore, stepsToLive, 2);
    }
}
