package controller;

import terrain.IcyTerrain;
import terrain. Direction;
import penguins.Penguin;
import penguins.PenguinType;
import hazards.Hazard;
import food.FoodItem;
import model.ITerrainObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Controls game flow, setup, user input, display, and turn management.
 */
public class GameController {
    private IcyTerrain terrain;
    private Penguin playerPenguin;
    private Scanner scanner;
    private int currentTurn;

    public GameController() {
        terrain = new IcyTerrain();
        scanner = new Scanner(System.in);
        currentTurn = 1;
    }

    public void startGame() {
        System.out.println("Welcome to Sliding Penguins Puzzle Game App.  A 10x10 icy terrain grid is being generated.");
        System.out. println("Penguins, Hazards, and Food items are being generated. The initial icy terrain grid:");

        initializeGame();
        displayGrid();
        displayPenguinInfo();

        for (currentTurn = 1; currentTurn <= 4; currentTurn++) {
            for (Penguin penguin : terrain.getPenguins()) {
                if (! penguin.isRemoved()) {
                    if (penguin.isStunned()) {
                        System. out.println("\n*** Turn " + currentTurn + " - " + penguin.getName() + ":");
                        System.out.println(penguin.getName() + " is stunned and skips this turn.");
                        penguin.setStunned(false);
                        displayGrid();
                        continue;
                    }
                    processPenguinTurn(penguin);
                }
            }
        }

        displayGameOver();
        scanner.close();
    }

    private void initializeGame() {
        generatePenguins();
        for (int i = 0; i < 15; i++) {
            placeHazard();
        }
        for (int i = 0; i < 20; i++) {
            placeFood();
        }
    }

    private void generatePenguins() {
        String[] names = {"P1", "P2", "P3"};
        List<int[]> edgePositions = terrain.getAvailableEdgePositions();

        for (int i = 0; i < 3; i++) {
            PenguinType type = PenguinType.getRandom();
            Penguin penguin = terrain.createPenguin(names[i], type);
            int[] pos = edgePositions.remove((int) (Math.random() * edgePositions.size()));
            penguin.setPosition(pos[0], pos[1]);
            terrain.placeObject(penguin, pos[0], pos[1]);
            terrain.addPenguin(penguin);
        }
        playerPenguin = terrain.getPenguins().get((int) (Math.random() * 3));
    }

    private void placeHazard() {
        Hazard hazard = Hazard.getRandomHazard();
        int[] pos = terrain.findEmptyPosition(true);
        if (pos != null) {
            hazard.setPosition(pos[0], pos[1]);
            terrain.placeObject(hazard, pos[0], pos[1]);
            terrain.addHazard(hazard);
        }
    }

    private void placeFood() {
        FoodItem food = new FoodItem();
        int[] pos = terrain. findEmptyPosition(false);
        if (pos != null) {
            food.setPosition(pos[0], pos[1]);
            terrain.placeObject(food, pos[0], pos[1]);
            terrain.addFoodItem(food);
        }
    }

    private void processPenguinTurn(Penguin penguin) {
        System.out.println("\n*** Turn " + currentTurn + " - " + penguin.getName() +
                (penguin == playerPenguin ?  " (Your Penguin):" : ":"));

        boolean useSpecial = false;
        Direction moveDir;

        if (penguin == playerPenguin) {
            if (!penguin.hasUsedSpecialAction()) {
                useSpecial = askPlayerSpecialAction();
            }
            moveDir = askPlayerDirection("Which direction will " + penguin.getName() + " move?  ");
        } else {
            if (!penguin.hasUsedSpecialAction()) {
                useSpecial = Math.random() < 0.3;
            }
            moveDir = chooseAIDirection(penguin);
        }

        System.out.println(penguin.getName() + (useSpecial ? " uses SPECIAL action" : " slides normally") +
                " " + getDirectionName(moveDir) + ".");
        if (useSpecial) {
            penguin.useSpecialAbility(moveDir, terrain);
        } else {
            penguin.move(moveDir, terrain);
        }
        displayGrid();
    }

