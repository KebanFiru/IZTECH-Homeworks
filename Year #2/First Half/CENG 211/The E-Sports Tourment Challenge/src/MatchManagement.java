import java.util.Random;

public class MatchManagement {
    private Match[][] matches;
    private Gamer[] gamers;
    private Game[] games;

    public MatchManagement(Gamer[] gamers, Game[] games) {
        this.gamers = gamers;
        this.games = games;
        this.matches = new Match[gamers.length][15];
    }

    private static final Random RANDOM = new Random();

    public void generateMatches() {
        for (int i = 0; i < gamers.length; i++) {
            for (int j = 0; j < 15; j++) {
                matches[i][j] = createMatchFor(gamers[i], i, j);
            }
        }
    }

    private Match createMatchFor(Gamer gamer, int gamerIndex, int matchIndex){
        Game[] selectedGames = selectRandomGames(games);
        int matchID = generateMatchID(gamerIndex, matchIndex);
        return new Match(matchID, gamer, selectedGames);
    }

    private Game[] selectRandomGames(Game[] games) {
        Game[] chosen = new Game[3];
        boolean[] used = new boolean[games.length];

        for (int i = 0; i < 3; i++) {
            int index;
            do {
                index = RANDOM.nextInt(games.length);
            } while (used[index]);
            used[index] = true;
            chosen[i] = games[index];
        }
        return chosen;
    }

    private int generateMatchID(int gamerIndex, int matchIndex) {
        return gamerIndex * 1000 + matchIndex + 1;
    }

    public Match[][] getAllMatches() {
        return matches;
    }

    public Match[] getMatchesOfGamer(int gamerIndex) {
        return matches[gamerIndex];
    }
}




