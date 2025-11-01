/**
 * Represents a game in the E-Sports tournament.
 * Each game has a unique ID, name, and base points awarded per round.
 */
public class Game {
    private int id;
    private String gameName;
    private int basePointPerRound;

    /**
     * Constructor to create a Game object.
     * 
     * @param id The unique identifier for the game
     * @param gameName The name of the game
     * @param basePointPerRound Points awarded per round for this game
     */
    public Game(int id, String gameName, int basePointPerRound) {
        this.id = id;
        this.gameName = gameName;
        this.basePointPerRound = basePointPerRound;
    }

    /**
     * Copy constructor to create a new Game object from an existing one.
     * 
     * @param other The Game object to copy from
     */
    public Game(Game other) {
        this.id = other.id;
        this.gameName = other.gameName;
        this.basePointPerRound = other.basePointPerRound;
    }

    /**
     * Gets the game ID.
     * 
     * @return The game's unique identifier
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the game ID.
     * 
     * @param id The unique identifier to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the game name.
     * 
     * @return The name of the game
     */
    public String getGameName() {
        return gameName;
    }

    /**
     * Sets the game name.
     * 
     * @param gameName The name to set
     */
    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    /**
     * Gets the base points per round.
     * 
     * @return Points awarded per round
     */
    public int getBasePointPerRound() {
        return basePointPerRound;
    }

    /**
     * Sets the base points per round.
     * 
     * @param basePointPerRound Points to award per round
     */
    public void setBasePointPerRound(int basePointPerRound) {
        this.basePointPerRound = basePointPerRound;
    }

    /**
     * Returns a string representation of the game.
     * 
     * @return String containing game details
     */
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", gameName='" + gameName + '\'' +
                ", basePointPerRound=" + basePointPerRound +
                '}';
    }

    /**
     * Compares this game with another for equality.
     * 
     * @param other The other Game object to compare with
     * @return true if both games are equal, false otherwise
     */
    public boolean equals(Game other) {
        return this.id == other.id &&
               this.gameName.equals(other.gameName) &&
               this.basePointPerRound == other.basePointPerRound;
    }
}