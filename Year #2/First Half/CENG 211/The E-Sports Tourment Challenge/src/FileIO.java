import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Utility class for reading CSV files and loading data
 * Handles file I/O operations for games and gamers data
 */
public class FileIO {

    /**
     * Reads games data from a CSV file
     * Expected format: ID, GameName, BasePointPerRound
     * 
     * @param filename Path to the games CSV file
     * @return Array of Game objects
     * @throws IllegalArgumentException if filename is null or empty
     */
    public static Game[] readGames(String filename) {
        if (filename == null || filename.trim().isEmpty()) {
            throw new IllegalArgumentException("Filename cannot be null or empty");
        }
        
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
                String[] parts = line.split(","); // Split with ","
                if (parts.length >= 3) { // Ensure that it reads the valid input
                    int id = Integer.parseInt(parts[0].trim()); // Parse the string to integer
                    String gameName = parts[1].trim(); // Get the string
                    int basePointPerRound = Integer.parseInt(parts[2].trim()); // Parse the string to integer
                    
                    games[index] = new Game(id, gameName, basePointPerRound); // Create a game object for each valid line
                    index++;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading games file: " + e.getMessage());
        }

        return games;
    }

    /**
     * Reads gamers data from a CSV file
     * Expected format: ID, Nickname, Name, Phone, ExperienceYears
     * 
     * @param filename Path to the gamers CSV file
     * @return Array of Gamer objects
     * @throws IllegalArgumentException if filename is null or empty
     */
    public static Gamer[] readGamers(String filename) {
        if (filename == null || filename.trim().isEmpty()) {
            throw new IllegalArgumentException("Filename cannot be null or empty");
        }
        
        // First pass: count the number of gamers
        int count = 0;
        try (BufferedReader inputStream = new BufferedReader(new FileReader(filename))) {
            inputStream.readLine(); // Skip header
            String line;
            while ((line = inputStream.readLine()) != null) {
                count++;
            }
        } catch (IOException e) {
            System.err.println("Error reading gamers file: " + e.getMessage());
            return new Gamer[0];
        }

        // Second pass: read the data
        Gamer[] gamers = new Gamer[count];
        int index = 0;
        
        try (BufferedReader inputStream = new BufferedReader(new FileReader(filename))) {
            String line = inputStream.readLine(); // Skip header
            
            while ((line = inputStream.readLine()) != null) {
                String[] parts = line.split(","); // Split with ","
                if (parts.length >= 5) { // Ensure that it reads the valid input
                    int id = Integer.parseInt(parts[0].trim()); // Parse the string to integer
                    String nickname = parts[1].trim(); // Get the string
                    String name = parts[2].trim(); // Get the string
                    String phone = parts[3].trim(); // Get the string
                    int experienceYears = Integer.parseInt(parts[4].trim()); // Parse the string to integer
                    
                    gamers[index] = new Gamer(id, nickname, name, phone, experienceYears); // Create a gamer object for each valid line
                    index++;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading gamers file: " + e.getMessage());
        }

        return gamers;
    }
}
