public class GameApp {

    public static void main(String[] args){
        Player player = new Player("Adventurer");
        Box box = new Box();
        Chest chest = new Chest();

        Game game = new Game(5, player, box, chest);

        game.play();

    }
}

