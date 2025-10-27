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
            matches = new Match[playedMatches.length][playedMatches[0].length];
            for(int i=0; i<playedMatches.length; i++){
                for(int j=0; j<playedMatches[i].length; j++){
                    Match match = new Match(playedMatches[i][j]);
                    matches[i][j] = match;
                }
            }
        }
        if(players == null){
            throw new IllegalArgumentException();
        }
        else{
            gamers = new Gamer[players.length];
            for(int i =0; i<players.length; i++){
                Gamer player = new Gamer(players[i]);
                gamers[i] = player;
            }
        }
        this.pointsBoard = new PointBoard(pointBoard);

        highestScoringGamer = new Gamer(players[0]);

        highestScoringGamerMedal = "";
        highestScoringGamerAverage = 0.0;
        highestScoringGamerTotalPoints = 0;

        goldMedalCount = 0;
        silverMedalCount = 0;
        bronzeMedalCount = 0;
        noneMedalCount = 0;

        totalTournamentPoint = 0;

        this.highestScoringMatch = new Match (playedMatches[0][0]);
        this.lowestScoringMatch = new Match (playedMatches[0][0]);
        this.lowestBonusPointMatch = new Match (playedMatches[0][0]);
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
                             Lowest-Scoring Match: +
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
                             Match with Lowest Bonus Points:\n 
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
                            """, goldMedalCount, (double)goldMedalCount/100,
                                 silverMedalCount, (double)silverMedalCount/100,
                                 bronzeMedalCount, (double)bronzeMedalCount/100,
                                 noneMedalCount, (double)noneMedalCount/100);
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
        for(Gamer gamer: gamers){
            if( "GOLD".equals(pointsBoard.getMedal())){
                goldMedalCount += 1;
            }
            else if("SILVER".equals(pointsBoard.getMedal())){
                silverMedalCount += 1;
            }
            else if("BRONZE".equals(pointsBoard.getMedal())){
                bronzeMedalCount += 1;
            }
            else{
                noneMedalCount += 1;
            }
        }
    }

}
