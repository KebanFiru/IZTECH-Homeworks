public class PointsBoard {

    private final Gamer[] gamers;
    private Match[][] matches;
    private final int[] totalPoints;
    private final double[] averagePointPerMatch;
    private final String[] gamerMedal;

    public PointsBoard(Gamer[] players, Match[][] playedMatches) {
        if(players == null){
            throw new IllegalArgumentException("players must not be null");
        }
        else{
            this.gamers = new Gamer[players.length];
            for(int i=0; i<players.length; i++){
                if(players[i] == null) {
                    throw new IllegalArgumentException("player must not be null");
                }
                Gamer player = new Gamer(players[i]);
                this.gamers[i] = player;
            }
        }
        if(playedMatches == null){
            throw new IllegalArgumentException("played matches must not be null");
        }
        else{
            this.matches = new Match[playedMatches.length][playedMatches[0].length];
            for(int i=0; i<playedMatches.length; i++){
                for(int j=0; j<playedMatches[i].length; j++){
                    if(playedMatches[i][j] == null){
                        throw new IllegalArgumentException("match must not be null");
                    }
                    Match match = new Match(playedMatches[i][j]);
                    this.matches[i][j] = match;
                }
            }
        }

        this.totalPoints = new int[this.gamers.length];
        this.averagePointPerMatch = new double[this.gamers.length];
        this.gamerMedal = new String[this.gamers.length];

        calculateAll();
    }

    public PointsBoard(PointsBoard another){

        if(another.gamers == null){
            throw new IllegalArgumentException("gamers must not be null");
        }
        else{
            this.gamers = new Gamer[another.gamers.length];
            for(int i=0; i<another.gamers.length; i++){
                Gamer player = new Gamer(another.gamers[i]);
                this.gamers[i] = player;
            }
        }

        if(another.matches == null){
            throw new IllegalArgumentException("matches must not be null");
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

        this.totalPoints = new int[this.gamers.length];
        this.averagePointPerMatch = new double[this.gamers.length];
        this.gamerMedal = new String[this.gamers.length];

        calculateAll();
    }

    public int getTotalPoints(int index) {
        return totalPoints[index];
    }
    public double getAveragePerMatch(int index) {
        return averagePointPerMatch[index];
    }
    public String getMedal(int index) {
        return gamerMedal[index];
    }

    private String calculateMedal(int total) {
        if (total >= 4400) {
            return "GOLD";
        }
        if (total >= 3800) {
            return "SILVER";
        }
        if (total >= 3500){
            return "BRONZE";
        }
        return "NONE";
    }

    public void calculateAll() {
        if(gamers.length != 0) {
            for (int gamer = 0; gamer<gamers.length; gamer++){
                int sum = 0;
                for(int match = 0; match<matches[gamer].length;match++){
                    Match currentMatch = matches[gamer][match];
                    sum = sum + currentMatch.getMatchPoints();
                }
                totalPoints[gamer] = sum;
                averagePointPerMatch[gamer] = (double)sum / matches[gamer].length;
                gamerMedal[gamer] = calculateMedal(sum);
            }
        }
    }

    public int calculateTournamentPoints() {
        int sum = 0;
        if(gamers.length != 0) {

            for (int t : totalPoints) {
                sum += t;
            }
        }
        return sum;
    }

}