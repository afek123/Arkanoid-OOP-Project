//213459381 Afek Nuttman
package spritesAndCollisonDetection;

import biuoop.DrawSurface;
import gameLogic.Game;
import gameLogic.HitListener;
import gameLogic.HitNotifier;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * The spritesAndCollisonDetection.Block class represents a block in the game
 * implementing both spritesAndCollisonDetection.Collidable and spritesAndCollisonDetection.Sprite interfaces.
 * Author: Afek Nuttman
 * Version: 14.7.2024
 */
public class Block implements Collidable, Sprite, HitNotifier {
    private List<HitListener> hitListeners;
    private static final double THRESHOLD = 1e-10; // A small threshold for comparing doubles
    private Rectangle rectangle;
    private Color color;

    /**
     * Constructs a new spritesAndCollisonDetection.Block with a given rectangle shape and color.
     *
     * @param rectangle The rectangle shape of the block.
     * @param color     The color of the block.
     */
    public Block(Rectangle rectangle, Color color) {
        this.rectangle = rectangle;
        this.color = color;
        this.hitListeners = new ArrayList<>();
    }

    /**
     * Returns the "collision shape" of the block.
     *
     * @return The rectangle representing the block.
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return this.rectangle;
    }

    /**
     * Gets the color of the block.
     *
     * @return The color of the block.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the color of the block.
     *
     * @param color The new color of the block.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Checks if the color of the ball matches the color of the block.
     *
     * @param ball The ball to check against.
     * @return True if the ball's color matches the block's color, false otherwise.
     */
    public boolean ballColorMatch(Ball ball) {
        return ball.getColor().equals(this.color);
    }

    /**
     * Removes the block from the game.
     *
     * @param game The game from which to remove the block.
     */
    public void removeFromGame(Game game) {
        game.removeCollidable(this);
        game.removeSprite(this);
    }

    /**
     * Changes the color of the block.
     *
     * @param newColor The new color of the block.
     */
    public void changeColor(Color newColor) {
        this.color = newColor;
    }

    /**
     * Notifies all registered listeners that a hit event has occurred.
     *
     * @param hitter The ball that hit the block.
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    /**
     * Checks if two numbers are approximately equal within a small threshold.
     *
     * @param num1 The first number.
     * @param num2 The second number.
     * @return True if the numbers are approximately equal, false otherwise.
     */
    private boolean almostEqual(double num1, double num2) {
        return Math.abs(num1 - num2) < THRESHOLD;
    }

    /**
     * Checks if the collision point is not on the edges of the screen.
     *
     * @param collisionPoint The collision point to check.
     * @return True if the collision point is on the edges, false otherwise.
     */
    public boolean checkNotOnEdges(Point collisionPoint) {
        if (almostEqual(collisionPoint.getY(), 25) || almostEqual(collisionPoint.getY(), 595)
                || almostEqual(collisionPoint.getX(), 5) || almostEqual(collisionPoint.getX(), 795)) {
            return true;
        }
        return false;
    }

    /**
     * Notifies the block that a collision occurred at a specific point with a given velocity.
     * Adjusts the velocity after the hit.
     *
     * @param hitter           The ball that hit the block.
     * @param collisionPoint   The point at which the collision occurred.
     * @param currentVelocity  The current velocity of the ball.
     * @return The new velocity after the hit.
     */
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        // Check if the collision point is not on the edges and if the ball's color doesn't match the block's color
        if (!checkNotOnEdges(collisionPoint) && !ballColorMatch(hitter)) {
            // Swap colors between the ball and the block
            Color originalColor = this.color;
            this.color = hitter.getColor();
            hitter.setColor(originalColor);
            this.notifyHit(hitter);
        }
//        if (almostEqual(collisionPoint.getY(), 595)) {
//            this.notifyHit(hitter);
//        }
        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();

        // Get the edges of the block
        Line[] edges = rectangle.getEdges();

        // Check which edge was hit and adjust velocity accordingly
        for (Line edge : edges) {
            if (edge.isOnLine(collisionPoint)) {
                if (edge.isVertical()) {
                    dy = -dy;  // Reverse vertical direction
                } else if (edge.isHorizontal()) {
                    dx = -dx;  // Reverse horizontal direction
                }
                break;
            }
        }

        return new Velocity(dx, dy);
    }

    /**
     * Draws the block on the given DrawSurface.
     *
     * @param d The DrawSurface to draw the block on.
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        Point upperLeft = rectangle.getUpperLeft();
        d.fillRectangle((int) upperLeft.getX(), (int) upperLeft.getY(),
                (int) rectangle.getWidth(), (int) rectangle.getHeight());
        if (upperLeft.getY() != 595) {
            d.setColor(Color.BLACK);
            d.drawRectangle((int) upperLeft.getX(), (int) upperLeft.getY(),
                    (int) rectangle.getWidth(), (int) rectangle.getHeight());
        }
    }

    /**
     * Notifies the block that time has passed.
     * Currently, blocks do not perform any action when time passes.
     */
    @Override
    public void timePassed() {
        // No actions needed for blocks on time passed
    }

    /**
     * Adds the block to the game, registering it as both a sprite and a collidable.
     *
     * @param g The game to add the block to.
     */
    public void addToGame(Game g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     * Adds a listener to be notified of hit events.
     *
     * @param hl The listener to add.
     */
    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    /**
     * Removes a listener from the list of listeners to be notified of hit events.
     *
     * @param hl The listener to remove.
     */
    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }
}
