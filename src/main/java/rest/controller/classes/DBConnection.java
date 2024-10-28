package rest.controller.classes;
import java.sql.Connection;
import java.sql.SQLException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DBConnection {
    private Connection connection;
    private Logger logger; 
    private Map<String, String> credentials = new HashMap<>();

    public DBConnection() {
        this.logger = new Logger(".\\src\\Classes\\log.txt");
        loadCredentials(".\\src\\Classes\\users.txt");
    }

    private void loadCredentials(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    credentials.put(parts[0], parts[1]);
                }
            }
            logger.logInfo("Credentials loaded successfully.");
        } catch (IOException e) {
            logger.logError("Failed to load credentials: " + e.getMessage());
        }
    }

    public Boolean connect(String connectionString) {
        if (!credentials.containsKey(connectionString.split(":")[0])
                || !credentials.get(connectionString.split(":")[0]).equals(connectionString.split(":")[1])) {
            logger.logError("Invalid username or password.");
            return false;
        }
        logger.logInfo("Successfully connected to the database: " + connectionString.split(":")[0] );
        return true;
    }

    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                logger.logInfo("Successfully disconnected from the database.");
            } catch (SQLException e) {
                logger.logError("Failed to disconnect from the database: " + e.getMessage());
            }
        }
    }
}