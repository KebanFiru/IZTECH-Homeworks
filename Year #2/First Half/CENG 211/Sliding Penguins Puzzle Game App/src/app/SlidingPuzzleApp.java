package app;

import controller.GameController;

public class SlidingPuzzleApp {
    public static void main(String[] args) {
        GameController controller = new GameController();
        controller.startGame();
    }
}