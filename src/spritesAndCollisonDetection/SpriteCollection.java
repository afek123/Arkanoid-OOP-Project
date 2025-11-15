package spritesAndCollisonDetection;
// 213459381 Afek Nuttman

import biuoop.DrawSurface;

import java.util.ArrayList;
import java.util.List;

/**
 * The spritesAndCollisonDetection.SpriteCollection class manages a collection of sprites that can be drawn
 * on a DrawSurface and updated over time.
 * Author: Afek Nuttman
 * Version: 14.7.2024
 */
public class SpriteCollection {
    private List<Sprite> sprites; // List to hold all sprites

    /**
     * Constructs an empty spritesAndCollisonDetection.SpriteCollection.
     */
    public SpriteCollection() {
        this.sprites = new ArrayList<>();
    }

    /**
     * Adds a sprite to the collection.
     *
     * @param s the sprite to add.
     */
    public void addSprite(Sprite s) {
        this.sprites.add(s);
    }

    /**
     * Removes a sprite from the collection.
     *
     * @param s the sprite to remove.
     */
    public void removeSprite(Sprite s) {
        sprites.remove(s);
    }

    /**
     * Draws all sprites in the collection on the given DrawSurface.
     *
     * @param d the DrawSurface on which to draw the sprites.
     */
    public void drawAllOn(DrawSurface d) {
        // Create a copy of the sprites list to avoid ConcurrentModificationException
        List<Sprite> spritesCopy = new ArrayList<>(this.sprites);
        for (Sprite s : spritesCopy) {
            s.drawOn(d);
        }
    }

    /**
     * Notifies all sprites in the collection that a unit of time has passed,
     * allowing them to update their state.
     */
    public void notifyAllTimePassed() {
        // Create a copy of the sprites list to avoid ConcurrentModificationException
        List<Sprite> spritesCopy = new ArrayList<>(this.sprites);
        for (Sprite s : spritesCopy) {
            s.timePassed();
        }
    }
}
