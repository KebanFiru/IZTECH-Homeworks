package app;

import controller.GameController;

/**
 * Main application class for the Sliding Penguins Puzzle Game.
 * Entry point for starting the game.
 */
public class SlidingPuzzleApp {
    /**
     * Main method - starts the game.
     * 
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        GameController controller = new GameController();
        controller.startGame();
    }
}