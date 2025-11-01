import java.util.Arrays;

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

    public Query( Match[][] playedMatches, Gamer[] players, PointsBoard pointBoard){

        if(playedMatches == null) {
            throw new IllegalArgumentException();
        }
        else{
            this.matches = new Match[playedMatches.length][playedMatches[0].length];
            for(int i=0; i<playedMatches.length; i++){
                for(int j=0; j<playedMatches[i].length; j++){
                    Match match = new Match(playedMatches[i][j]);
                    this.matches[i][j] = match;
                }
            }
        }
        if(players == null){
            throw new IllegalArgumentException();
        }
        else{
            this.gamers = new Gamer[players.length];
            for(int i =0; i<players.length; i++){
                Gamer player = new Gamer(players[i]);
                this.gamers[i] = player;
            }
        }
        this.pointsBoard = new PointsBoard(pointBoard);

        this.highestScoringGamer = new Gamer(players[0]);

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

    public Query(Query another){
        if(another.matches == null) {
            throw new IllegalArgumentException();
        }
        else{
            this.matches = new Match[another.matches.length][another.matches[0].length];
            for(int i=0; i<another.matches.length; i++){
                for(int j=0; j<another.matches[i].length; j++){
                    Match match = new Match(another.matches[i][j]);
                    this.matches[i][j] = match;
                }
            }
        }

        if(another.gamers == null){
            throw new IllegalArgumentException();
        }
        else{
            this.gamers = new Gamer[another.gamers.length];
            for(int i =0; i<another.gamers.length; i++){
                Gamer player = new Gamer(another.gamers[i]);
                this.gamers[i] = player;
            }
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

    public String getHighestScoringMatch(){
        findHighestScoringMatch();

        int highestScoringMatchID = highestScoringMatch.getMatchID();
        Game[] highestScoringMatchGames = highestScoringMatch.getGames();
        int[] highestScoringMatchGamesRounds = highestScoringMatch.getRounds();
        int highestScoringMatchSkillPoints = highestScoringMatch.getSkillPoints();
        int highestScoringMatchRawPoints = highestScoringMatch.getRawPoints();
        int highestScoringMatchBonusPoints = highestScoringMatch.getBonusPoints();
        int highestScoringMatchMatchPoints = highestScoringMatch.getMatchPoints();

        return String.format("""
                             Highest-Scoring Match: +
                             Match ID: %d 
                             Games: %s 
                             Rounds: %s 
                             Raw Points: %d 
                             Skill Points: %d 
                             Bonus Points: %d 
                             Match Points: %d""", highestScoringMatchID,
                                                  Arrays.toString(highestScoringMatchGames),
                                                  Arrays.toString(highestScoringMatchGamesRounds),
                                                  highestScoringMatchRawPoints,
                                                  highestScoringMatchSkillPoints,
                                                  highestScoringMatchBonusPoints,
                                                  highestScoringMatchMatchPoints);

    }

    public String getLowestScoringMatch(){
        findLowestScoringMatch();

        int lowestScoringMatchID = lowestScoringMatch.getMatchID();
        Game[] lowestScoringMatchGames = lowestScoringMatch.getGames();
        int[] lowestScoringMatchGamesRounds = lowestScoringMatch.getRounds();
        int lowestScoringMatchSkillPoints = lowestScoringMatch.getSkillPoints();
        int lowestScoringMatchRawPoints = lowestScoringMatch.getRawPoints();
        int lowestScoringMatchBonusPoints = lowestScoringMatch.getBonusPoints();
        int lowestScoringMatchMatchPoints = lowestScoringMatch.getMatchPoints();
        String lowestScoringMatchMostContributedGame = "";
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
                             Contribution: %d rounds, %d points = %d""",lowestScoringMatchID ,
                                                  Arrays.toString(lowestScoringMatchGames),
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

    public String getMatchWithTheLowestBonusPoints(){
        findMatchWithTheLowestBonusPoints();

        int lowestBonusPointMatchID = lowestBonusPointMatch.getMatchID();
        Game[] lowestBonusPointMatchGames = lowestBonusPointMatch.getGames();
        int[] lowestBonusPointMatchGamesRounds = lowestBonusPointMatch.getRounds();
        int lowestBonusPointMatchSkillPoints = lowestBonusPointMatch.getSkillPoints();
        int lowestBonusPointMatchBonusPoints = lowestBonusPointMatch.getBonusPoints();
        int lowestBonusPointMatchPoints = lowestBonusPointMatch.getMatchPoints();

        return String.format("""
                             Match with Lowest Bonus Points:
                             Match ID: %d 
                             Games: %s 
                             Rounds: %s 
                             Skill Points: %d 
                             Bonus Points: %d 
                             Match Points: %d
                             """, lowestBonusPointMatchID,
                                  Arrays.toString(lowestBonusPointMatchGames),
                                  Arrays.toString(lowestBonusPointMatchGamesRounds),
                                  lowestBonusPointMatchSkillPoints,
                                  lowestBonusPointMatchBonusPoints,
                                  lowestBonusPointMatchPoints);
    }

    public String getHighestScoringGamer(){
        findHighestScoringGamer();

        String highestScoringGamerNickname = highestScoringGamer.getNickname();
        String highestScoringGamerName = highestScoringGamer.getName();

        return String.format("""
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

    public String getTotalTournamentPoints(){
        findTotalTournamentPoint();

        return String.format("Total Tournament Points across 1500 matches:%d", totalTournamentPoint);
    }
    public String getMedalDistribution(){
        findMedalDistribution();

        return String.format("""
                            Medal Distribution:
                            GOLD: %f gamers(%f)
                            SILVER: %f gamers(%f)
                            BRONZE: %f gamers(%f)
                            NONE: %f gamers(%f)
                            """, goldMedalCount, goldMedalCount*gamers.length/100,
                                 silverMedalCount, silverMedalCount*gamers.length/100,
                                 bronzeMedalCount, bronzeMedalCount*gamers.length/100,
                                 noneMedalCount, noneMedalCount*gamers.length/100);
    }


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
    private void findTotalTournamentPoint(){

        for(int i=0; i<gamers.length; i++){
            totalTournamentPoint = totalTournamentPoint+pointsBoard.getTotalPoints(i);
        }
    }
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
