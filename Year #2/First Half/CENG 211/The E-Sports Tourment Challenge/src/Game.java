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
     * @throws IllegalArgumentException if validation fails
     */
    public Game(int id, String gameName, int basePointPerRound) {
        if (id <= 0) {
            throw new IllegalArgumentException("Game ID must be positive");
        }
        if (gameName == null || gameName.trim().isEmpty()) {
            throw new IllegalArgumentException("Game name cannot be null or empty");
        }
        if (basePointPerRound <= 0) {
            throw new IllegalArgumentException("Base point per round must be positive");
        }
        
        this.id = id;
        this.gameName = gameName.trim();
        this.basePointPerRound = basePointPerRound;
    }

    /**
     * Copy constructor to create a Game object from another Game.
     * 
     * @param other The Game object to copy
     * @throws IllegalArgumentException if other is null
     */
    public Game(Game other) {
        if (other == null) {
            throw new IllegalArgumentException("Cannot copy from null Game object");
        }
        
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
     * @throws IllegalArgumentException if id is not positive
     */
    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Game ID must be positive");
        }
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
     * @throws IllegalArgumentException if gameName is null or empty
     */
    public void setGameName(String gameName) {
        if (gameName == null || gameName.trim().isEmpty()) {
            throw new IllegalArgumentException("Game name cannot be null or empty");
        }
        this.gameName = gameName.trim();
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
     * @throws IllegalArgumentException if basePointPerRound is not positive
     */
    public void setBasePointPerRound(int basePointPerRound) {
        if (basePointPerRound <= 0) {
            throw new IllegalArgumentException("Base point per round must be positive");
        }
        this.basePointPerRound = basePointPerRound;
    }

    /**
     * Returns a string representation of the game.
     * 
     * @return String containing game details
     */
    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", gameName='" + gameName + '\'' +
                ", basePointPerRound=" + basePointPerRound +
                '}';
    }

    /**
     * Checks if two Game objects are equal based on their attributes.
     * 
     * @param obj The object to compare with
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Game game = (Game) obj;
        return id == game.id &&
               basePointPerRound == game.basePointPerRound &&
               (gameName != null ? gameName.equals(game.gameName) : game.gameName == null);
    }
}