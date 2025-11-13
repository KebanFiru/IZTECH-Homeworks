package utils;

import java.util.ArrayList;

public class ExtendedArrayList<T> extends ArrayList<T> {

    private final Class<T> type;

    public ExtendedArrayList(Class<T> type, int size){
        super(size);
        this.type = type;
    }

    @Override
    public ExtendedArrayList<T> clone(){
        if(Application.class.equels(type)) {
            ExtendedArrayList<Application> clonedArrayList = new ExtendedArrayList<Application>(Application.class, super.size());

            for(int i =0; i<this.size(); i++){
                Application clonedApplication = new Application(this.get(i));
                clonedArrayList.add(clonedApplication);
            }

            return (ExtendedArrayList<T>) clonedArrayList;
        }
        return (ExtendedArrayList<T>) super.clone();
    }
}
