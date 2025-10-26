public class Query {

    private int[] totalPoints;
    private Match[][] matches;

    private int highestScoringMatchID;

    public Query(int[] totalPointsArray, Match[][] playedMatches){

        this.totalPoints = totalPointsArray;
        this.matches = playedMatches;

        this.highestScoringMatchID = 0;

        findHighestScoringMatch();
    }

    public int getHighestScoringMatch(){

        return highestScoringMatchID;
    }

    public void findHighestScoringMatch() {
        int highestScoringMatchPoint = matches[0][0].getMatchPoint;

        for(int i =0; i< matches.length; i++) {
            for (int j = 0; j < matches[i].length; j++) {
                if (matches[i][j].getMatchPoint > highestScoringMatchPoint) {
                    highestScoringMatchPoint = matches[i][j].getMatchPoint;
                    highestScoringMatchID = 15*(i-1)+j;
                }
            }
        }
    }
}
