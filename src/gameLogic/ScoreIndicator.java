package gameLogic;

import biuoop.DrawSurface;
import geometry.Point;
import geometry.Rectangle;
import spritesAndCollisonDetection.Sprite;

import java.awt.Color;

/**
 * The gameLogic.ScoreIndicator class implements the spritesAndCollisonDetection.Sprite interface and is responsible
 * for displaying the current score on the screen.
 * It uses a rectangle to define the area where the score will be displayed and
 * updates the score information accordingly.
 */
public class ScoreIndicator implements Sprite {
    private Rectangle rectangle; // geometry.Rectangle defining the area for displaying the score
    private Counter countScore;   // gameLogic.Counter object to keep track of the current score

    /**
     * Constructs a gameLogic.ScoreIndicator with the specified rectangle and score counter.
     *
     * @param rectangle The rectangle that defines the area for displaying the score.
     * @param counter   The gameLogic.Counter object used to keep track of the score.
     */
    public ScoreIndicator(Rectangle rectangle, Counter counter) {
        this.rectangle = rectangle; // Initialize the rectangle
        this.countScore = counter;   // Initialize the score counter
    }

    /**
     * This method is called to update the state of the gameLogic.ScoreIndicator.
     * In this implementation, it does not perform any updates.
     * It is a placeholder method from the spritesAndCollisonDetection.Sprite interface.
     */
    @Override
    public void timePassed() {
        // No action needed for the score indicator on time passage
    }

    /**
     * Draws the score indicator on the provided DrawSurface.
     * It draws the rectangle that represents the score area and the current score
     * as text within that rectangle.
     * @param d The DrawSurface object where the score will be drawn.
     */
    @Override
    public void drawOn(DrawSurface d) {
        Point upperLeft = rectangle.getUpperLeft(); // Get the upper-left corner of the rectangle

        // Draw the rectangle for the score indicator
        d.setColor(Color.WHITE); // Set the color for the rectangle outline
        d.drawRectangle((int) upperLeft.getX(), (int) upperLeft.getY(),
                (int) rectangle.getWidth(), (int) rectangle.getHeight());

        // Draw the score text within the rectangle
        String countString = "Score: " + this.countScore.getValue(); // Format the score text
        d.setColor(Color.BLACK); // Set the color for the score text
        d.drawText(400, 10, countString, 11); // Draw the text at a specified position
    }

    /**
     * Adds the gameLogic.ScoreIndicator to the game as a sprite.
     * This method allows the gameLogic.ScoreIndicator to be rendered as part of the game's display.
     * @param g The game object to which the gameLogic.ScoreIndicator is added.
     */
    public void addToGame(Game g) {
        g.addSprite(this); // Add this gameLogic.ScoreIndicator instance to the game's list of sprites
    }
}
