public class ESportManagementApp {

    private static final String GAMES_CSV = "files/games.csv";
    private static final String GAMERS_CSV = "files/gamers.csv";
    private static final int MATCHES_PER_GAMER = 15;

    public static void main(String[] args) {

        Game[] games = FileIO.readGames(GAMES_CSV);
        Gamer[] gamers = FileIO.readGamers(GAMERS_CSV);

        Match[][] matches = new Match[gamers.length][MATCHES_PER_GAMER];

        MatchManagement matchManagement = new MatchManagement(gamers,games);

        matchManagement.generateMatches();

        PointsBoard pointsBoard = new PointsBoard(gamers, matches);

        Query query = new Query(matches, gamers, pointsBoard);
        Query copyQuery = new Query(query);

        copyQuery.getHighestScoringMatch();
        copyQuery.getLowestScoringMatch();
        copyQuery.getMatchWithTheLowestBonusPoints();
        copyQuery.getHighestScoringGamer();
        copyQuery.getMedalDistribution();


    }

}