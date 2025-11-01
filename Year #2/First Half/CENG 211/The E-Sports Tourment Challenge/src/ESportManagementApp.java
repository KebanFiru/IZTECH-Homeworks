public class ESportManagementApp {

    private static final String GAMES_CSV = "games.csv";
    private static final String GAMERS_CSV = "gamers.csv";
    private static final int MATCHES_PER_GAMER = 15;

    public static void main(String[] args) {

        Game[] games = FileIO.readGames(GAMES_CSV);
        Gamer[] gamers = FileIO.readGamers(GAMERS_CSV);

        System.out.println(GAMERS_CSV);

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