package gameLogic;

import geometry.Line;
import geometry.Point;
import spritesAndCollisonDetection.Collidable;
import spritesAndCollisonDetection.CollisionInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * The gameLogic.GameEnvironment class represents the environment in which the game takes place.
 * It handles the management of collidable objects and determines collision events.
 * Author: Afek Nuttman
 * Version: 14.7.2024
 */
public class GameEnvironment {
    private List<Collidable> collidables; // List of collidable objects in the game environment

    /**
     * Constructs a new gameLogic.GameEnvironment object, initializing the list of collidables.
     */
    public GameEnvironment() {
        this.collidables = new ArrayList<>();
    }

    /**
     * Adds a collidable object to the game environment.
     *
     * @param c The collidable object to add.
     */
    public void addCollidable(Collidable c) {
        this.collidables.add(c);
    }

    /**
     * Removes a collidable object from the game environment.
     *
     * @param c The collidable object to remove.
     */
    public void removeCollidable(Collidable c) {
        this.collidables.remove(c);
    }

    /**
     * Returns the list of collidable objects in the game environment.
     *
     * @return The list of collidable objects.
     */
    public List<Collidable> getCollidables() {
        return this.collidables;
    }

    /**
     * Gets the closest collision information along the given trajectory.
     *
     * @param trajectory The trajectory of the moving object.
     * @return The collision information of the closest collision, or null if no collision occurs.
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        CollisionInfo closestCollision = null;
        double closestDistance = Double.POSITIVE_INFINITY; // Initialize to a very large value
        for (Collidable c : this.collidables) {
            Point collisionPoint = trajectory.closestIntersectionToStartOfLine(c.getCollisionRectangle());
            if (collisionPoint != null) {
                double distance = trajectory.start().distance(collisionPoint);
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestCollision = new CollisionInfo(collisionPoint, c);
                }
            }
        }
        return closestCollision;
    }

    /**
     * Checks if a given point is inside any of the collidable blocks.
     *
     * @param point The point to check.
     * @return The collidable object if the point is inside a block, null otherwise.
     */
    public Collidable isInsideBlocks(Point point) {
        for (Collidable c : collidables) {
            if (c.getCollisionRectangle().contains(point)) {
                return c;
            }
        }
        return null;
    }

    /**
     * Checks if a given point is inside any of the collidable blocks.
     *
     * @param point The point to check.
     * @return true if the point is inside a block, false otherwise.
     */
    public boolean isInsideBlock(Point point) {
        for (Collidable c : collidables) {
            if (c.getCollisionRectangle().contains(point)) {
                return true;
            }
        }
        return false;
    }
}
