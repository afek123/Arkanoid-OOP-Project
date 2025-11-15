package gameLogic;

import spritesAndCollisonDetection.Ball;
import spritesAndCollisonDetection.Block;

/**
 * The gameLogic.BallRemover class handles the removal of balls from the game when they collide with blocks.
 * It also manages the count of remaining balls in the game.
 * Implements the gameLogic.HitListener interface to respond to hit events involving balls.
 */
public class BallRemover implements HitListener {
    private Game game;
    private Counter remainingBalls;

    /**
     * Constructs a new gameLogic.BallRemover with the specified game and counter for remaining balls.
     *
     * @param game           the game instance to remove balls from
     * @param remainingBalls the counter that tracks the number of remaining balls
     */
    public BallRemover(Game game, Counter remainingBalls) {
        this.game = game;
        this.remainingBalls = remainingBalls;
    }

    /**
     * Called when a ball hits a block.
     * The ball is removed from the game, and the count of remaining balls is decremented.
     * This method assumes that the spritesAndCollisonDetection.Ball class has a method to remove itself from the game.
     *
     * @param beingHit the block that is being hit by the ball
     * @param hitter   the ball that is hitting the block
     */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        // Remove the ball from the game
        hitter.removeFromGame(game);
        // Decrease the count of remaining balls
        remainingBalls.decrease(1);
    }
}
