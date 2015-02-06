/**
 * This class is used to create bonus with type pineapple in game. All bonuses
 * appears, existing and disappear due to some rules. Pineapple appears on fixed
 * position on the board, when player pressed 'g'. This pineapple will be
 * visible for 40 iteration, or until the player eats the pineapple, whichever
 * happens first.
 * 
 * @author Anton Bubnov
 * @version 06.02.15
 * 
 */

public class PineApple extends Bonus {

    public PineApple(PaKman game, int maxSteps, int maxScore, int stepsToLive) {
        super(game, "sprites/pineapple.gif", maxSteps, maxScore, stepsToLive, 1);
    }

}
