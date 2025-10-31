import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileIO {
        public static Game[] readGames(String filename) {
        // First pass: count the number of games
        int count = 0;
        try (BufferedReader inputStream = new BufferedReader(new FileReader(filename))) {
            inputStream.readLine(); // Skip header
            String line;
            while ((line = inputStream.readLine()) != null) {
                count++;
            }
        } catch (IOException e) {
            System.err.println("Error reading games file: " + e.getMessage());
            return new Game[0];
        }

        // Second pass: read the data
        Game[] games = new Game[count];
        int index = 0;
        
        try (BufferedReader inputStream = new BufferedReader(new FileReader(filename))) {
            String line = inputStream.readLine(); // Skip header
            
            while ((line = inputStream.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    int id = Integer.parseInt(parts[0].trim());
                    String gameName = parts[1].trim();
                    int basePointPerRound = Integer.parseInt(parts[2].trim());
                    
                    games[index] = new Game(id, gameName, basePointPerRound);
                    index++;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading games file: " + e.getMessage());
        }

        return games;
}
}
