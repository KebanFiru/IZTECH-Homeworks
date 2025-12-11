package terrain;

import penguins.*;
import food.FoodItem;
import hazards.*;
import model.ITerrainObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class IcyTerrain {
    private static final int GRID_SIZE = 10;
    private List<List<ITerrainObject>> grid;
    private List<Penguin> penguins;
    private List<FoodItem> foodItems;
    private List<Hazard> hazards;
    private Penguin playerPenguin;
    private Scanner scanner;
    private int currentTurn;

    public IcyTerrain() {
        grid = new ArrayList<>();
        for (int i = 0; i < GRID_SIZE; i++) {
            List<ITerrainObject> row = new ArrayList<>();
            for (int j = 0; j < GRID_SIZE; j++) {
                row.add(null);
            }
            grid.add(row);
        }
        penguins = new ArrayList<>();
        foodItems = new ArrayList<>();
        hazards = new ArrayList<>();
        scanner = new Scanner(System.in);
        currentTurn = 1;
    }

    public void startGame() {
        System.out.println("Welcome to Sliding Penguins Puzzle Game App. A 10x10 icy terrain grid is being generated.");
        System.out.println("Penguins, Hazards, and Food items are being generated. The initial icy terrain grid:");
        initializeGame();
        displayGrid();
        displayPenguinInfo();

        for (currentTurn = 1; currentTurn <= 4; currentTurn++) {
            for (Penguin penguin : penguins) {
                if (!penguin.isRemoved()) {
                    if (penguin.isStunned()) {
                        System.out.println("\n*** Turn " + currentTurn + " - " + penguin.getName() + ":");
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
        List<int[]> edgePositions = getAvailableEdgePositions();

        for (int i = 0; i < 3; i++) {
            PenguinType type = PenguinType.getRandom();
            Penguin penguin = createPenguin(names[i], type);
            int[] pos = edgePositions.remove((int) (Math.random() * edgePositions.size()));
            penguin.setPosition(pos[0], pos[1]);
            placeObject(penguin, pos[0], pos[1]);
            penguins.add(penguin);
        }
        playerPenguin = penguins.get((int) (Math.random() * 3));
    }

    private Penguin createPenguin(String name, PenguinType type) {
        switch (type) {
            case KING: return new KingPenguin(name);
            case EMPEROR: return new EmperorPenguin(name);
            case ROYAL: return new RoyalPenguin(name);
            case ROCKHOPPER: return new RockhopperPenguin(name);
            default: return new KingPenguin(name);
        }
    }

    private List<int[]> getAvailableEdgePositions() {
        List<int[]> positions = new ArrayList<>();
        for (int i = 0; i < GRID_SIZE; i++) {
            positions.add(new int[]{i, 0});
            positions.add(new int[]{i, GRID_SIZE - 1});
        }
        for (int i = 1; i < GRID_SIZE - 1; i++) {
            positions.add(new int[]{0, i});
            positions.add(new int[]{GRID_SIZE - 1, i});
        }
        return positions;
    }

    private void placeHazard() {
        Hazard hazard = Hazard.getRandomHazard();
        int[] pos = findEmptyPosition(true);
        if (pos != null) {
            hazard.setPosition(pos[0], pos[1]);
            placeObject(hazard, pos[0], pos[1]);
            hazards.add(hazard);
        }
    }

    private void placeFood() {
        FoodItem food = new FoodItem();
        int[] pos = findEmptyPosition(false);
        if (pos != null) {
            food.setPosition(pos[0], pos[1]);
            placeObject(food, pos[0], pos[1]);
            foodItems.add(food);
        }
    }

    private int[] findEmptyPosition(boolean avoidPenguins) {
        List<int[]> emptyPositions = new ArrayList<>();
        for (int y = 0; y < GRID_SIZE; y++) {
            for (int x = 0; x < GRID_SIZE; x++) {
                ITerrainObject obj = getObjectAt(x, y);
                if (obj == null || (!avoidPenguins && obj instanceof FoodItem)) {
                    emptyPositions.add(new int[]{x, y});
                } else if (avoidPenguins && obj instanceof Penguin) {
                    continue;
                }
            }
        }
        if (emptyPositions.isEmpty()) return null;
        return emptyPositions.get((int) (Math.random() * emptyPositions.size()));
    }

    private void processPenguinTurn(Penguin penguin) {
        System.out.println("\n*** Turn " + currentTurn + " - " + penguin.getName() +
                (penguin == playerPenguin ? " (Your Penguin):" : ":"));

        boolean useSpecial = false;
        Direction moveDir;

        if (penguin == playerPenguin) {
            if (!penguin.hasUsedSpecialAction()) {
                useSpecial = askPlayerSpecialAction();
            }
            moveDir = askPlayerDirection("Which direction will " + penguin.getName() + " move? ");
        } else {
            if (!penguin.hasUsedSpecialAction()) {
                double rand = Math.random();
                useSpecial = rand < 0.3;
            }
            moveDir = chooseAIDirection(penguin);
        }

        System.out.println(penguin.getName() + (useSpecial ? " uses SPECIAL action" : " slides normally") +
                " " + getDirectionName(moveDir) + ".");
        if (useSpecial) {
            penguin.useSpecialAbility(moveDir, this);
        } else {
            penguin.move(moveDir, this);
        }
        displayGrid();
    }

    private boolean askPlayerSpecialAction() {
        while (true) {
            System.out.print("Will " + playerPenguin.getName() + " use its special action? Y/N: ");
            String input = scanner.nextLine().trim().toUpperCase();
            if (input.equals("Y")) return true;
            if (input.equals("N")) return false;
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
            int[] next = getNextPosition(penguin.getRow(), penguin.getColumn(), dir);
            if (!isValidPosition(next[0], next[1])) continue;
            ITerrainObject obj = getObjectAt(next[0], next[1]);
            if (obj instanceof FoodItem) foodDirs.add(dir);
            else if (obj instanceof Hazard) hazardDirs.add(dir);
            else safeDirs.add(dir);
        }
        if (!foodDirs.isEmpty()) return foodDirs.get((int) (Math.random() * foodDirs.size()));
        if (!safeDirs.isEmpty()) return safeDirs.get((int) (Math.random() * safeDirs.size()));
        if (!hazardDirs.isEmpty()) return hazardDirs.get((int) (Math.random() * hazardDirs.size()));
        return directions[(int) (Math.random() * directions.length)];
    }

    /**
     * Movement logic delegated to penguin/hazard objects for true OOP.
     * stopSquare: special ability (e.g. Emperor 3rd square), -1 for default.
     */
    public void slidePenguin(Penguin penguin, Direction dir, int stopSquare, boolean canJump) {
        removeObject(penguin.getRow(), penguin.getColumn());
        int squareCount = 0;
        int currentX = penguin.getRow();
        int currentY = penguin.getColumn();
        boolean hasJumped = false;

        while (true) {
            int[] next = getNextPosition(currentX, currentY, dir);
            squareCount++;

            if (!isValidPosition(next[0], next[1])) {
                System.out.println(penguin.getName() + " falls into water!");
                penguin.setRemoved(true);
                return;
            }

            ITerrainObject obj = getObjectAt(next[0], next[1]);

            // Special stop logic (used for special ability)
            if (stopSquare > 0 && squareCount == stopSquare &&
                    (obj == null || obj instanceof FoodItem)) {
                currentX = next[0];
                currentY = next[1];
                if (obj instanceof FoodItem) {
                    penguin.collectFood((FoodItem) obj);
                    foodItems.remove(obj);
                }
                penguin.setPosition(currentX, currentY);
                placeObject(penguin, currentX, currentY);
                System.out.println(penguin.getName() + " stops at the " + getOrdinal(stopSquare) + " square (special ability).");
                return;
            }

            if (obj != null) {
                if (obj instanceof FoodItem) {
                    penguin.collectFood((FoodItem) obj);
                    foodItems.remove(obj);
                    penguin.setPosition(next[0], next[1]);
                    placeObject(penguin, next[0], next[1]);
                    return;
                } else if (obj instanceof Hazard) {
                    ((Hazard)obj).onPenguinLand(penguin, this, dir);
                    return;
                } else if (obj instanceof Penguin) {
                    Penguin otherPenguin = (Penguin) obj;
                    System.out.println(penguin.getName() + " collides with " + otherPenguin.getName() + "!");
                    penguin.setPosition(currentX, currentY);
                    placeObject(penguin, currentX, currentY);
                    return;
                }
            }
            currentX = next[0];
            currentY = next[1];
        }
    }

    // Helper methods for board management
    public int[] getNextPosition(int x, int y, Direction dir) {
        switch (dir) {
            case UP: return new int[]{x, y - 1};
            case DOWN: return new int[]{x, y + 1};
            case LEFT: return new int[]{x - 1, y};
            case RIGHT: return new int[]{x + 1, y};
            default: return new int[]{x, y};
        }
    }

    public boolean isValidPosition(int x, int y) {
        return x >= 0 && x < GRID_SIZE && y >= 0 && y < GRID_SIZE;
    }

    private ITerrainObject getObjectAt(int x, int y) {
        if (!isValidPosition(x, y)) return null;
        return grid.get(y).get(x);
    }

    public void placeObject(ITerrainObject obj, int x, int y) {
        if (isValidPosition(x, y)) {
            grid.get(y).set(x, obj);
        }
    }

    public void removeObject(int x, int y) {
        if (isValidPosition(x, y)) {
            grid.get(y).set(x, null);
        }
    }

    private void displayGrid() {
        System.out.println("-------------------------------------------------------------");
        for (int y = 0; y < GRID_SIZE; y++) {
            System.out.print("|");
            for (int x = 0; x < GRID_SIZE; x++) {
                ITerrainObject obj = getObjectAt(x, y);
                String notation = obj == null ? "  " : obj.getNotation();
                System.out.print(" " + String.format("%-2s", notation) + " |");
            }
            System.out.println();
            System.out.println("-------------------------------------------------------------");
        }
    }

    private void displayPenguinInfo() {
        System.out.println("These are the penguins on the icy terrain:");
        for (Penguin penguin : penguins) {
            System.out.println("- " + penguin.getName() +
                    ": " + penguin.getClass().getSimpleName() +
                    (penguin == playerPenguin ? " ---> YOUR PENGUIN" : ""));
        }
    }

    private void displayGameOver() {
        System.out.println("\n***** GAME OVER *****");
        System.out.println("***** SCOREBOARD FOR THE PENGUINS *****");

        List<Penguin> sortedPenguins = new ArrayList<>(penguins);
        sortedPenguins.sort((p1, p2) -> Integer.compare(p2.getTotalFoodWeight(), p1.getTotalFoodWeight()));

        String[] places = {"1st", "2nd", "3rd"};
        for (int i = 0; i < sortedPenguins.size(); i++) {
            Penguin p = sortedPenguins.get(i);
            System.out.println("* " + places[i] + " place: " + p.getName() +
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

    private String getOrdinal(int num) {
        if (num % 10 == 1 && num != 11) return num + "st";
        if (num % 10 == 2 && num != 12) return num + "nd";
        if (num % 10 == 3 && num != 13) return num + "rd";
        return num + "th";
    }

}
