public class GameApp {

    public static void main(String[] args){
        Player player = new Player("Adventurer");
        Box box = new Box();
        Chest chest = new Chest();

        Game game = new Game(5, player, box, chest);

        game.play();

        System.out.println("\n\nRunning the game again...\n");

        player = new Player("Adventurer");
        box = new Box();
        chest = new Chest();
        game = new Game(5, player, box, chest);
        game.play();
    }
}

