import java.io.*;

public class FileIO {
    public static Command[] readCommands(String csvFilePath) {
        Command[] commands = new Command[100]; // Fixed size array for up to 100 commands
        int count = 0;

        File file = new File(csvFilePath);
        if (!file.exists()) {
            System.out.println("File not found: " + csvFilePath);
            return new Command[0];
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(",", -1);
                String action = parts[0].trim().toLowerCase();

                Command cmd = null;

                if ("new".equals(action) && parts.length >= 4) {
                    cmd = new Command("new", parts[1].trim(), parts[2].trim(), parts[3].trim());
                } else if ("display".equals(action) || "history".equals(action)) {
                    if (parts.length >= 2) {
                        cmd = new Command(action, parts[1].trim());
                    } else {
                        cmd = new Command(action, "");
                    }
                } else if ("resolve".equals(action)) {
                    cmd = new Command("resolve", "");
                }

                if (cmd != null && count < commands.length) {
                    commands[count++] = cmd;
                }
            }

            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
        }

        // Trim the array to exact size
        Command[] result = new Command[count];
        for (int i = 0; i < count; i++) {
            result[i] = commands[i];
        }

        return result;
    }
}
