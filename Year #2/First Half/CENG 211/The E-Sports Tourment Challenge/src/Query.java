public class Query {

    private int[] totalPoints;
    private Match[][] matches
    private Match highestScoringMatch;

    public Query(int[] totalPointsArray, Match[][] playedMatches){

        this.totalPoints = totalPointsArray;
        this.matches = playedMatches;

        findHighestScoringMatch();
    }

    public Match getHighestScoringMatch(){

        return highestScoringMatch;
    }

    public void findHighestScoringMatch() {
        for(int i =0; i< matches.length; i++){
            for(int j=0; j<matches[i].length; j++){
                if(matches[i][j].getMatchPoints > highestScoringMatch){
                    highestScoringMatch = matches[i][j];
                }
            }
        }
    }
}
