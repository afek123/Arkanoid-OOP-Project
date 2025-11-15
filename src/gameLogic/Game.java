package gameLogic;
//213459381 Afek Nuttman

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import biuoop.Sleeper;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;
import spritesAndCollisonDetection.Ball;
import spritesAndCollisonDetection.Block;
import spritesAndCollisonDetection.Collidable;
import spritesAndCollisonDetection.Paddle;
import spritesAndCollisonDetection.Sprite;
import spritesAndCollisonDetection.SpriteCollection;

import java.awt.Color;

/**
 * The gameLogic.Game class represents the Arkanoid game, handling game initialization,
 * game loop, and interactions between game elements such as sprites and collidables.
 * It sets up the game environment, manages the game state, and updates the display.
 * Author: Afek Nuttman
 * Version: 14.7.2024
 */
public class Game {

    private SpriteCollection sprites;      // Collection of all sprites in the game
    private GameEnvironment environment;    // Environment containing all collidables
    private GUI gui;                        // The graphical user interface
    private KeyboardSensor keyboard;        // Keyboard sensor for user input
    private static final int SCREEN_WIDTH = 800;  // Width of the game screen
    private static final int SCREEN_HEIGHT = 600; // Height of the game screen
    private Counter remainingBlocks;        // gameLogic.Counter for remaining blocks in the game
    private Counter remainingBalls;         // gameLogic.Counter for remaining balls in the game
    private Counter scoreIndicator;         // gameLogic.Counter for the player's score
    private static final Color[] ROW_COLORS = {
            Color.GRAY, Color.RED, Color.YELLOW, Color.BLUE, Color.PINK, Color.GREEN, Color.ORANGE
    };  // Colors used for the blocks

    /**
     * Constructs a new gameLogic.Game object, initializing the game environment, GUI,
     * keyboard sensor, and counters.
     */
    public Game() {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.gui = new GUI("Arkanoid", SCREEN_WIDTH, SCREEN_HEIGHT);
        this.keyboard = gui.getKeyboardSensor();
        this.remainingBlocks = new Counter(0); // Initialize the block counter with 0
        this.remainingBalls = new Counter(0);  // Initialize the ball counter with 0
        this.scoreIndicator = new Counter(0); // Initialize the score counter with 0
    }

    /**
     * Adds a collidable object to the game environment.
     *
     * @param c The collidable object to be added to the environment.
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * Removes a collidable object from the game environment.
     *
     * @param c The collidable object to be removed from the environment.
     */
    public void removeCollidable(Collidable c) {
        environment.removeCollidable(c);
    }

    /**
     * Removes a sprite from the sprite collection.
     *
     * @param s The sprite to be removed from the collection.
     */
    public void removeSprite(Sprite s) {
        sprites.removeSprite(s);
    }

    /**
     * Adds a sprite to the sprite collection.
     *
     * @param s The sprite to be added to the collection.
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * Initializes the game by setting up blocks, paddle, balls, and adding them to the game environment.
     * This method also sets up listeners for game events and initializes counters.
     */
    public void initialize() {
        // Create and add border blocks
        BlockRemover blockRemover = new BlockRemover(this, remainingBlocks);
        Block topBorder = new Block(new Rectangle(new Point(0, 20), SCREEN_WIDTH, 5), Color.GRAY);
        Block bottomBorder = new Block(new Rectangle(new Point(0, SCREEN_HEIGHT - 5), SCREEN_WIDTH, 5), Color.WHITE);
        Block leftBorder = new Block(new Rectangle(new Point(0, 25), 5, SCREEN_HEIGHT - 30), Color.GRAY);
        Block rightBorder = new Block(new Rectangle(new Point(SCREEN_WIDTH - 5, 25),
                5, SCREEN_HEIGHT - 30), Color.GRAY);
        BallRemover ballRemover = new BallRemover(this, remainingBalls); // Handles ball removal when needed
        ScoreTrackingListener scoreIndic = new ScoreTrackingListener(scoreIndicator);

        // Add border blocks to the game
        topBorder.addToGame(this);
        bottomBorder.addToGame(this);
        leftBorder.addToGame(this);
        rightBorder.addToGame(this);
        bottomBorder.addHitListener(ballRemover); // Add listener to detect ball removal
        remainingBalls.increase(3); // Initialize with 3 balls

        // Create and add game blocks
        for (int i = 0; i <= 5; i++) {
            for (int j = 0; j < 12 - i; j++) {
                Rectangle rect = new Rectangle(new Point(740 - j * 55, 100 + (i + 1) * 30), 55, 30);
                Block block = new Block(rect, ROW_COLORS[i]);
                block.addToGame(this);
                block.addHitListener(blockRemover); // Add listener to handle block removal
                block.addHitListener(scoreIndic);    // Add listener to update score
                remainingBlocks.increase(1); // Increment the counter for each block added
            }
        }

        // Create and add paddle
        Rectangle paddleRect = new Rectangle(new Point(350, 575), 80, 20);
        Paddle paddle = new Paddle(paddleRect, ROW_COLORS[ROW_COLORS.length - 1], keyboard, 10);
        paddle.addToGame(this);

        // Create and add score indicator
        Rectangle scoreRect = new Rectangle(new Point(0, 0), 800, 20);
        ScoreIndicator scoreIndicator1 = new ScoreIndicator(scoreRect, scoreIndicator);
        scoreIndicator1.addToGame(this);

        // Create and add balls
        Ball ball = new Ball(new Point(200, 300), 5, Color.GRAY);
        ball.setVelocity(Velocity.fromAngleAndSpeed(45, 4));
        ball.setGameEnvironment(environment);
        ball.addToGame(this);

        Ball ball1 = new Ball(new Point(300, 300), 5, Color.GREEN);
        ball1.setVelocity(Velocity.fromAngleAndSpeed(45, 3));
        ball1.setGameEnvironment(environment);
        ball1.addToGame(this);

        Ball ball2 = new Ball(new Point(250, 300), 5, Color.RED);
        ball2.setVelocity(Velocity.fromAngleAndSpeed(45, 5));
        ball2.setGameEnvironment(environment);
        ball2.addToGame(this);
    }

    /**
     * Runs the game loop, which repeatedly draws the current state of the game on the GUI,
     * updates the state of the sprites, and handles game termination conditions.
     */
    public void run() {
        Sleeper sleeper = new Sleeper();
        int framesPerSecond = 60;
        int millisecondsPerFrame = 1000 / framesPerSecond;

        while (true) {
            long startTime = System.currentTimeMillis();
            DrawSurface d = gui.getDrawSurface();

            // Draw all sprites
            this.sprites.drawAllOn(d);
            gui.show(d);

            // Notify all sprites that time has passed
            this.sprites.notifyAllTimePassed();

            // Check game termination conditions
            if (remainingBlocks.getValue() < 0 || remainingBalls.getValue() <= 0) {
                sleeper.sleepFor(200);
                gui.close();
                return; // Exit the game loop when no more blocks or balls are available
            }
            if (remainingBlocks.getValue() == 0) {
                scoreIndicator.increase(100); // Add bonus points for clearing all blocks
                remainingBlocks.decrease(1);  // Ensure the loop can exit
            }
            System.out.println(scoreIndicator.getValue()); // Print the current score to the console

            // Calculate and sleep for the remaining time to maintain the frame rate
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }
}
