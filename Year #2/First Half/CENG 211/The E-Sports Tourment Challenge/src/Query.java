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

    private int goldMedalCount;
    private int silverMedalCount;
    private int bronzeMedalCount;
    private int noneMedalCount;

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
        this.pointsBoard = new PointBoard(pointBoard);

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

        this.pointsBoard = new PointBoard(another.pointBoard);

        this.highestScoringGamer = new Gamer(another.players[0]);

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
        Match[] highestScoringMatchGames = highestScoringMatch.getGames();
        int[] highestScoringMatchGamesRounds = new int[3];
        int highestScoringMatchSkillPoints = highestScoringMatch.getSkillPoint();
        int highestScoringMatchRawPoints = highestScoringMatch.getRawPoint();
        int highestScoringMatchBonusPoints = highestScoringMatch.getBonusPoint();
        int highestScoringMatchMatchPoints = highestScoringMatch.getMatchPoint();

        for(int i=0; i<highestScoringMatchGames.length; i++){
            highestScoringMatchGamesRounds[i] = highestScoringMatchGames[i].getRounds();
        }

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
        Match[] lowestScoringMatchGames = lowestScoringMatch.getGames();
        int[] lowestScoringMatchGamesRounds = new int[3];
        int lowestScoringMatchSkillPoints = lowestScoringMatch.getSkillPoint();
        int lowestScoringMatchRawPoints = lowestScoringMatch.getRawPoint();
        int lowestScoringMatchBonusPoints = lowestScoringMatch.getBonusPoint();
        int lowestScoringMatchMatchPoints = lowestScoringMatch.getMatchPoint();
        int [] lowestScoringMatchGamesContribution = new int[3];
        String lowestScoringMatchMostContributedGame = "";
        int lowestScoringMatchMostContributedGameRounds = 0;
        int lowestScoringMatchMostContributedGamePoints = 0;

        for(int i=0; i<lowestScoringMatchGames.length; i++){
            lowestScoringMatchGamesRounds[i] = lowestScoringMatchGames[i].getRounds();
            lowestScoringMatchGamesContribution[i] = lowestScoringMatchGames[i].getRounds() * lowestScoringMatchGames[i].getPoints();
        }
        int lowestScoringMatchGamesContributionPoint = lowestScoringMatchGamesContribution[0];
        for(int i=0; i<lowestScoringMatchGamesContribution.length; i++){
            if(lowestScoringMatchGamesContributionPoint < lowestScoringMatchGamesContribution[i]){
                lowestScoringMatchGamesContributionPoint = lowestScoringMatchGamesContribution[i];
                lowestScoringMatchMostContributedGame = lowestScoringMatchGames[i].getName();
                lowestScoringMatchMostContributedGameRounds = lowestScoringMatchGames[i].getRounds();
                lowestScoringMatchMostContributedGamePoints = lowestScoringMatchGames[i].getGamePoints();
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
                             Contribution: %d rounds, %d points = %d""", lowestScoringMatchID,
                                                  Arrays.toString(lowestScoringMatchGames),
                                                  Arrays.toString(lowestScoringMatchGamesRounds),
                                                  lowestScoringMatchRawPoints,
                                                  lowestScoringMatchSkillPoints,
                                                  lowestScoringMatchBonusPoints,
                                                  lowestScoringMatchMatchPoints,
                                                  lowestScoringMatchMostContributedGame,
                                                  lowestScoringMatchMostContributedGamePoints,
                                                  lowestScoringMatchMostContributedGameRounds,
                                                  lowestScoringMatchGamesContributionPoint);

    }

    public String getMatchWithTheLowestBonusPoints(){
        findMatchWithTheLowestBonusPoints();

        int lowestBonusPointMatchID = lowestBonusPointMatch.getMatchID();
        Match[] lowestBonusPointMatchGames = lowestBonusPointMatch.getGames();
        int[] lowestBonusPointMatchGamesRounds = new int[3];
        int lowestBonusPointMatchSkillPoints = lowestBonusPointMatch.getSkillPoint();
        int lowestBonusPointMatchBonusPoints = lowestBonusPointMatch.getBonusPoint();
        int lowestBonusPointMatchPoints = lowestBonusPointMatch.getMatchPoint();

        for(int i=0; i<lowestBonusPointMatchGames.length; i++){
            lowestBonusPointMatchGamesRounds[i] = lowestBonusPointMatchGames[i].getRounds();
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
                            GOLD: %d gamers(%.1f %)
                            SILVER: %d gamers(%.1f %)
                            BRONZE: %d gamers(%.1f %)
                            NONE: %d gamers(%.1f %)
                            """, goldMedalCount, (double)goldMedalCount/gamers.length*100,
                                 silverMedalCount, (double)silverMedalCount/gamers.length*100,
                                 bronzeMedalCount, (double)bronzeMedalCount/gamers.length*100,
                                 noneMedalCount, (double)noneMedalCount/gamers.length*100);
    }


    private void findHighestScoringMatch() {
        int highestScoringMatchPoint = matches[0][0].getMatchPoint();

        for(int i =0; i< matches.length; i++) {
            for (int j = 0; j < matches[i].length; j++) {
                if (matches[i][j].getMatchPoint() > highestScoringMatchPoint) {
                    highestScoringMatchPoint = matches[i][j].getMatchPoint();
                    highestScoringMatch = matches[i][j];
                }
            }
        }
    }
    private void findLowestScoringMatch(){
        int lowestScoringMatchPoint = matches[0][0].getMatchPoint();

        for(int i =0; i< matches.length; i++) {
            for (int j = 0; j < matches[i].length; j++) {
                if (matches[i][j].getMatchPoint() < lowestScoringMatchPoint) {
                    lowestScoringMatchPoint = matches[i][j].getMatchPoint();
                    lowestScoringMatch = matches[i][j];
                }
            }
        }
    }
    private void findMatchWithTheLowestBonusPoints(){
        int lowestBonusPoint = matches[0][0].getBonusPoint();

        for(int i =0; i<matches.length; i++){
            for(int j=0; j<matches[i].length; j++){
                if(matches[i][j].getBonusPoint() < lowestBonusPoint){
                    lowestBonusPoint = matches[i][j].getBonusPoint();
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
