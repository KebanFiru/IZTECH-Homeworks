public class ESportManagementApp {

    private static final String GAMES_CSV = "files/games.csv";
    private static final String GAMERS_CSV = "files/gamers.csv";

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