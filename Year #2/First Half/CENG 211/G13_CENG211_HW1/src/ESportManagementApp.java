/**
 * Main application class for the E-Sports Tournament Management System.
 * Loads data, generates matches, calculates statistics, and displays tournament results.
 */
public class ESportManagementApp {

    /** Path to the games CSV file. */
    private static final String GAMES_CSV = "files/games.csv";
    
    /** Path to the gamers CSV file. */
    private static final String GAMERS_CSV = "files/gamers.csv";

    /**
     * Main method to run the E-Sports tournament application.
     * Loads games and gamers from CSV files, generates matches, calculates points,
     * and displays various tournament statistics and queries.
     * 
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {

        Game[] games = FileIO.readGames(GAMES_CSV);
        if (games == null || games.length == 0) {
            System.err.println("Error: Could not load games from " + GAMES_CSV);
            return;
        }
        
        Gamer[] gamers = FileIO.readGamers(GAMERS_CSV);
        if (gamers == null || gamers.length == 0) {
            System.err.println("Error: Could not load gamers from " + GAMERS_CSV);
            return;
        }

        MatchManagement matchManagement = new MatchManagement(gamers,games);

        matchManagement.generateMatches();
        PointsBoard pointsBoard = new PointsBoard(gamers, matchManagement.getAllMatches());

        Query query = new Query(matchManagement.getAllMatches(), gamers, pointsBoard);
        Query copyQuery = new Query(query);

        System.out.println(copyQuery.getHighestScoringMatch());
        System.out.println(copyQuery.getLowestScoringMatch());
        System.out.println(copyQuery.getMatchWithTheLowestBonusPoints());
        System.out.println(copyQuery.getHighestScoringGamer());
        System.out.println(copyQuery.getTotalTournamentPoints());
        System.out.println(copyQuery.getMedalDistribution());


    }

}