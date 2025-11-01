import java.io.IOException;
import java.util.Random;

public class ESportManagementApp {

    private static final String GAMES_CSV = "files/games.csv";
    private static final String GAMERS_CSV = "files/gamers.csv";

    public static void main(String[] args) {

        

        Game[] games = FileIO.readGames(GAMES_CSV);
        Gamer gamers = FileIO.readGamers(GAMERS_CSV)
    }
}