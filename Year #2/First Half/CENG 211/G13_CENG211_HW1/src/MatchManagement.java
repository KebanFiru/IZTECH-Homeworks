import java.util.Random;

/**
 * Manages the generation and storage of matches for all gamers in the tournament.
 * Each gamer plays a fixed number of matches with randomly selected games.
 */
public class MatchManagement {
    private Match[][] matches;
    private Gamer[] gamers;
    private Game[] games;

    private static int matchIDCounter = 0;
    private static final int MATCHES_PER_GAMER = 15;
    private static final int MIN_GAMES_REQUIRED = 3;
    private static final Random RANDOM = new Random();


    /**
     * Constructor to create a MatchManagement object.
     * 
     * @param gamers Array of gamers participating in the tournament
     * @param games Array of available games
     * @throws IllegalArgumentException if validation fails
     */
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

        this.gamers = copyGamers(gamers);
        this.games = copyGames(games);
        this.matches = new Match[gamers.length][MATCHES_PER_GAMER];
    }

    /**
     * Creates a deep copy of the games array.
     * 
     * @param games The games array to copy
     * @return Deep copy of the games array
     */
    private static Game[] copyGames(Game[] games) {
        Game[] copy = new Game[games.length];
        for (int i = 0; i < games.length; i++) {
            copy[i] = new Game(games[i]);
        }
        return copy;
    }

    /**
     * Creates a deep copy of the gamers array.
     * 
     * @param gamers The gamers array to copy
     * @return Deep copy of the gamers array
     */
    private static Gamer[] copyGamers(Gamer[] gamers) {
        Gamer[] copy = new Gamer[gamers.length];
        for (int i = 0; i < gamers.length; i++) {
            copy[i] = new Gamer(gamers[i]);
        }
        return copy;
    }


    /**
     * Generates all matches for all gamers.
     * Each gamer will have MATCHES_PER_GAMER matches created.
     */
    public void generateMatches() {
        for (int i = 0; i < gamers.length; i++) {
            for (int j = 0; j < MATCHES_PER_GAMER; j++) {
                matches[i][j] = createMatchFor(gamers[i], i, j);
            }
        }
    }

    /**
     * Creates a match for a specific gamer with randomly selected games.
     * 
     * @param gamer The gamer for this match
     * @param gamerIndex The index of the gamer in the gamers array
     * @param matchIndex The match number for this gamer (0-14)
     * @return The created Match object
     * @throws IllegalArgumentException if parameters are invalid
     */
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

    /**
     * Selects random unique games from the available games pool.
     * 
     * @param games Array of available games
     * @return Array of randomly selected unique games
     * @throws IllegalArgumentException if games array is invalid
     */
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

    /**
     * Generates a unique match ID based on gamer and match indices.
     * 
     * @param gamerIndex The index of the gamer
     * @param matchIndex The match number for this gamer
     * @return Unique match ID
     */
    private int generateMatchID(int gamerIndex, int matchIndex) {
        return ++matchIDCounter;
    }
    
    /**
     * Gets all matches for a specific gamer.
     * 
     * @param gamerIndex The index of the gamer
     * @return Deep copy of the gamer's matches
     * @throws IllegalArgumentException if gamerIndex is invalid
     */
    public Match[] getMatchesOfGamer(int gamerIndex) {
        if (gamerIndex < 0) {
            throw new IllegalArgumentException("Gamer index cannot be negative");
        }
        if (gamerIndex >= matches.length) {
            throw new IllegalArgumentException("Gamer index out of bounds");
        }

        Match[] originalMatches = this.matches[gamerIndex];

        Match[] copy = new Match[originalMatches.length];

        for (int i = 0; i < originalMatches.length; i++){
            if(originalMatches[i] != null){
                copy[i] = new Match(originalMatches[i]);
            }

        }
        return copy;
    }

    /**
     * Gets all matches for all gamers.
     * 
     * @return 2D array containing deep copies of all matches
     */
    public Match[][] getAllMatches() {
        Match[][] copy = new Match[this.matches.length][];
        for (int i = 0; i < this.matches.length; i++) {
            copy[i] = new Match[this.matches[i].length];
            for (int j = 0; j < this.matches[i].length; j++) {
                if (this.matches[i][j] != null) {
                    copy[i][j] = new Match(this.matches[i][j]);
                }
            }
        }
        return copy;
    }

}




