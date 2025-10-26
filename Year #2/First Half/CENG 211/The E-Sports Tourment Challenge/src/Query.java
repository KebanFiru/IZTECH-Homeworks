import java.util.Arrays;

public class Query {

    private Match[][] matches;
    private Gamer[] gamers;

    private Match highestScoringMatch;
    private Match lowestScoringMatch;
    private Match lowestBonusPointMatch;

    private Gamer highestScoringGamer;
    private String highestScoringGamerMedal;
    private double highestScoringGamerAvarage;
    private PointsBoard pointsBoard;

    private int totalTournamentPoint;

    public Query( Match[][] playedMatches, Gamer[] players, PointsBoard pointBoard){

        this.matches = playedMatches;

        this.gamers = players;
        this.pointsBoard = pointBoard;

        highestScoringGamer = players[0];
        highestScoringGamerMedal = "";
        highestScoringGamerAvarage = 0.0;

        totalTournamentPoint = 0;

        this.highestScoringMatch = playedMatches[0][0];
        this.lowestScoringMatch = playedMatches[0][0];
        this.lowestBonusPointMatch = playedMatches[0][0];

    }

    public String getHighestScoringMatch(){
        findHighestScoringMatch();

        int highestScoringMatchID = highestScoringMatch.getMatchID;
        Match[] highestScoringMatchGames = highestScoringMatch.getGames;
        int[] highestScoringMatchGamesRounds = new int[3];
        int highestScoringMatchSkillPoints = highestScoringMatch.getSkillPoint;
        int highestScoringMatchRawPoints = highestScoringMatch. getRawPoint;
        int highestScoringMatchBonusPoints = highestScoringMatch.getBonusPoint;
        int highestScoringMatchMatchPoints = highestScoringMatch.getMatchPoint;

        for(int i=0; i<highestScoringMatchGames.length; i++){
            highestScoringMatchGamesRounds[i] = highestScoringMatchGames[i].getRounds;
        }

        return String.format("""
                             Highest-Scoring Match:\n +
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

        int lowesttScoringMatchID = lowestScoringMatch.getMatchID;
        Match[] lowestScoringMatchGames = lowestScoringMatch.getGames;
        int[] lowestScoringMatchGamesRounds = new int[3];
        int lowestScoringMatchSkillPoints = lowestScoringMatch.getSkillPoint;
        int lowestScoringMatchRawPoints = lowestScoringMatch. getRawPoint;
        int lowestScoringMatchBonusPoints = lowestScoringMatch.getBonusPoint;
        int lowestScoringMatchMatchPoints = lowestScoringMatch.getMatchPoint;
        int [] lowestScoringMatchGamesContribution = new int[3];
        String lowestScoringMatchMostContributedGame = "";
        int lowestScoringMatchMostContributedGameRounds = 0;
        int lowestScoringMatchMostContributedGamePoints = 0;

        for(int i=0; i<lowestScoringMatchGames.length; i++){
            lowestScoringMatchGamesRounds[i] = lowestScoringMatchGames[i].getRounds;
            lowestScoringMatchGamesContribution[i] = lowestScoringMatchGames[i].getRounds * lowestScoringMatchGames[i].getPoints;
        }
        int lowestScoringMatchGamesContributionPoint = lowestScoringMatchGamesContribution[0];
        for(int i=0; i<lowestScoringMatchGamesContribution.length; i++){
            if(lowestScoringMatchGamesContributionPoint < lowestScoringMatchGamesContribution[i]){
                lowestScoringMatchGamesContributionPoint = lowestScoringMatchGamesContribution[i];
                lowestScoringMatchMostContributedGame = lowestScoringMatchGames[i].getName();
                lowestScoringMatchMostContributedGameRounds = lowestScoringMatchGames[i].getRounds;
                lowestScoringMatchMostContributedGamePoints = lowestScoringMatchGames[i].getGamePoints;
            }
        }

        return String.format("""
                             Lowest-Scoring Match:\n +
                             Match ID: %d 
                             Games: %s 
                             Rounds: %s 
                             Raw Points: %d 
                             Skill Points: %d 
                             Bonus Points: %d 
                             Match Points: %d
                             Most Contributing Game in this Match:
                             Game: %s
                             Contribution: %d rounds, %d points = %d""", lowesttScoringMatchID,
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
        Match[] lowestBonusPointMatchGames = lowestBonusPointMatch.getGames;
        int[] lowestBonusPointMatchGamesRounds = new int[3];
        int lowestBonusPointMatchSkillPoints = lowestBonusPointMatch.getSkillPoint;
        int lowestBonusPointMatchBonusPoints = lowestBonusPointMatch.getBonusPoint;
        int lowestBonusPointMatchPoints = lowestBonusPointMatch.getMatchPoint;

        for(int i=0; i<lowestBonusPointMatchGames.length; i++){
            lowestBonusPointMatchGamesRounds[i] = lowestBonusPointMatchGames[i].getRounds;
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

        return String.format("""
                            Highest-Scoring Gamer
                            Nickname: %s
                            Name: %s
                            Total Points: %d
                            Avarage Per Match: %.2f
                            Medal: %s
                            """,);
    }

    public String getTotalTournamentPoints(){
        findTotalTournamentPoint();

        return String.format("Total Tournament Points across 1500 matches:%d", totalTournamentPoint);
    }
    public String getMedalDistribution(){
        findMetalDistrubition();

        return String.format("""
                            Medal Distribution:
                            GOLD: %d gamers(%.1f %)
                            SILVER: %d gamers(%.1f %)
                            BRONZE: %d gamers(%.1f %)
                            NONE: %d gamers(%.1f %)
                            """, );
    }


    private void findHighestScoringMatch() {
        int highestScoringMatchPoint = matches[0][0].getMatchPoint;

        for(int i =0; i< matches.length; i++) {
            for (int j = 0; j < matches[i].length; j++) {
                if (matches[i][j].getMatchPoint > highestScoringMatchPoint) {
                    highestScoringMatchPoint = matches[i][j].getMatchPoint;
                    highestScoringMatch = matches[i][j];
                }
            }
        }
    }
    private void findLowestScoringMatch(){
        int lowestScoringMatchPoint = matches[0][0].getMatchPoint;

        for(int i =0; i< matches.length; i++) {
            for (int j = 0; j < matches[i].length; j++) {
                if (matches[i][j].getMatchPoint < lowestScoringMatchPoint) {
                    lowestScoringMatchPoint = matches[i][j].getMatchPoint;
                    lowestScoringMatch = matches[i][j];
                }
            }
        }
    }
    private void findMatchWithTheLowestBonusPoints(){
        int lowestBonusPoint = matches[0][0].getBonusPoint;

        for(int i =0; i<matches.length; i++){
            for(int j=0; j<matches[i].length; j++){
                if(matches[i][j].getBonusPoint < lowestBonusPoint){
                    lowestBonusPoint = matches[i][j].getBonusPoint;
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
                highestScoringGamerMedal = pointsBoard.getMedal(i);
                highestScoringGamerAvarage = pointsBoard.getAvaragePerMatch(i);
                highestScoringGamer = gamers[i];

            }
        }

    }
    private void findTotalTournamentPoint(){

    }
    private void findMetalDistrubition(){

    }

}
