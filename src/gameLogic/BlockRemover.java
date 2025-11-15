package gameLogic;

import spritesAndCollisonDetection.Ball;
import spritesAndCollisonDetection.Block;

/**
 * The gameLogic.BlockRemover class is responsible for removing blocks from the game when they are hit.
 * It also keeps track of the number of remaining blocks.
 * Implements the gameLogic.HitListener interface to respond to hit events on blocks.
 */
public class BlockRemover implements HitListener {
    private Game game;
    private Counter remainingBlocks;

    /**
     * Constructs a new gameLogic.BlockRemover with the specified game and counter for remaining blocks.
     *
     * @param game            the game instance to remove blocks from
     * @param remainingBlocks the counter that tracks the number of remaining blocks
     */
    public BlockRemover(Game game, Counter remainingBlocks) {
        this.game = game;
        this.remainingBlocks = remainingBlocks;
    }

    /**
     * Called when a block is hit by a ball.
     * If the color of the ball does not match the block's color, the block is removed
     * from the game, and the count of remaining blocks is decremented.
     * Also removes this gameLogic.BlockRemover as a listener from the block being removed.
     *
     * @param beingHit the block that is being hit
     * @param hitter   the ball that is hitting the block
     */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        // Check if the ball's color does not match the block's color
        if (!beingHit.ballColorMatch(hitter)) {
            // Remove this gameLogic.BlockRemover from the block's list of hit listeners
            beingHit.removeHitListener(this);
            // Remove the block from the game
            beingHit.removeFromGame(this.game);
            // Decrease the count of remaining blocks
            this.remainingBlocks.decrease(1);
        }
    }
}
