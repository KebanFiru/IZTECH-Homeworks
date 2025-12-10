package penguins;

import food.FoodType;
import java.util.ArrayList;
import java.util.List;

public abstract class Penguin {
    private PenguinType type;
    private final String name;
    private int x;
    private int y;
    private List<FoodType> collectedFoods;
    private int movesLeft;
    private boolean specialEffectUsed;
    private boolean stunned;
    private boolean inGame;

    public Penguin(String name, PenguinType type) {

        this.name = name;
        this.type = type;
        this.x = 0;
        this.y = 0;
        this.collectedFoods = new ArrayList<>();
        this.movesLeft = 4;
        this.specialEffectUsed = false;
        this.stunned = false;
        this.inGame = true;
    }

    public PenguinType getType() {
        return type;
    }
    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void setPosition(int x, int y) {
        this.x = x; this.y = y;
    }
    public List<FoodType> getCollectedFoods() {
        return collectedFoods;
    }

    public void collectFood(FoodType food) {
        collectedFoods.add(food);
    }
    public int getTotalFoodWeight() {
        int totalWeight = 0;
        for (FoodType food : collectedFoods) {
            totalWeight += food.getWeight();
        }
        return totalWeight;
    }
    public boolean isStunned() {
        return stunned;
    }
    public void setStunned(boolean value) {
        this.stunned = value;
    }

    public boolean isInGame() {
        return inGame;
    }
    public void setInGame(boolean value) {
        this.inGame = value;
    }
    public boolean hasUsedSpecialEffect() {
        return specialEffectUsed;
    }
    public void setSpecialEffectUsed(boolean value) {
        this.specialEffectUsed = value;
    }

    public int getMovesLeft() {
        return movesLeft;
    }
    public void decrementMoves() {
        if (movesLeft > 0){
            movesLeft--;
        }
    }
}
