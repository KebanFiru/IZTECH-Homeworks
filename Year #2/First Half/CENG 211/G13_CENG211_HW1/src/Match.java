import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

/**
 * Represents a match in the E-Sports tournament.
 * Each match consists of a gamer playing 3 games with calculated points based on performance and experience.
 */
public class Match {
    private int matchID;
    private Gamer gamer;
    private Game[] games;
    private int[] rounds;
    private int rawPoints;
    private int skillPoints;
    private int bonusPoints;
    private int matchPoints;

    /**
     * Number of games played in each match.
     */
    public static final int NUM_GAMES = 3;
    private static final Random RANDOM = new Random();

    /**
     * Constructor to create a Match object.
     * Generates random rounds for each game and calculates all point values.
     * 
     * @param matchID The unique identifier for the match
     * @param gamer The gamer participating in this match
     * @param games Array of games to be played in this match
     * @throws IllegalArgumentException if validation fails
     */
    public Match(int matchID, Gamer gamer, Game[] games) {
        if (matchID <= 0) {
            throw new IllegalArgumentException("Match ID must be positive");
        }
        if (gamer == null) {
            throw new IllegalArgumentException("Gamer cannot be null");
        }
        if (games == null) {
            throw new IllegalArgumentException("Games array cannot be null");
        }
        if (games.length != NUM_GAMES) {
            throw new IllegalArgumentException("Games array must contain exactly " + NUM_GAMES + " games");
        }
        for (int i = 0; i < games.length; i++) {
            if (games[i] == null) {
                throw new IllegalArgumentException("Game at index " + i + " cannot be null");
            }
        }

        this.matchID = matchID;
        this.gamer = new Gamer(gamer);
        this.games = copyGames(games);
        this.rounds = generateRandomRounds();
        this.rawPoints = calculateRawPoints();
        this.skillPoints = calculateSkillPoints();
        this.bonusPoints = calculateBonusPoints();
        this.matchPoints = calculateMatchPoints();
    }

    /**
     * Copy constructor to create a deep copy of a Match object.
     * 
     * @param another The Match object to copy from
     * @throws IllegalArgumentException if another is null or contains invalid data
     */
    public Match(Match another) {
        if (another == null) {
            throw new IllegalArgumentException("Cannot copy from null Match object");
        }
        if (another.gamer == null) {
            throw new IllegalArgumentException("Gamer in source match cannot be null");
        }
        if (another.games == null) {
            throw new IllegalArgumentException("Games in source match cannot be null");
        }
        if (another.games.length != NUM_GAMES) {
            throw new IllegalArgumentException("Games array must contain exactly " + NUM_GAMES + " games");
        }

        this.matchID = another.matchID;
        this.gamer = new Gamer(another.gamer);
        this.games = copyGames(another.games);
        this.rounds = another.rounds.clone();
        this.rawPoints = another.rawPoints;
        this.skillPoints = another.skillPoints;
        this.bonusPoints = another.bonusPoints;
        this.matchPoints = another.matchPoints;
    }

