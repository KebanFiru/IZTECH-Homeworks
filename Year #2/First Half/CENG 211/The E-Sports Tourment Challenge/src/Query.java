import java.util.Arrays;
import java.util.Locale;

/**
 * Handles all query operations for tournament statistics.
 * Provides methods to find highest/lowest scoring matches, gamers, and calculate medal distributions.
 */
public class Query {

    private Match[][] matches;
    private Gamer[] gamers;

    private Match highestScoringMatch;
    private Match lowestScoringMatch;
    private Match lowestBonusPointMatch;

    private Gamer highestScoringGamer;

    private PointsBoard pointsBoard;

    private String highestScoringGamerMedal;
    private double highestScoringGamerAverage;
    private int highestScoringGamerTotalPoints;

    private double goldMedalCount;
    private double silverMedalCount;
    private double bronzeMedalCount;
    private double noneMedalCount;

    private int totalTournamentPoint;

    /**
     * Constructor to create a Query object.
     * 
     * @param playedMatches 2D array of all matches played
     * @param gamers Array of all gamers
     * @param pointBoard PointsBoard containing calculated statistics
     * @throws IllegalArgumentException if validation fails
     */
    public Query( Match[][] playedMatches, Gamer[] gamers, PointsBoard pointBoard){

        if(playedMatches == null) {
            throw new IllegalArgumentException("Played matches array cannot be null");
        }
        if(playedMatches.length == 0) {
            throw new IllegalArgumentException("Played matches array cannot be empty");
        }
        
        this.matches = new Match[playedMatches.length][playedMatches[0].length];
        for(int i=0; i<playedMatches.length; i++){
            if(playedMatches[i] == null){
                throw new IllegalArgumentException("Match row at index " + i + " cannot be null");
            }
            for(int j=0; j<playedMatches[i].length; j++){
                if(playedMatches[i][j] == null){
                    throw new IllegalArgumentException("Match at [" + i + "][" + j + "] cannot be null");
                }
                Match match = new Match(playedMatches[i][j]);
                this.matches[i][j] = match;
            }
        }
        
        if(gamers == null){
            throw new IllegalArgumentException("Gamers array cannot be null");
        }
        if(gamers.length == 0){
            throw new IllegalArgumentException("Gamers array cannot be empty");
        }
        
        this.gamers = new Gamer[gamers.length];
        for(int i =0; i<gamers.length; i++){
            if(gamers[i] == null){
                throw new IllegalArgumentException("Gamer at index " + i + " cannot be null");
            }
            Gamer gamer = new Gamer(gamers[i]);
            this.gamers[i] = gamer;
        }
        
        if(pointBoard == null){
            throw new IllegalArgumentException("PointsBoard cannot be null");
        }
        this.pointsBoard = new PointsBoard(pointBoard);

        this.highestScoringGamer = new Gamer(gamers[0]);

        this.highestScoringGamerMedal = "";
        this.highestScoringGamerAverage = 0.0;
        this.highestScoringGamerTotalPoints = 0;

        this.goldMedalCount = 0;
        this.silverMedalCount = 0;
        this.bronzeMedalCount = 0;
        this.noneMedalCount = 0;

        this.totalTournamentPoint = 0;

        this.highestScoringMatch = new Match (playedMatches[0][0]);
        this.lowestScoringMatch = new Match (playedMatches[0][0]);
        this.lowestBonusPointMatch = new Match (playedMatches[0][0]);
    }

