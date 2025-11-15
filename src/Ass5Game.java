import gameLogic.Game;

/**
 * The Ass5Game class is the entry point for the game application.
 * It creates a new game instance, initializes it, and starts the game loop.
 * Author: Afek Nuttman
 * Version: 14.7.2024
 */
public class Ass5Game {
    /**
     * The main method is the entry point of the application.
     * It creates and runs the game.
     *
     * @param args command line arguments (not used).
     */
    public static void main(String[] args) {
        // Create a new gameLogic.Game instance
        Game game = new Game();

        // Initialize the game (setting up game elements, loading resources, etc.)
        game.initialize();

        // Run the game (starting the game loop)
        game.run();
    }
}
