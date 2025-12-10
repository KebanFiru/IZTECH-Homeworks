package penguins;

import food.FoodType;
import util.ExtendedArrayList;

public abstract class Penguin {

    private String type;
    private String name;
    private int x;
    private int y;
    private ExtendedArrayList<FoodType> collectedFoods;
    private String[] moves;
    private int wichMove;
    private int movesLeft;
    private boolean specialEffectUsed;
    private boolean stunned;
    private int totalWeight;
    private boolean inGame;

    public Penguin(String name){

        this.x = 0;
        this.y = 0;
        this.collectedFoods = new ExtendedArrayList<FoodType>(0,FoodType);
        this.moves = new String[4];
        this.wichMove = 0;
        this.movesLeft = 4;
        this.specialEffectUsed = false;
        this.stunned = false;
        this.totalWeight = 0;
        this.inGame = true;
    }

    public abstract String getType();

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

    public abstract void slide(Direction direction);

    public abstract void useSpecialAbility(Direction direction);

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

    public void move(String direction){

        if(this.moves.length < 4){
            moves[wichMove] = direction;
            wichMove = wichMove+1;
            movesLeft = movesLeft - 1;
        }
        else{
            throw new ArrayIndexOutOfBoundsException("A penuin only can move 4 times");
        }
    }

    public boolean isIngGame(){

        return inGame;
    }

    public void setInGame(boolean value){

        this.inGame = value;
    }
    
}
