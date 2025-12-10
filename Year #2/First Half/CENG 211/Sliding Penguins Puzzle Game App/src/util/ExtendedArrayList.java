package util;

import java.util.ArrayList;
import food.FoodType;

public class ExtendedArrayList<T> extends ArrayList<T> {

    private Class<T> type;
    private int length;

    public ExtendedArrayList(int length,  Class<T> type){
        super(length);
        this.length = length;
        this.type = type;
    }

    @Override
    public ExtendedArrayList<T> clone(){

        ExtendedArrayList<T> copyArray = new ExtendedArrayList<T>(length, type);

        if(FoodType.class.isAssignableFrom(type)){
            for(T food: this){
                T copyFood = new FoodType(food);
                copyArray.add(copyFood);
            }
            return copyArray;
        }
        return (ExtendedArrayList<T>) super.clone();
    }

}
