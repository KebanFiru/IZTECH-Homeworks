public class Query {

    private int[] totalPoints;
    private Match[][] matches

    public Query(int[] totalPointsArray, Match[][] playedMatches){

        this.totalPoints = totalPointsArray;
        this.matches = playedMatches;
    }

    public int findHighestScoringMatch() {
        Match highestScoringMatch = null;
        for(int i =0; i< matches.length; i++){
            for(int j=0; j<matches[i].length; j++){
                if(matches[i][j] > highestScoringMatch){
                    highestScoringMatch = matches[i][j];
                }
            }
        }
        return highestScoringMatch;
    }
}
