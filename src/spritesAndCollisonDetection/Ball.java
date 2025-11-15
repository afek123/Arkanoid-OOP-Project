package spritesAndCollisonDetection;

import biuoop.DrawSurface;
import gameLogic.Game;
import gameLogic.GameEnvironment;
import geometry.Line;
import geometry.Point;
import geometry.Velocity;

import java.awt.Color;

/**
 * The spritesAndCollisonDetection.Ball class represents a ball in the game
 * implementing the spritesAndCollisonDetection.Sprite interface.
 * Author: Afek Nuttman
 * Version: 14.7.2024
 */
public class Ball implements Sprite {
    private Point center;
    private int radius;
    private Color color;
    private Velocity velocity;
    private GameEnvironment gameEnvironment;

    /**
     * Constructs a new spritesAndCollisonDetection.Ball with a given center point, radius, and color.
     *
     * @param center The center point of the ball.
     * @param radius The radius of the ball.
     * @param color  The color of the ball.
     */
    public Ball(Point center, int radius, Color color) {
        this.center = center;
        this.radius = radius;
        this.color = color;
    }

    /**
     * Gets the color of the ball.
     *
     * @return The color.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Gets the radius (size) of the ball.
     *
     * @return The radius.
     */
    public int getSize() {
        return this.radius;
    }

    /**
     * Sets the color of the ball.
     *
     * @param color The new color.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Sets the velocity of the ball.
     *
     * @param velocity The new velocity.
     */
    public void setVelocity(Velocity velocity) {
        this.velocity = velocity;
    }

    /**
     * Sets the game environment for the ball, where it interacts with other collidable objects.
     *
     * @param gameEnvironment The game environment.
     */
    public void setGameEnvironment(GameEnvironment gameEnvironment) {
        this.gameEnvironment = gameEnvironment;
    }

    /**
     * Gets the center point of the ball.
     *
     * @return The center point.
     */
    public Point getCenter() {
        return this.center;
    }

    /**
     * Moves the ball one step based on its velocity and handles collisions with other objects.
     */
    public void moveOneStep() {
        // Create a trajectory line from the current position to the next position
        Line trajectory = new Line(center, velocity.applyToPoint(center));
        CollisionInfo collisionInfo = gameEnvironment.getClosestCollision(trajectory);

        if (collisionInfo != null) {
            Point collisionPoint = collisionInfo.collisionPoint();
            Collidable collisionObject = collisionInfo.collisionObject();

            // Move the ball to just before the collision point
            double adjustX = collisionPoint.getX() - 0.1 * this.velocity.getDx();
            double adjustY = collisionPoint.getY() - 0.1 * this.velocity.getDy();
            this.center = new Point(adjustX, adjustY);

            // Update velocity based on the collision
            this.velocity = collisionObject.hit(this, collisionPoint, this.velocity);
        } else {
            // Check if the ball is inside any blocks and adjust position accordingly
            Collidable c = gameEnvironment.isInsideBlocks(this.center);
            if (c != null) {
                this.center = new Point(center.getX(),
                        c.getCollisionRectangle().getUpperLeft().getY() - this.radius - 1);
            }
            while (gameEnvironment.isInsideBlock(this.center)) {
                this.center = this.velocity.applyToPoint(this.center);
            }
            // Move the ball according to its velocity
            this.center = this.velocity.applyToPoint(this.center);
        }
    }

    /**
     * Draws the ball on the given DrawSurface.
     *
     * @param d The DrawSurface to draw the ball on.
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.fillCircle((int) center.getX(), (int) center.getY(), radius);
    }

    /**
     * Removes the ball from the game.
     *
     * @param g The game to remove the ball from.
     */
    public void removeFromGame(Game g) {
        g.removeSprite(this);
    }

    /**
     * Notifies the ball that time has passed and it should move.
     */
    @Override
    public void timePassed() {
        moveOneStep();
    }

    /**
     * Adds the ball to the game as a sprite.
     *
     * @param g The game to add the ball to.
     */
    public void addToGame(Game g) {
        g.addSprite(this);
    }
}