    private boolean askPlayerSpecialAction() {
        while (true) {
            System. out.print("Will " + playerPenguin.getName() + " use its special action? Y/N: ");
            String input = scanner.nextLine().trim().toUpperCase();
            if (input.equals("Y")) return true;
            if (input. equals("N")) return false;
        }
    }

    private Direction askPlayerDirection(String prompt) {
        while (true) {
            System.out.print(prompt + "U (Up), D (Down), L (Left), R (Right): ");
            String input = scanner.nextLine().trim().toUpperCase();
            if (!input.isEmpty()) {
                Direction dir = Direction.fromChar(input.charAt(0));
                if (dir != null) return dir;
            }
        }
    }

    private Direction chooseAIDirection(Penguin penguin) {
        Direction[] directions = Direction.values();
        List<Direction> foodDirs = new ArrayList<>();
        List<Direction> hazardDirs = new ArrayList<>();
        List<Direction> safeDirs = new ArrayList<>();

        for (Direction dir : directions) {
            int[] next = terrain.getNextPosition(penguin.getRow(), penguin.getColumn(), dir);
            if (! terrain.isValidPosition(next[0], next[1])) continue;
            ITerrainObject obj = terrain. getObjectAt(next[0], next[1]);
            if (obj instanceof FoodItem) foodDirs.add(dir);
            else if (obj instanceof Hazard) hazardDirs.add(dir);
            else safeDirs.add(dir);
        }
        if (! foodDirs.isEmpty()) return foodDirs.get((int) (Math.random() * foodDirs.size()));
        if (!safeDirs.isEmpty()) return safeDirs.get((int) (Math.random() * safeDirs.size()));
        if (!hazardDirs.isEmpty()) return hazardDirs.get((int) (Math.random() * hazardDirs.size()));
        return directions[(int) (Math.random() * directions.length)];
    }

    private void displayGrid() {
        System.out.println("-------------------------------------------------------------");
        for (int y = 0; y < terrain. getGridSize(); y++) {
            System.out.print("|");
            for (int x = 0; x < terrain.getGridSize(); x++) {
                ITerrainObject obj = terrain.getObjectAt(x, y);
                String notation = obj == null ? "  " : obj. getNotation();
                System.out. print(" " + String.format("%-2s", notation) + " |");
            }
            System.out.println();
            System.out.println("-------------------------------------------------------------");
        }
    }

    private void displayPenguinInfo() {
        System.out.println("These are the penguins on the icy terrain:");
        for (Penguin penguin : terrain.getPenguins()) {
            System.out.println("- " + penguin.getName() +
                    ": " + penguin.getClass().getSimpleName() +
                    (penguin == playerPenguin ?  " ---> YOUR PENGUIN" : ""));
        }
    }

    private void displayGameOver() {
        System.out.println("\n***** GAME OVER *****");
        System.out.println("***** SCOREBOARD FOR THE PENGUINS *****");

        List<Penguin> sortedPenguins = new ArrayList<>(terrain.getPenguins());
        sortedPenguins.sort((p1, p2) -> Integer.compare(p2.getTotalFoodWeight(), p1.getTotalFoodWeight()));

        String[] places = {"1st", "2nd", "3rd"};
        for (int i = 0; i < sortedPenguins.size(); i++) {
            Penguin p = sortedPenguins. get(i);
            System. out.println("* " + places[i] + " place: " + p.getName() +
                    (p == playerPenguin ? " (Your Penguin)" : ""));
            System.out.print("|---> Food items: ");
            List<FoodItem> foods = p.getCollectedFoods();
            for (int j = 0; j < foods.size(); j++) {
                FoodItem f = foods.get(j);
                System.out.print(f.getNotation() + " (" + f.getWeight() + " units)");
                if (j < foods.size() - 1) System.out.print(", ");
            }
            System.out.println();
            System.out.println("|---> Total weight: " + p.getTotalFoodWeight() + " units");
        }
    }

    private String getDirectionName(Direction dir) {
        switch (dir) {
            case UP: return "UP";
            case DOWN: return "DOWN";
            case LEFT: return "LEFT";
            case RIGHT: return "RIGHT";
            default: return "";
        }
    }
}