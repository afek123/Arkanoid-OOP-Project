package spritesAndCollisonDetection;

import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;

/**
 * The spritesAndCollisonDetection.Collidable interface represents objects that can be collided with in the game.
 * Author: Afek Nuttman
 * Version: 14.7.2024
 */
public interface Collidable {

    /**
     * Returns the "collision shape" of the object.
     * This method provides the rectangular shape that represents the collidable object.
     *
     * @return The rectangle representing the shape of the collidable object.
     */
    Rectangle getCollisionRectangle();

    /**
     * Notifies the object that a collision occurred at a specific point with a given velocity.
     * Adjusts the velocity of the object after the hit based on the collision point.
     *
     * @param hitter          The ball that hit the collidable object.
     * @param collisionPoint  The point at which the collision occurred.
     * @param currentVelocity The current velocity of the colliding object.
     * @return The new velocity after the hit.
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}
