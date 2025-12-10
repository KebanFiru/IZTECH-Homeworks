package penguins;

import food.FoodType;
import util.ExtendedArrayList;

public abstract class Penguin {

    private PenguinType type;
    private int x;
    private int y;
    private ExtendedArrayList<FoodType> collectedFoods;
    private int movesLeft;
    private boolean specialEffectUsed;
    private boolean stunned;
    private int totalWeight;
    private boolean inGame;

    public Penguin(String name, PenguinType type){

        this.type = type;
        this.collectedFoods = new ExtendedArrayList<FoodType>(0,FoodType);
        this.movesLeft = 4;
        this.specialEffectUsed = false;
        this.stunned = false;
        this.totalWeight = 0;
        this.inGame = true;
    }

    public abstract PenguinType getType();

    public int[] getPosition(){
        int[] position = new int[2];
        position[0] = x;
        position[1] = y;

        return position;
    }

    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }

    public abstract void useSpecialAbility(Direction dir, IcyTerrain terrain);

    public void addFood(FoodType food){

        collectedFoods.add(food);
    }

    public int getTotalWeight(){

        for(FoodType food: collectedFoods){
            totalWeight = totalWeight+food.getWeight();
        }

        return totalWeight;
    }

    public boolean isStunned(){

        return this.stunned;
    }

    public void setStun(boolean value){
        this.stunned = value;
    }

    public void move(Direction direction, IcyTerrain terrain) {
        if(this.movesLeft > 0){
            terrain.slidePenguin(this, direction, -1, false);
            movesLeft = movesLeft - 1;
        }
    }

    public boolean isIngGame(){

        return inGame;
    }

    public void setInGame(boolean value){

        this.inGame = value;
    }
    public void setSpecialEffectUsed(boolean value){

        this.specialEffectUsed = value;
    }
    public boolean getSpecialEffectUsed(){

        return specialEffectUsed;
    }
    public ExtendedArrayList<FoodType> getCollectedFoods() {

        return collectedFoods;
    }

    public FoodType removeLightestFood() {
        if (collectedFoods.isEmpty()) return null;
        FoodType lightest = collectedFoods.get(0);
        for (FoodType food : collectedFoods) {
            if (food.getWeight() < lightest.getWeight()) {
                lightest = food;
            }
        }
        collectedFoods.remove(lightest);
        return lightest;
    }


}
