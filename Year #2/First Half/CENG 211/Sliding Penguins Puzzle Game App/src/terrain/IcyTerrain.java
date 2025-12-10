package terrain;

import terrain.Direction;
import penguins.PenguinType;
// import food.Food; // Food sınıfı çıkarıldı
import food.FoodType; // FoodType eklendi
import hazards.*;
import model.ITerrainObject;
import penguins.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class IcyTerrain {
    private static final int GRID_SIZE = 10;
    private List<List<ITerrainObject>> grid;
    private List<Penguin> penguins;
    private List<FoodType> foodItems; // Değiştirildi: Food -> FoodType
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
        foodItems = new ArrayList<>(); // Değiştirildi
        hazards = new ArrayList<>();
        scanner = new Scanner(System.in);
        currentTurn = 1;
    }

    public void startGame() {
        System.out.println("Welcome to Sliding Penguins Puzzle Game App. An 10x10 icy terrain grid is being generated.");
        System.out.println("Penguins, Hazards, and Food items are also being generated. The initial icy terrain grid:");

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

    // Değiştirildi: Food yerine FoodType kullanılıyor
    private void placeFood() {
        FoodType food = new FoodType(); // FoodType bir sınıf (Class) olmalı!
        int[] pos = findEmptyPosition(false);
        if (pos != null) {
            food.setPosition(pos[0], pos[1]); // FoodType'ın setPosition metodu olmalı
            placeObject(food, pos[0], pos[1]);
            foodItems.add(food);
        }
    }

    private int[] findEmptyPosition(boolean avoidPenguins) {
        List<int[]> emptyPositions = new ArrayList<>();

        for (int y = 0; y < GRID_SIZE; y++) {
            for (int x = 0; x < GRID_SIZE; x++) {
                ITerrainObject obj = getObjectAt(x, y);
                // Değiştirildi: instanceof FoodType kontrolü
                if (obj == null || (!avoidPenguins && obj instanceof FoodType)) {
                    emptyPositions.add(new int[]{x, y});
                } else if (avoidPenguins && obj instanceof Penguin) {
                    continue;
                }
            }
        }

        if (emptyPositions.isEmpty()) {
            return null;
        }
        return emptyPositions.get((int) (Math.random() * emptyPositions.size()));
    }

    private void processPenguinTurn(Penguin penguin) {
        System.out.println("\n*** Turn " + currentTurn + " - " + penguin.getName() +
                (penguin == playerPenguin ? " (Your Penguin):" : ":"));

        boolean useSpecial = false;
        Direction specialMoveDir = null;
        Direction moveDir;

        if (penguin == playerPenguin) {
            if (!penguin.hasUsedSpecialAction()) {
                useSpecial = askPlayerSpecialAction();
            }

            if (useSpecial && penguin instanceof RoyalPenguin) {
                specialMoveDir = askPlayerDirection("Which direction will " + penguin.getName() + " move one square? ");
            }

            moveDir = askPlayerDirection("Which direction will " + penguin.getName() + " move? ");
        } else {
            if (!penguin.hasUsedSpecialAction()) {
                double rand = Math.random();
                if (penguin instanceof RockhopperPenguin) {
                    useSpecial = false;
                } else {
                    useSpecial = rand < 0.3;
                }
            }

            if (useSpecial) {
                System.out.println(penguin.getName() + " chooses to USE its special action.");
                penguin.setSpecialActionUsed(true);

                if (penguin instanceof RoyalPenguin) {
                    specialMoveDir = chooseAISafeDirection(penguin, true);
                    if (specialMoveDir != null) {
                        System.out.println(penguin.getName() + " moves one square to the " +
                                getDirectionName(specialMoveDir) + ".");
                        moveOneSquare(penguin, specialMoveDir);
                    }
                }
            } else {
                System.out.println(penguin.getName() + " does NOT to use its special action.");
            }

            moveDir = chooseAIDirection(penguin);
        }

        if (specialMoveDir != null && penguin instanceof RoyalPenguin) {
            moveOneSquare(penguin, specialMoveDir);
        }

        System.out.println(penguin.getName() + " chooses to move " + getDirectionName(moveDir) + ".");
        performMove(penguin, moveDir, useSpecial);

        displayGrid();
    }

    private boolean askPlayerSpecialAction() {
        while (true) {
            System.out.print("Will " + playerPenguin.getName() + " use its special action? Answer with Y or N --> ");
            String input = scanner.nextLine().trim().toUpperCase();
            if (input.equals("Y")) {
                return true;
            } else if (input.equals("N")) {
                return false;
            }
        }
    }

    private Direction askPlayerDirection(String prompt) {
        while (true) {
            System.out.print(prompt + "Answer with U (Up), D (Down), L (Left), R (Right) --> ");
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                Direction dir = Direction.fromChar(input.charAt(0));
                if (dir != null) {
                    return dir;
                }
            }
        }
    }

    private Direction chooseAIDirection(Penguin penguin) {
        Direction[] directions = Direction.values();
        List<Direction> foodDirs = new ArrayList<>();
        List<Direction> hazardDirs = new ArrayList<>();
        List<Direction> waterDirs = new ArrayList<>();
        List<Direction> safeDirs = new ArrayList<>();

        for (Direction dir : directions) {
            int[] next = getNextPosition(penguin.getX(), penguin.getY(), dir);
            if (!isValidPosition(next[0], next[1])) {
                waterDirs.add(dir);
                continue;
            }

            ITerrainObject obj = getObjectAt(next[0], next[1]);
            // Değiştirildi: FoodType kontrolü
            if (obj instanceof FoodType) {
                foodDirs.add(dir);
            } else if (obj instanceof HoleInIce && !((HoleInIce) obj).isPlugged()) {
                waterDirs.add(dir);
            } else if (obj instanceof Hazard) {
                hazardDirs.add(dir);
            } else {
                safeDirs.add(dir);
            }
        }

        if (!foodDirs.isEmpty()) {
            return foodDirs.get((int) (Math.random() * foodDirs.size()));
        }

        if (!safeDirs.isEmpty()) {
            return safeDirs.get((int) (Math.random() * safeDirs.size()));
        }

        if (!hazardDirs.isEmpty()) {
            if (penguin instanceof RockhopperPenguin && !penguin.hasUsedSpecialAction()) {
                System.out.println(penguin.getName() + " will automatically USE its special action.");
                penguin.setSpecialActionUsed(true);
            }
            return hazardDirs.get((int) (Math.random() * hazardDirs.size()));
        }

        return waterDirs.get((int) (Math.random() * waterDirs.size()));
    }

    private Direction chooseAISafeDirection(Penguin penguin, boolean singleStep) {
        Direction[] directions = Direction.values();
        List<Direction> safeDirs = new ArrayList<>();

        for (Direction dir : directions) {
            int[] next = getNextPosition(penguin.getX(), penguin.getY(), dir);
            if (!isValidPosition(next[0], next[1])) {
                continue;
            }

            ITerrainObject obj = getObjectAt(next[0], next[1]);
            // Değiştirildi: FoodType kontrolü
            if (obj == null || obj instanceof FoodType) {
                safeDirs.add(dir);
            }
        }

        if (!safeDirs.isEmpty()) {
            return safeDirs.get((int) (Math.random() * safeDirs.size()));
        }

        return directions[(int) (Math.random() * directions.length)];
    }

    private void moveOneSquare(Penguin penguin, Direction dir) {
        int[] next = getNextPosition(penguin.getX(), penguin.getY(), dir);

        if (!isValidPosition(next[0], next[1])) {
            System.out.println(penguin.getName() + " falls into the water!");
            removePenguin(penguin);
            return;
        }

        ITerrainObject obj = getObjectAt(next[0], next[1]);
        removeObject(penguin.getX(), penguin.getY());

        // Değiştirildi: FoodType casting ve metot çağrıları
        if (obj instanceof FoodType) {
            FoodType food = (FoodType) obj;
            penguin.collectFood(food);
            foodItems.remove(food);
            // FoodType sınıfı içinde getType() olmayabilir, direkt food'u kullanabilirsin veya getName() vb.
            // Burada FoodType'ın yapısına göre düzenleme gerekebilir.
            System.out.println(penguin.getName() + " takes the food on the ground. (Weight=" + food.getWeight() + " units)");
        }

        penguin.setPosition(next[0], next[1]);
        placeObject(penguin, next[0], next[1]);

        if (obj == null || obj instanceof FoodType) {
            System.out.println(penguin.getName() + " stops at " +
                    (obj == null ? "an empty square" : "a square with food") +
                    " using its special action.");
        }
    }

    private void performMove(Penguin penguin, Direction dir, boolean useSpecial) {
        int stopSquare = -1;
        boolean isJumping = false;

        if (useSpecial) {
            if (penguin instanceof KingPenguin) {
                stopSquare = 5;
            } else if (penguin instanceof EmperorPenguin) {
                stopSquare = 3;
            } else if (penguin instanceof RockhopperPenguin) {
                isJumping = true;
            }
        }

        slidePenguin(penguin, dir, stopSquare, isJumping);
    }

    private void slidePenguin(Penguin penguin, Direction dir, int stopSquare, boolean canJump) {
        removeObject(penguin.getX(), penguin.getY());
        int squareCount = 0;
        int currentX = penguin.getX();
        int currentY = penguin.getY();
        boolean hasJumped = false;

        while (true) {
            int[] next = getNextPosition(currentX, currentY, dir);
            squareCount++;

            if (!isValidPosition(next[0], next[1])) {
                System.out.println(penguin.getName() + " falls into the water!");
                removePenguin(penguin);
                return;
            }

            ITerrainObject obj = getObjectAt(next[0], next[1]);

            if (stopSquare > 0 && squareCount == stopSquare) {
                if (obj == null || obj instanceof FoodType) { // Değiştirildi
                    currentX = next[0];
                    currentY = next[1];
                    if (obj instanceof FoodType) {
                        collectFoodAtPosition(penguin, (FoodType) obj);
                    }
                    penguin.setPosition(currentX, currentY);
                    placeObject(penguin, currentX, currentY);
                    System.out.println(penguin.getName() + " stops at the " +
                            getOrdinal(stopSquare) + " square using its special action.");
                    return;
                }
            }

            if (obj != null) {
                // Değiştirildi: FoodType kontrolü
                if (obj instanceof FoodType) {
                    currentX = next[0];
                    currentY = next[1];
                    collectFoodAtPosition(penguin, (FoodType) obj);
                    penguin.setPosition(currentX, currentY);
                    placeObject(penguin, currentX, currentY);
                    return;
                } else if (obj instanceof Penguin) {
                    penguin.setPosition(currentX, currentY);
                    placeObject(penguin, currentX, currentY);

                    Penguin otherPenguin = (Penguin) obj;
                    System.out.println(penguin.getName() + " collides with " + otherPenguin.getName() + "!");

                    slidePenguin(otherPenguin, dir, -1, false);
                    return;
                } else if (obj instanceof Hazard) {
                    if (canJump && !hasJumped) {
                        Hazard hazard = (Hazard) obj;
                        System.out.println(penguin.getName() + " jumps over " +
                                hazard.getHazardType() + " in its path.");
                        hasJumped = true;
                        currentX = next[0];
                        currentY = next[1];
                        continue;
                    }

                    handleHazardCollision(penguin, (Hazard) obj, currentX, currentY, dir);
                    return;
                }
            }

            currentX = next[0];
            currentY = next[1];
        }
    }

    private void handleHazardCollision(Penguin penguin, Hazard hazard, int stopX, int stopY, Direction dir) {
        if (hazard instanceof HoleInIce) {
            HoleInIce hole = (HoleInIce) hazard;
            if (hole.isPlugged()) {
                penguin.setPosition(hazard.getX(), hazard.getY());
                placeObject(penguin, hazard.getX(), hazard.getY());
            } else {
                System.out.println(penguin.getName() + " falls into the " + hazard.getHazardType() + " due to " +
                        hazard.getHazardType() + " in its path.");
                removePenguin(penguin);
            }
        } else if (hazard instanceof HeavyIceBlock) {
            penguin.setPosition(stopX, stopY);
            placeObject(penguin, stopX, stopY);

            // Food -> FoodType dönüşümü gerekebilir (Penguin sınıfındaki removeLightestFood metoduna bağlı)
            FoodType removed = penguin.removeLightestFood();
            if (removed != null) {
                System.out.println(penguin.getName() + " loses food (" + removed.getWeight() + " units) as penalty.");
            }
        } else if (hazard instanceof LightIceBlock) {
            penguin.setPosition(stopX, stopY);
            placeObject(penguin, stopX, stopY);
            penguin.setStunned(true);
            System.out.println(penguin.getName() + " hits the " + hazard.getHazardType() +
                    " and will be stunned next turn.");

            removeObject(hazard.getX(), hazard.getY());
            slideHazard(hazard, dir);
        } else if (hazard instanceof SeaLion) {
            Direction opposite = dir.getOpposite();
            System.out.println(penguin.getName() + " bounces off the " + hazard.getHazardType() + "!");

            removeObject(hazard.getX(), hazard.getY());
            slideHazard(hazard, dir);

            slidePenguin(penguin, opposite, -1, false);
        }
    }

    private void slideHazard(Hazard hazard, Direction dir) {
        int currentX = hazard.getX();
        int currentY = hazard.getY();

        while (true) {
            int[] next = getNextPosition(currentX, currentY, dir);

            if (!isValidPosition(next[0], next[1])) {
                hazards.remove(hazard);
                return;
            }

            ITerrainObject obj = getObjectAt(next[0], next[1]);

            // Değiştirildi: FoodType kontrolü
            if (obj instanceof FoodType) {
                foodItems.remove(obj);
                currentX = next[0];
                currentY = next[1];
                continue;
            } else if (obj instanceof HoleInIce) {
                HoleInIce hole = (HoleInIce) obj;
                if (!hole.isPlugged()) {
                    hole.setPlugged(true);
                    hazards.remove(hazard);
                    return;
                }
            } else if (obj instanceof Penguin) {
                hazard.setPosition(currentX, currentY);
                placeObject(hazard, currentX, currentY);
                return;
            } else if (obj != null) {
                hazard.setPosition(currentX, currentY);
                placeObject(hazard, currentX, currentY);
                return;
            }

            currentX = next[0];
            currentY = next[1];
        }
    }

    // Değiştirildi: Parametre Food yerine FoodType
    private void collectFoodAtPosition(Penguin penguin, FoodType food) {
        penguin.collectFood(food);
        foodItems.remove(food);
        System.out.println(penguin.getName() + " takes the food on the ground. (Weight=" + food.getWeight() + " units)");
    }

    private void removePenguin(Penguin penguin) {
        penguin.setRemoved(true);
        System.out.println("\n*** " + penguin.getName() + " IS REMOVED FROM THE GAME!");
    }

    private int[] getNextPosition(int x, int y, Direction dir) {
        switch (dir) {
            case UP: return new int[]{x, y - 1};
            case DOWN: return new int[]{x, y + 1};
            case LEFT: return new int[]{x - 1, y};
            case RIGHT: return new int[]{x + 1, y};
            default: return new int[]{x, y};
        }
    }

    private boolean isValidPosition(int x, int y) {
        return x >= 0 && x < GRID_SIZE && y >= 0 && y < GRID_SIZE;
    }

    private ITerrainObject getObjectAt(int x, int y) {
        if (!isValidPosition(x, y)) return null;
        return grid.get(y).get(x);
    }

    private void placeObject(ITerrainObject obj, int x, int y) {
        if (isValidPosition(x, y)) {
            grid.get(y).set(x, obj);
        }
    }

    private void removeObject(int x, int y) {
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
            System.out.println("- Penguin " + penguin.getName().substring(1) + " (" + penguin.getName() +
                    "): " + penguin.getType().getDisplayName() +
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

            // Değiştirildi: Food -> FoodType
            List<FoodType> foods = p.getCollectedFood();
            for (int j = 0; j < foods.size(); j++) {
                FoodType f = foods.get(j);
                // FoodType üzerinden nota veya isme erişim
                System.out.print(f.getNotation() + " (" + f.getWeight() + " units)");
                if (j < foods.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
            System.out.println("|---> Total weight: " + p.getTotalFoodWeight() + " units");
        }
    }

    private String getDirectionName(Direction dir) {
        switch (dir) {
            case UP: return "UPWARDS";
            case DOWN: return "DOWNWARDS";
            case LEFT: return "to the LEFT";
            case RIGHT: return "to the RIGHT";
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