    /**
     * Copy constructor to create a deep copy of a Query object.
     * 
     * @param another The Query object to copy from
     * @throws IllegalArgumentException if another is null or contains invalid data
     */
    public Query(Query another){
        if(another == null){
            throw new IllegalArgumentException("Cannot copy from null Query object");
        }
        if(another.matches == null) {
            throw new IllegalArgumentException("Matches in source Query cannot be null");
        }
        
        this.matches = new Match[another.matches.length][another.matches[0].length];
        for(int i=0; i<another.matches.length; i++){
            if(another.matches[i] == null){
                throw new IllegalArgumentException("Match row at index " + i + " cannot be null");
            }
            for(int j=0; j<another.matches[i].length; j++){
                if(another.matches[i][j] == null){
                    throw new IllegalArgumentException("Match at [" + i + "][" + j + "] cannot be null");
                }
                Match match = new Match(another.matches[i][j]);
                this.matches[i][j] = match;
            }
        }

        if(another.gamers == null){
            throw new IllegalArgumentException("Gamers in source Query cannot be null");
        }
        
        this.gamers = new Gamer[another.gamers.length];
        for(int i =0; i<another.gamers.length; i++){
            if(another.gamers[i] == null){
                throw new IllegalArgumentException("Gamer at index " + i + " cannot be null");
            }
            Gamer player = new Gamer(another.gamers[i]);
            this.gamers[i] = player;
        }

        if(another.pointsBoard == null){
            throw new IllegalArgumentException("PointsBoard in source Query cannot be null");
        }
        this.pointsBoard = new PointsBoard(another.pointsBoard);

        this.highestScoringGamer = new Gamer(another.gamers[0]);

        this.highestScoringGamerMedal = another.highestScoringGamerMedal;
        this.highestScoringGamerAverage = another.highestScoringGamerAverage;
        this.highestScoringGamerTotalPoints = another.highestScoringGamerTotalPoints;

        this.goldMedalCount = another.goldMedalCount;
        this.silverMedalCount = another.silverMedalCount;
        this.bronzeMedalCount = another.bronzeMedalCount;
        this.noneMedalCount = another.noneMedalCount;

        this.totalTournamentPoint = another.totalTournamentPoint;

        this.highestScoringMatch = new Match (matches[0][0]);
        this.lowestScoringMatch = new Match (matches[0][0]);
        this.lowestBonusPointMatch = new Match (matches[0][0]);
    }

    /**
     * Finds and returns formatted information about the highest scoring match.
     * 
     * @return Formatted string containing match details
     */
    public String getHighestScoringMatch(){
        findHighestScoringMatch();

        int highestScoringMatchID = highestScoringMatch.getMatchID();
        Game[] highestScoringMatchGames = highestScoringMatch.getGames();
        String[] highestScoringMatchGamesNames = new String[Match.NUM_GAMES];
        int[] highestScoringMatchGamesRounds = highestScoringMatch.getRounds();
        int highestScoringMatchSkillPoints = highestScoringMatch.getSkillPoints();
        int highestScoringMatchRawPoints = highestScoringMatch.getRawPoints();
        int highestScoringMatchBonusPoints = highestScoringMatch.getBonusPoints();
        int highestScoringMatchMatchPoints = highestScoringMatch.getMatchPoints();

        for(int i =0; i<highestScoringMatchGames.length; i++){
            highestScoringMatchGamesNames[i] = highestScoringMatchGames[i].getGameName();
        }

        return String.format("""
                             Highest-Scoring Match: 
                             Match ID: %d 
                             Games: %s 
                             Rounds: %s 
                             Raw Points: %d 
                             Skill Points: %d 
                             Bonus Points: %d 
                             Match Points: %d
                             """, highestScoringMatchID,
                                                  Arrays.toString(highestScoringMatchGamesNames),
                                                  Arrays.toString(highestScoringMatchGamesRounds),
                                                  highestScoringMatchRawPoints,
                                                  highestScoringMatchSkillPoints,
                                                  highestScoringMatchBonusPoints,
                                                  highestScoringMatchMatchPoints);

    }

