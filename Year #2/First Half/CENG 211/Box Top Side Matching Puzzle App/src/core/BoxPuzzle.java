package core;
import java.util.Random;

public class BoxPuzzle {
    private BoxGrid grid;
    private int currentTurn;
    private MenuHandler menuHandler;

    public BoxPuzzle (){
        this.grid = new BoxGrid();
        this.menuHandler = new MenuHandler();
    }


}
