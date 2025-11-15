package spritesAndCollisonDetection;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import gameLogic.Game;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;

import java.awt.Color;

/**
 * The spritesAndCollisonDetection.Paddle class represents the player's paddle in the game.
 * It is responsible for rendering the paddle, moving it based on user input,
 * and handling collisions with the ball.
 * Author: Afek Nuttman
 * Version: 14.7.2024
 */
public class Paddle implements Sprite, Collidable {
    private Rectangle rectangle; // The rectangle representing the paddle's shape and position
    private Color color; // The color of the paddle
    private KeyboardSensor keyboard; // The keyboard sensor to detect user input
    private int speed; // The speed at which the paddle moves

    // Constants defining the regions of the paddle
    private static final int NUM_REGIONS = 5;
    private static final double REGION_WIDTH;

    // Static block to initialize REGION_WIDTH
    static {
        REGION_WIDTH = (double) 80 / NUM_REGIONS;
    }

    /**
     * Constructs a spritesAndCollisonDetection.Paddle with the specified properties.
     *
     * @param rectangle the rectangle representing the paddle's shape and position.
     * @param color     the color of the paddle.
     * @param keyboard  the keyboard sensor to detect user input.
     * @param speed     the speed at which the paddle moves.
     */
    public Paddle(Rectangle rectangle, Color color, KeyboardSensor keyboard, int speed) {
        this.rectangle = rectangle;
        this.color = color;
        this.keyboard = keyboard;
        this.speed = speed;
    }

    /**
     * Moves the paddle to the left by the specified speed.
     * Ensures the paddle stays within the left boundary of the screen.
     */
    public void moveLeft() {
        double newX = rectangle.getUpperLeft().getX() - speed;
        // Ensure the paddle does not move out of the screen on the left
        if (newX > 5) {
            rectangle = new Rectangle(new Point(newX, rectangle.getUpperLeft().getY()),
                    rectangle.getWidth(), rectangle.getHeight());
        } else {
            // Wrap the paddle to the right side if it goes out of the left boundary
            rectangle = new Rectangle(new Point(795 - rectangle.getWidth(), rectangle.getUpperLeft().getY()),
                    rectangle.getWidth(), rectangle.getHeight());
        }
    }

    /**
     * Moves the paddle to the right by the specified speed.
     * Ensures the paddle stays within the right boundary of the screen.
     */
    public void moveRight() {
        double newX = rectangle.getUpperLeft().getX() + speed;
        // Ensure the paddle does not move out of the screen on the right
        if (newX + rectangle.getWidth() < 795) { // Assuming the game width is 800
            rectangle = new Rectangle(new Point(newX, rectangle.getUpperLeft().getY()),
                    rectangle.getWidth(), rectangle.getHeight());
        } else {
            // Wrap the paddle to the left side if it goes out of the right boundary
            rectangle = new Rectangle(new Point(5, rectangle.getUpperLeft().getY()),
                    rectangle.getWidth(), rectangle.getHeight());
        }
    }

    /**
     * Notifies the paddle that time has passed.
     * Moves the paddle left or right based on the user's key presses.
     */
    @Override
    public void timePassed() {
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft();
        }
        if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight();
        }
    }

    /**
     * Draws the paddle on the given DrawSurface.
     *
     * @param d the DrawSurface to draw the paddle on.
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        Point upperLeft = rectangle.getUpperLeft();
        d.fillRectangle((int) upperLeft.getX(), (int) upperLeft.getY(),
                (int) rectangle.getWidth(), (int) rectangle.getHeight());
    }

    /**
     * Returns the collision rectangle of the paddle.
     *
     * @return the rectangle representing the paddle's collision area.
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return this.rectangle;
    }

    /**
     * Handles the collision with the paddle.
     * Determines the new velocity of the ball based on the collision point.
     *
     * @param hitter          the ball that hit the paddle.
     * @param collisionPoint  the point where the collision occurred.
     * @param currentVelocity the current velocity of the ball.
     * @return the new velocity of the ball after the collision.
     */
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        // Calculate the region of the paddle where the collision occurred
        Line[] edges = rectangle.getEdges();
        Velocity newVelocity = currentVelocity;
        for (Line edge : edges) {
            if (edge.isOnLine(collisionPoint)) {
                if (edge.equals(new Line(rectangle.getUpperLeft(), new Point(rectangle.getUpperLeft().getX()
                        + rectangle.getWidth(), rectangle.getUpperLeft().getY() + rectangle.getHeight())))) {
                    int region = getCollisionRegion(collisionPoint);
                    double newAngle = calculateNewAngle(region);
                    if (newAngle == 0) {
                        newVelocity = new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
                    } else {
                        newVelocity = Velocity.fromAngleAndSpeed(newAngle, currentVelocity.getSpeed());
                    }
                } else {
                    // If the collision is with a vertical edge
                    if (edge.isVertical()) {
                        newVelocity.setDy(-newVelocity.getDy());
                    } else if (edge.isHorizontal()) {
                        newVelocity.setDx(-newVelocity.getDx());
                    }
                }
                break;
            }
        }
        // Ensure the ball always bounces upwards
        if (newVelocity.getDy() > 0) {
            newVelocity = new Velocity(newVelocity.getDx(), -newVelocity.getDy());
        }
        return newVelocity;
    }

    /**
     * Determines the collision region of the paddle based on the collision point.
     *
     * @param collisionPoint the point where the collision occurred.
     * @return the region number (1 to 5) where the collision occurred.
     */
    private int getCollisionRegion(Point collisionPoint) {
        // Calculate the x-coordinate relative to the paddle's position
        double relativeX = collisionPoint.getX() - this.rectangle.getUpperLeft().getX();
        // Determine the region based on the relative x-coordinate
        if (relativeX == REGION_WIDTH) {
            return (int) (relativeX / REGION_WIDTH);
        }
        return (int) (relativeX / REGION_WIDTH) + 1;
    }

    /**
     * Calculates the new angle for the ball's velocity based on the collision region.
     *
     * @param region the region number where the collision occurred.
     * @return the new angle for the ball's velocity.
     */
    private double calculateNewAngle(int region) {
        // Define the angles for each region
        double[] regionAngles = {300, 330, 0, 30, 60};

        // Get the angle for the specified region
        return regionAngles[region - 1];
    }

    /**
     * Adds the paddle to the game as both a sprite and a collidable object.
     *
     * @param g the game to add the paddle to.
     */
    public void addToGame(Game g) {
        g.addSprite(this);
        g.addCollidable(this);
    }
}
