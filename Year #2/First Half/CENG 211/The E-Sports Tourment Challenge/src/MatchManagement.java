import java.util.Random;

public class MatchManagement {
    private Match[][] matches;
    private Gamer[] gamers;
    private Game[] games;
    
    private static final int MATCHES_PER_GAMER = 15;
    private static final int MIN_GAMES_REQUIRED = 3;
    private static final Random RANDOM = new Random();

    public MatchManagement(Gamer[] gamers, Game[] games) {
        if (gamers == null) {
            throw new IllegalArgumentException("Gamers array cannot be null");
        }
        if (games == null) {
            throw new IllegalArgumentException("Games array cannot be null");
        }
        if (gamers.length == 0) {
            throw new IllegalArgumentException("Gamers array cannot be empty");
        }
        if (games.length == 0) {
            throw new IllegalArgumentException("Games array cannot be empty");
        }
        if (games.length < MIN_GAMES_REQUIRED) {
            throw new IllegalArgumentException("Games array must contain at least " + MIN_GAMES_REQUIRED + " games");
        }
        for (int i = 0; i < gamers.length; i++) {
            if (gamers[i] == null) {
                throw new IllegalArgumentException("Gamer at index " + i + " cannot be null");
            }
        }
        for (int i = 0; i < games.length; i++) {
            if (games[i] == null) {
                throw new IllegalArgumentException("Game at index " + i + " cannot be null");
            }
        }
        
        this.gamers = gamers;
        this.games = games;
        this.matches = new Match[gamers.length][MATCHES_PER_GAMER];
    }

    public void generateMatches() {
        for (int i = 0; i < gamers.length; i++) {
            for (int j = 0; j < MATCHES_PER_GAMER; j++) {
                matches[i][j] = createMatchFor(gamers[i], i, j);
            }
        }
    }

    private Match createMatchFor(Gamer gamer, int gamerIndex, int matchIndex){
        if (gamer == null) {
            throw new IllegalArgumentException("Gamer cannot be null");
        }
        if (gamerIndex < 0) {
            throw new IllegalArgumentException("Gamer index cannot be negative");
        }
        if (matchIndex < 0) {
            throw new IllegalArgumentException("Match index cannot be negative");
        }
        
        Game[] selectedGames = selectRandomGames(games);
        int matchID = generateMatchID(gamerIndex, matchIndex);
        return new Match(matchID, gamer, selectedGames);
    }

    private Game[] selectRandomGames(Game[] games) {
        if (games == null) {
            throw new IllegalArgumentException("Games array cannot be null");
        }
        if (games.length < MIN_GAMES_REQUIRED) {
            throw new IllegalArgumentException("Games array must contain at least " + MIN_GAMES_REQUIRED + " games");
        }
        
        Game[] chosen = new Game[MIN_GAMES_REQUIRED];
        boolean[] used = new boolean[games.length];

        for (int i = 0; i < MIN_GAMES_REQUIRED; i++) {
            int index;
            do {
                index = RANDOM.nextInt(games.length);
            } while (used[index]);
            used[index] = true;
            chosen[i] = games[index];
        }
        return chosen;
    }

    private int generateMatchID(int gamerIndex, int matchIndex) {
        return (gamerIndex) * MATCHES_PER_GAMER + matchIndex + 1;
    }

    public Match[][] getAllMatches() {
        return matches;
    }

}




