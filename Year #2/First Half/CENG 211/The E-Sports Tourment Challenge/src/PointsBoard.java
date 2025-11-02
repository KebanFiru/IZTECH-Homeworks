public class PointsBoard {

    private final Gamer[] gamers;
    private Match[][] matches;
    private final int[] totalPoints;
    private final double[] averagePointPerMatch;
    private final String[] gamerMedal;

    public PointsBoard(Gamer[] gamers, Match[][] playedMatches) {
        if(gamers == null){
            throw new IllegalArgumentException("Gamers array cannot be null");
        }
        if(gamers.length == 0){
            throw new IllegalArgumentException("Gamers array cannot be empty");
        }
        
        this.gamers = new Gamer[gamers.length];
        for(int i=0; i<gamers.length; i++){
            if(gamers[i] == null) {
                throw new IllegalArgumentException("Gamer at index " + i + " cannot be null");
            }
            Gamer gamer = new Gamer(gamers[i]);
            this.gamers[i] = gamer;
        }
        
        if(playedMatches == null){
            throw new IllegalArgumentException("Played matches array cannot be null");
        }
        if(playedMatches.length == 0){
            throw new IllegalArgumentException("Played matches array cannot be empty");
        }
        if(playedMatches.length != gamers.length){
            throw new IllegalArgumentException("Number of match rows must equal number of gamers");
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

        this.totalPoints = new int[this.gamers.length];
        this.averagePointPerMatch = new double[this.gamers.length];
        this.gamerMedal = new String[this.gamers.length];

        calculateAll();
    }

    public PointsBoard(PointsBoard another){

        if(another == null){
            throw new IllegalArgumentException("Cannot copy from null PointsBoard object");
        }
        if(another.gamers == null){
            throw new IllegalArgumentException("Gamers in source PointsBoard cannot be null");
        }
        
        this.gamers = new Gamer[another.gamers.length];
        for(int i=0; i<another.gamers.length; i++){
            if(another.gamers[i] == null){
                throw new IllegalArgumentException("Gamer at index " + i + " cannot be null");
            }
            Gamer gamer = new Gamer(another.gamers[i]);
            this.gamers[i] = gamer;
        }

        if(another.matches == null){
            throw new IllegalArgumentException("Matches in source PointsBoard cannot be null");
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

        this.totalPoints = new int[this.gamers.length];
        this.averagePointPerMatch = new double[this.gamers.length];
        this.gamerMedal = new String[this.gamers.length];

        calculateAll();
    }

    public int getTotalPoints(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Index cannot be negative");
        }
        if (index >= totalPoints.length) {
            throw new IllegalArgumentException("Index out of bounds");
        }
        return totalPoints[index];
    }
    
    public double getAveragePerMatch(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Index cannot be negative");
        }
        if (index >= averagePointPerMatch.length) {
            throw new IllegalArgumentException("Index out of bounds");
        }
        return averagePointPerMatch[index];
    }
    
    public String getMedal(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Index cannot be negative");
        }
        if (index >= gamerMedal.length) {
            throw new IllegalArgumentException("Index out of bounds");
        }
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