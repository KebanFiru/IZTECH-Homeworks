import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class Match {
    private int matchID;
    private Gamer gamer;
    private Game[] games;
    private int[] rounds;
    private int rawPoints;
    private int skillPoints;
    private int bonusPoints;
    private int matchPoints;

    private static final int NUM_GAMES = 3;
    private static final Random RANDOM = new Random();

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
        this.gamer = another.gamer;
        this.games = another.games;
        this.rounds = another.rounds;
        this.rawPoints = another.rawPoints;
        this.skillPoints = another.skillPoints;
        this.bonusPoints = another.bonusPoints;
        this.matchPoints = another.matchPoints;
    }

    private static int[] generateRandomRounds() {
        int[] rounds = new int[NUM_GAMES];
        for (int i = 0; i < NUM_GAMES; i++) {
            rounds[i] = RANDOM.nextInt(10) + 1;
        }
        return rounds;
    }

    private static Game[] copyGames(Game[] games) {
        Game[] copy = new Game[games.length];
        for (int i = 0; i < games.length; i++) {
            copy[i] = new Game(games[i]);
        }
        return copy;
    }

    private int calculateRawPoints() {
        int total = 0;
        for (int i = 0; i < NUM_GAMES; i++) {
            total += rounds[i] * games[i].getBasePointPerRound();
        }
        return total;
    }

    private int calculateSkillPoints() {
        int experience = Math.min(gamer.getExperienceYears(), 10);
        double multiplier = 1 + experience * 0.02;
        return (int) Math.floor(rawPoints * multiplier);
    }

    private int calculateBonusPoints() {
        if (rawPoints < 0) {
            throw new IllegalStateException("Error: Bonus point cannot be calculated because the raw point is negative.");
        }
        if (rawPoints >= 600) return 100;
        else if (rawPoints >= 400) return 50;
        else if (rawPoints >= 200) return 25;
        else return 10;
    }

    private int calculateMatchPoints() {
        return skillPoints + bonusPoints;
    }

    public int getMatchID() { return matchID; }
    
    public int getRawPoints() { return rawPoints; }
    public int getSkillPoints() { return skillPoints; }
    public int getBonusPoints() { return bonusPoints; }
    public int getMatchPoints() { return matchPoints; }
    
    public Game[] getGames() { return copyGames(games); }
    public int[] getRounds() { return rounds.clone(); }

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

    @Override
    public int hashCode() {
        return Objects.hash(matchID, matchPoints, Arrays.hashCode(rounds), gamer, Arrays.hashCode(games));
    }
}