    /**
     * Finds and returns formatted information about the lowest scoring match.
     * Also includes the most contributing game in that match.
     * 
     * @return Formatted string containing match details and most contributing game
     */
    public String getLowestScoringMatch(){
        findLowestScoringMatch();

        int lowestScoringMatchID = lowestScoringMatch.getMatchID();
        Game[] lowestScoringMatchGames = lowestScoringMatch.getGames();
        String[] lowestScoringMatchGamesNames = new String[Match.NUM_GAMES];
        int[] lowestScoringMatchGamesRounds = lowestScoringMatch.getRounds();
        int lowestScoringMatchSkillPoints = lowestScoringMatch.getSkillPoints();
        int lowestScoringMatchRawPoints = lowestScoringMatch.getRawPoints();
        int lowestScoringMatchBonusPoints = lowestScoringMatch.getBonusPoints();
        int lowestScoringMatchMatchPoints = lowestScoringMatch.getMatchPoints();
        String lowestScoringMatchMostContributedGame = lowestScoringMatchGames[0].getGameName();
        int lowestScoringMatchMostContributedGameRounds = lowestScoringMatchGamesRounds[0];
        int lowestScoringMatchMostContributedGamePoints = lowestScoringMatchGames[0].getBasePointPerRound();


        for(int i =0; i<lowestScoringMatchGames.length; i++){
            int tempGameContrubition =  lowestScoringMatchGamesRounds[i] * lowestScoringMatchGames[i].getBasePointPerRound();
            if(tempGameContrubition>lowestScoringMatchMostContributedGameRounds*lowestScoringMatchMostContributedGamePoints){
                lowestScoringMatchMostContributedGameRounds = lowestScoringMatchGamesRounds[i];
                lowestScoringMatchMostContributedGamePoints = lowestScoringMatchGames[i].getBasePointPerRound();
                lowestScoringMatchMostContributedGame = lowestScoringMatchGames[i].getGameName();
            }
        }

        for(int i =0; i<lowestScoringMatchGames.length; i++){
            lowestScoringMatchGamesNames[i] = lowestScoringMatchGames[i].getGameName();
        }

        return String.format("""
                             Lowest-Scoring Match: 
                             Match ID: %d 
                             Games: %s 
                             Rounds: %s 
                             Raw Points: %d 
                             Skill Points: %d 
                             Bonus Points: %d 
                             Match Points: %d
                         
                             Most Contributing Game in this Match:
                             Game: %s
                             Contribution: %d rounds × %d points = %d
                             """,lowestScoringMatchID ,
                                                  Arrays.toString(lowestScoringMatchGamesNames),
                                                  Arrays.toString(lowestScoringMatchGamesRounds),
                                                  lowestScoringMatchRawPoints,
                                                  lowestScoringMatchSkillPoints,
                                                  lowestScoringMatchBonusPoints,
                                                  lowestScoringMatchMatchPoints,
                                                  lowestScoringMatchMostContributedGame,
                                                  lowestScoringMatchMostContributedGameRounds,
                                                  lowestScoringMatchMostContributedGamePoints,
                                                  lowestScoringMatchMostContributedGameRounds * lowestScoringMatchMostContributedGamePoints);

    }

    /**
     * Finds and returns formatted information about the match with the lowest bonus points.
     * 
     * @return Formatted string containing match details
     */
    public String getMatchWithTheLowestBonusPoints(){
        findMatchWithTheLowestBonusPoints();

        int lowestBonusPointMatchID = lowestBonusPointMatch.getMatchID();
        Game[] lowestBonusPointMatchGames = lowestBonusPointMatch.getGames();
        String[] lowestBonusPointMatchGamesNames = new String[Match.NUM_GAMES];
        int[] lowestBonusPointMatchGamesRounds = lowestBonusPointMatch.getRounds();
        int lowestBonusPointMatchSkillPoints = lowestBonusPointMatch.getSkillPoints();
        int lowestBonusPointMatchBonusPoints = lowestBonusPointMatch.getBonusPoints();
        int lowestBonusPointMatchPoints = lowestBonusPointMatch.getMatchPoints();

        for(int i =0; i<lowestBonusPointMatchGames.length; i++){
            lowestBonusPointMatchGamesNames[i] = lowestBonusPointMatchGames[i].getGameName();
        }

        return String.format("""
                             Match with Lowest Bonus Points:
                             Match ID: %d 
                             Games: %s 
                             Rounds: %s 
                             Skill Points: %d 
                             Bonus Points: %d 
                             Match Points: %d
                             """, lowestBonusPointMatchID,
                                  Arrays.toString(lowestBonusPointMatchGamesNames),
                                  Arrays.toString(lowestBonusPointMatchGamesRounds),
                                  lowestBonusPointMatchSkillPoints,
                                  lowestBonusPointMatchBonusPoints,
                                  lowestBonusPointMatchPoints);
    }

    /**
     * Finds and returns formatted information about the highest scoring gamer.
     * 
     * @return Formatted string containing gamer details, total points, average, and medal
     */
    public String getHighestScoringGamer(){
        findHighestScoringGamer();

        String highestScoringGamerNickname = highestScoringGamer.getNickname();
        String highestScoringGamerName = highestScoringGamer.getName();

        return String.format(Locale.US, """
                            Highest-Scoring Gamer
                            Nickname: %s
                            Name: %s
                            Total Points: %d
                            Average Per Match: %.2f
                            Medal: %s
                            """,highestScoringGamerNickname,
                                highestScoringGamerName,
                                highestScoringGamerTotalPoints,
                                highestScoringGamerAverage,
                                highestScoringGamerMedal);
    }

