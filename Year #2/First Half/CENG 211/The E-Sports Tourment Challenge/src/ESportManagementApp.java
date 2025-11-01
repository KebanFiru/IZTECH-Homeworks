public class ESportManagementApp {

    /**
     * Add relative paths of files as static final variables for increase readibility
     * */
    private static final String GAMES_CSV = "files/games.csv";
    private static final String GAMERS_CSV = "files/gamers.csv";

    public static void main(String[] args) {

    /*-------------Define Instances of Necesseray Classes-------------*/
        Game[] games = FileIO.readGames(GAMES_CSV);
        Gamer[] gamers = FileIO.readGamers(GAMERS_CSV);

        MatchManagement matchManagement = new MatchManagement(gamers,games);

        matchManagement.generateMatches();/*This is for generate matches*/
        PointsBoard pointsBoard = new PointsBoard(gamers, matchManagement.getAllMatches());

        Query query = new Query(matchManagement.getAllMatches(), gamers, pointsBoard);
        Query copyQuery = new Query(query);/*I created here a instance of Query with copy constructor for increase safety*/
    /*----------------------------------------------------------------*/

    /*---------Print Information on Screen Gathered by Query---------*/
        System.out.println(copyQuery.getHighestScoringMatch());
        System.out.println(copyQuery.getLowestScoringMatch());
        System.out.println(copyQuery.getMatchWithTheLowestBonusPoints());
        System.out.println(copyQuery.getHighestScoringGamer());
        System.out.println(copyQuery.getMedalDistribution());
    /*----------------------------------------------------------------*/


    }

}