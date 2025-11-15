package gameLogic;

import spritesAndCollisonDetection.Ball;
import spritesAndCollisonDetection.Block;

/**
 * The gameLogic.ScoreTrackingListener class implements the gameLogic.HitListener interface and
 * listens for hit events to update the score based on interactions between
 * the ball and blocks.
 * It increases the score by 5 points when a block is hit by a ball,
 * provided the color of the ball does not match the color of the block.
 */
public class ScoreTrackingListener implements HitListener {
    private Counter currentScore; // gameLogic.Counter object to track the score

    /**
     * Constructs a gameLogic.ScoreTrackingListener with the given score counter.
     *
     * @param scoreCounter The gameLogic.Counter object used to keep track of the score.
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter; // Initialize the score counter
    }

    /**
     * Handles the event when a block is hit by a ball.
     * If the ball's color does not match the block's color, the score is increased
     * by 5 points.
     *
     * @param beingHit The block that is hit by the ball.
     * @param hitter   The ball that hits the block.
     */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        // Check if the ball color does not match the block color
        if (!beingHit.ballColorMatch(hitter)) {
            // Increase the score by 5 points if the colors do not match
            this.currentScore.increase(5);
        }
    }
}
