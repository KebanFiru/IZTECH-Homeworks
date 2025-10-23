public class PointsBoard {

    private Gamer[] gamers;
    private Match[][] matches;
    private int[] totalPoints;
    private double[] avaragePointPerMatch;
    private String[] gamerMedal;

    public PointsBoard(Gamer[] gamers, Match[][] matches) {
        this.gamers = (gamers != null) ? gamers : new Gamer[0];
        this.matches = matches;

        int n = this.gamers.length;

        this.totalPoints = new int[n];
        this.avaragePointPerMatch = new double[n];
        this.gamerMedal = new String[n];

        calculateAll();
    }

    public int getTotalPoints(int index) {
        return totalPoints[index];
    }
    public double getAveragePerMatch(int index) {
        return avaragePointPerMatch[index];
    }
    public String getMedal(int index) {
        return gamerMedal[index];
    }

    private String calculateMedal(int total) {
        if (total >= 2000) {
            return "GOLD";
        }
        if (total >= 1200) {
            return "SILVER";
        }
        if (total >= 700){
            return "BRONZE";
        }
        return "NONE";
    }

    public void calculateAll() {
        if(gamers.length != 0) {
            for (int gamer = 0; gamer<gamers.length; gamer++){
                int sum = 0;
                for(int match = 0; match<matches[gamer].length;match++){
                    Match match = matches[gamer];
                    sum = sum + match.getMatchPoint();
                }
                totalPoints[gamer] = sum;
                avaragePointPerMatch[gamer] = (double)sum/15.0;
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

    public void updateMatches(Match[][] newMatches) {
        this.matches = newMatches;
        if (this.totalPoints == null || this.totalPoints.length != gamers.length) {
            this.totalPoints = new int[gamers.length];
            this.avaragePointPerMatch = new double[gamers.length];
            this.gamerMedal = new String[gamers.length];
        }
        calculateAll();
    }
}