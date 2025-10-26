import java.util.Arrays;

public class Query {

    private int[] totalPoints;
    private Match[][] matches;

    private Match highestScoringMatch;
    private Match lowestScoringMatch;

    public Query(int[] totalPointsArray, Match[][] playedMatches){

        this.totalPoints = totalPointsArray;
        this.matches = playedMatches;

        this.highestScoringMatch = playedMatches[0][0];
        this.lowestScoringMatch = playedMatches[0][0];

    }

    public String getHighestScoringMatch(){
        findHighestScoringMatch();

        int highestScoringMatchID = highestScoringMatch.getMatchID;
        Match[] highestScoringMatchGames = highestScoringMatch.getMatches;
        int[] highestScoringMatchGamesRounds = new int[3];
        int highestScoringMatchSkillPoints = highestScoringMatch.getSkillPoint;
        int highestScoringMatchRawPoints = highestScoringMatch. getRawPoint;
        int highestScoringMatchBonusPoints = highestScoringMatch.getBonusPoint;
        int highestScoringMatchMatchPoints = highestScoringMatch.getMatcbPoint;

        for(int i=0; i<highestScoringMatchGames.length; i++){
            highestScoringMatchGamesRounds[i] = highestScoringMatchGames[i].getRounds;
        }

        return String.format("""
                             Highest-Scoring Match:\n +
                             Match ID: %d +
                             Games: %s +
                             Rounds: %s +
                             Raw Points: %d +
                             Skill Points: %d +
                             Bonus Points: %d +
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
        Match[] lowestScoringMatchGames = lowestScoringMatch.getMatches;
        int[] lowestScoringMatchGamesRounds = new int[3];
        int lowestScoringMatchSkillPoints = lowestScoringMatch.getSkillPoint;
        int lowestScoringMatchRawPoints = lowestScoringMatch. getRawPoint;
        int lowestScoringMatchBonusPoints = lowestScoringMatch.getBonusPoint;
        int lowestScoringMatchMatchPoints = lowestScoringMatch.getMatcbPoint;
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
                             Highest-Scoring Match:\n +
                             Match ID: %d +
                             Games: %s +
                             Rounds: %s +
                             Raw Points: %d +
                             Skill Points: %d +
                             Bonus Points: %d +
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

    private void findHighestScoringMatch() {
        int highestScoringMatchPoint = 0;

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
        int lowestScoringMatchPoint = 0;

        for(int i =0; i< matches.length; i++) {
            for (int j = 0; j < matches[i].length; j++) {
                if (matches[i][j].getMatchPoint < lowestScoringMatchPoint) {
                    lowestScoringMatchPoint = matches[i][j].getMatchPoint;
                    lowestScoringMatch = matches[i][j];
                }
            }
        }
    }

}
