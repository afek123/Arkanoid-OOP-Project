package spritesAndCollisonDetection;

import biuoop.DrawSurface;

/**
 * The spritesAndCollisonDetection.Sprite interface represents an object that can be drawn on a DrawSurface
 * and can update its state over time.
 * Author: Afek Nuttman
 * Version: 14.7.2024
 */
public interface Sprite {
    /**
     * Draws the sprite on the given DrawSurface.
     *
     * @param d the DrawSurface on which to draw the sprite.
     */
    void drawOn(DrawSurface d);

    /**
     * Notifies the sprite that a unit of time has passed, allowing it to update its state.
     */
    void timePassed();
}