    /**
     * Generates random rounds for each game in the match.
     * Each round value is between 1 and 10.
     * 
     * @return Array of random round values
     */
    private static int[] generateRandomRounds() {
        int[] rounds = new int[NUM_GAMES];
        for (int i = 0; i < NUM_GAMES; i++) {
            rounds[i] = RANDOM.nextInt(10) + 1;
        }
        return rounds;
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
     * Calculates raw points by summing (rounds × basePointPerRound) for all games.
     * 
     * @return Total raw points
     */
    private int calculateRawPoints() {
        int total = 0;
        for (int i = 0; i < NUM_GAMES; i++) {
            total += rounds[i] * games[i].getBasePointPerRound();
        }
        return total;
    }

    /**
     * Calculates skill points based on raw points and gamer experience.
     * Formula: floor(rawPoints × (1 + min(experience, 10) × 0.02))
     * 
     * @return Calculated skill points
     */
    private int calculateSkillPoints() {
        int experience = Math.min(gamer.getExperienceYears(), 10);
        double multiplier = 1 + experience * 0.02;
        return (int) Math.floor(rawPoints * multiplier);
    }

    /**
     * Calculates bonus points based on raw points tiers.
     * ≥600: 100 points, ≥400: 50 points, ≥200: 25 points, <200: 10 points
     * 
     * @return Calculated bonus points
     * @throws IllegalStateException if rawPoints is negative
     */
    private int calculateBonusPoints() {
        if (rawPoints < 0) {
            throw new IllegalStateException("Error: Bonus point cannot be calculated because the raw point is negative.");
        }
        if (rawPoints >= 600) return 100;
        else if (rawPoints >= 400) return 50;
        else if (rawPoints >= 200) return 25;
        else return 10;
    }
    
    /**
     * Sets a new match ID.
     * 
     * @param matchID The new match ID
     * @throws IllegalArgumentException if matchID is not positive
     */
    public void setMatchID(int matchID) {
        if (matchID <= 0) {
            throw new IllegalArgumentException("Match ID must be positive");
        }
        this.matchID = matchID;
    }

    /**
     * Calculates total match points as skill points plus bonus points.
     * 
     * @return Total match points
     */
    private int calculateMatchPoints() {
        return skillPoints + bonusPoints;
    }

    /**
     * Gets the match ID.
     * 
     * @return The match ID
     */
    public int getMatchID() { return matchID; }

    /**
     * Gets the raw points.
     * 
     * @return The raw points
     */
    public int getRawPoints() { return rawPoints; }
    
    /**
     * Gets the skill points.
     * 
     * @return The skill points
     */
    public int getSkillPoints() { return skillPoints; }
    
    /**
     * Gets the bonus points.
     * 
     * @return The bonus points
     */
    public int getBonusPoints() { return bonusPoints; }
    
    /**
     * Gets the total match points.
     * 
     * @return The total match points
     */
    public int getMatchPoints() { return matchPoints; }

    /**
     * Gets a deep copy of the games array.
     * 
     * @return Copy of the games array
     */
    public Game[] getGames() { return copyGames(games); }
    
    /**
     * Gets a clone of the rounds array.
     * 
     * @return Clone of the rounds array
     */
    public int[] getRounds() { return rounds.clone(); }
    
    /**
     * Gets a copy of the gamer.
     * 
     * @return Copy of the gamer object
     */
    public Gamer getGamer() { return new Gamer(gamer); }

    /**
     * Sets a new gamer and recalculates skill-dependent points.
     * 
     * @param gamer The new gamer
     * @throws IllegalArgumentException if gamer is null
     */
    public void setGamer(Gamer gamer) {
        if (gamer == null) {
            throw new IllegalArgumentException("Gamer cannot be null");
        }
        this.gamer = new Gamer(gamer);
        this.skillPoints = calculateSkillPoints();
        this.bonusPoints = calculateBonusPoints();
        this.matchPoints = calculateMatchPoints();
    }
    
    /**
     * Sets new games and recalculates all points.
     * 
     * @param games The new games array
     * @throws IllegalArgumentException if games array is invalid
     */
    public void setGames(Game[] games) {
        if (games == null) {
            throw new IllegalArgumentException("Games array cannot be null");
        }
        if (games.length != NUM_GAMES) {
            throw new IllegalArgumentException("Games array must contain exactly " + NUM_GAMES + " elements");
        }
        for (int i = 0; i < games.length; i++) {
            if (games[i] == null) {
                throw new IllegalArgumentException("Game at index " + i + " cannot be null");
            }
        }
        this.games = copyGames(games);
        this.rawPoints = calculateRawPoints();
        this.skillPoints = calculateSkillPoints();
        this.bonusPoints = calculateBonusPoints();
        this.matchPoints = calculateMatchPoints();
    }

    /**
     * Sets new rounds and recalculates all points.
     * 
     * @param rounds The new rounds array (each value 1-10)
     * @throws IllegalArgumentException if rounds array is invalid
     */
    public void setRounds(int[] rounds) {
        if (rounds == null) {
            throw new IllegalArgumentException("Rounds array cannot be null");
        }
        if (rounds.length != NUM_GAMES) {
            throw new IllegalArgumentException("Rounds array must contain exactly " + NUM_GAMES + " values");
        }
        for (int i = 0; i < rounds.length; i++) {
            if (rounds[i] < 1) {
                throw new IllegalArgumentException("Round value at index " + i + " must be at least 1");
            }
            if (rounds[i] > 10) {
                throw new IllegalArgumentException("Round value at index " + i + " cannot exceed 10");
            }
        }
        this.rounds = rounds.clone();
        this.rawPoints = calculateRawPoints();
        this.skillPoints = calculateSkillPoints();
        this.bonusPoints = calculateBonusPoints();
        this.matchPoints = calculateMatchPoints();
    }
    
    /**
     * Returns a string representation of this match.
     * 
     * @return String containing match details including ID, points, gamer, games and rounds
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Match ID: %d, Total Match Points: %d", matchID, matchPoints));
        sb.append("\nGamer Information:\n").append(gamer.toString());

        for (int i = 0; i < NUM_GAMES; i++) {
            sb.append(games[i].toString())
                    .append(String.format("\nNumber of rounds for this game: %d\n", rounds[i]));
        }
        return sb.toString();
    }

    /**
     * Compares this match with another object for equality.
     * Two matches are equal if they have the same matchID, matchPoints, rounds, gamer, and games.
     * 
     * @param obj The object to compare with
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Match)) return false;
        Match other = (Match) obj;
        return matchID == other.matchID &&
                matchPoints == other.matchPoints &&
                Arrays.equals(rounds, other.rounds) &&
                Objects.equals(gamer, other.gamer) &&
                Arrays.equals(games, other.games);
    }

    /**
     * Generates a hash code for this match.
     * 
     * @return Hash code based on matchID, matchPoints, rounds, gamer, and games
     */
    @Override
    public int hashCode() {
        return Objects.hash(matchID, matchPoints, Arrays.hashCode(rounds), gamer, Arrays.hashCode(games));
    }
}
