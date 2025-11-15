package gameLogic;

import spritesAndCollisonDetection.Ball;
import spritesAndCollisonDetection.Block;

/**
 * The gameLogic.HitListener interface is used to define a listener that responds to hit events.
 * Classes implementing this interface will be notified when an object
 * (spritesAndCollisonDetection.Block) is hit by a spritesAndCollisonDetection.Ball.
 */
public interface HitListener {

    /**
     * This method is called whenever the specified spritesAndCollisonDetection.
     * Block object is hit by a spritesAndCollisonDetection.Ball.
     *
     * @param beingHit The spritesAndCollisonDetection.
     * Block that is being hit. This object represents the target of the hit.
     * @param hitter   The spritesAndCollisonDetection.
     * Ball that is causing the hit. This object represents the source of the hit.
     */
    void hitEvent(Block beingHit, Ball hitter);
}
