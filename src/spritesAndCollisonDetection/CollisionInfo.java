package spritesAndCollisonDetection;

import geometry.Point;

/**
 * The spritesAndCollisonDetection.CollisionInfo class holds information about a collision event in the game.
 * It stores the point of collision and the collidable object involved in the collision.
 * Author: Afek Nuttman
 * Version: 14.7.2024
 */
public class CollisionInfo {
    private Point collisionPoint; // The point where the collision occurred
    private Collidable collisionObject; // The object involved in the collision

    /**
     * Constructs a spritesAndCollisonDetection.CollisionInfo
     * object with the given collision point and collidable object.
     *
     * @param collisionPoint  The point at which the collision occurred.
     * @param collisionObject The collidable object involved in the collision.
     */
    public CollisionInfo(Point collisionPoint, Collidable collisionObject) {
        this.collisionPoint = collisionPoint;
        this.collisionObject = collisionObject;
    }

    /**
     * Returns the point at which the collision occurred.
     *
     * @return The collision point.
     */
    public Point collisionPoint() {
        return collisionPoint;
    }

    /**
     * Returns the collidable object involved in the collision.
     *
     * @return The collidable object.
     */
    public Collidable collisionObject() {
        return collisionObject;
    }
}
