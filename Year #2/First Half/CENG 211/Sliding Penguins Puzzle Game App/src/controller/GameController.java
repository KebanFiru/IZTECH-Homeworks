package controller;

import terrain.IcyTerrain;

public class GameController {
    private IcyTerrain terrain;

    public GameController() {
        this.terrain = new IcyTerrain();
    }

    public void startGame() {
        terrain.startGame();
    }
}