    /**
     * Calculates and returns the total tournament points across all matches.
     * 
     * @return Formatted string with total tournament points
     */
    public String getTotalTournamentPoints(){
        findTotalTournamentPoint();

        return String.format(Locale.US, "Total Tournament Points across 1500 matches: %,d\n", totalTournamentPoint);
    }
    
    /**
     * Calculates and returns medal distribution among all gamers.
     * Shows count and percentage for each medal type (GOLD, SILVER, BRONZE, NONE).
     * 
     * @return Formatted string with medal distribution statistics
     */
    public String getMedalDistribution(){
        findMedalDistribution();

        return String.format(Locale.US, """
                            Medal Distribution:
                            GOLD:   %.0f gamers (%.1f%%)
                            SILVER: %.0f gamers (%.1f%%)
                            BRONZE: %.0f gamers (%.1f%%)
                            NONE:   %.0f gamers (%.1f%%)
                            """, goldMedalCount, (goldMedalCount / gamers.length) * 100,
                                 silverMedalCount, (silverMedalCount / gamers.length) * 100,
                                 bronzeMedalCount, (bronzeMedalCount / gamers.length) * 100,
                                 noneMedalCount, (noneMedalCount / gamers.length) * 100);
    }


    /**
     * Finds the match with the highest match points.
     */
    private void findHighestScoringMatch() {
        int highestScoringMatchPoint = matches[0][0].getMatchPoints();

        for(int i =0; i< matches.length; i++) {
            for (int j = 0; j < matches[i].length; j++) {
                if (matches[i][j].getMatchPoints() > highestScoringMatchPoint) {
                    highestScoringMatchPoint = matches[i][j].getMatchPoints();
                    highestScoringMatch = matches[i][j];
                }
            }
        }
    }
    
    /**
     * Finds the match with the lowest match points.
     */
    private void findLowestScoringMatch(){
        int lowestScoringMatchPoint = matches[0][0].getMatchPoints();

        for(int i =0; i< matches.length; i++) {
            for (int j = 0; j < matches[i].length; j++) {
                if (matches[i][j].getMatchPoints() < lowestScoringMatchPoint) {
                    lowestScoringMatchPoint = matches[i][j].getMatchPoints();
                    lowestScoringMatch = matches[i][j];
                }
            }
        }
    }
    
    /**
     * Finds the match with the lowest bonus points.
     */
    private void findMatchWithTheLowestBonusPoints(){
        int lowestBonusPoint = matches[0][0].getBonusPoints();

        for(int i =0; i<matches.length; i++){
            for(int j=0; j<matches[i].length; j++){
                if(matches[i][j].getBonusPoints() < lowestBonusPoint){
                    lowestBonusPoint = matches[i][j].getBonusPoints();
                    lowestBonusPointMatch = matches[i][j];

                }
            }
        }
    }
    
    /**
     * Finds the gamer with the highest total points.
     */
    private void findHighestScoringGamer(){
        int totalPoint = 0;
        for(int i =0; i<gamers.length; i++){
            if(pointsBoard.getTotalPoints(i) > totalPoint){
                totalPoint = pointsBoard.getTotalPoints(i);
                highestScoringGamerTotalPoints = pointsBoard.getTotalPoints(i);
                highestScoringGamerMedal = pointsBoard.getMedal(i);
                highestScoringGamerAverage = pointsBoard.getAveragePerMatch(i);
                highestScoringGamer = gamers[i];

            }
        }

    }
    
    /**
     * Calculates the total tournament points from the PointsBoard.
     */
    private void findTotalTournamentPoint(){

        for(int i=0; i<gamers.length; i++){
            totalTournamentPoint = totalTournamentPoint+pointsBoard.getTotalPoints(i);
        }
    }
    
    /**
     * Counts the number of gamers in each medal category.
     */
    private void findMedalDistribution(){
        for(int i=0; i<gamers.length; i++){
            if( "GOLD".equals(pointsBoard.getMedal(i))){
                goldMedalCount += 1;
            }
            else if("SILVER".equals(pointsBoard.getMedal(i))){
                silverMedalCount += 1;
            }
            else if("BRONZE".equals(pointsBoard.getMedal(i))){
                bronzeMedalCount += 1;
            }
            else{
                noneMedalCount += 1;
            }
        }
    }

}
