package rest.controller.classes;
import rest.controller.interfaces.ILogger;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger implements ILogger {
    private String logFilePath;

    public Logger(String logFilePath) {
        this.logFilePath = logFilePath;
    }

    public void logInfo(String message) {
        String formattedMessage = formatMessage("INFO", message);
        System.out.println(formattedMessage);
        writeToFile(formattedMessage);
    }

    public void logError(String message) {
        String formattedMessage = formatMessage("ERROR", message);
        System.err.println(formattedMessage);
        writeToFile(formattedMessage);
    }

    public String formatMessage(String level, String message) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timestamp = LocalDateTime.now().format(formatter);
        return String.format("%s [%s]: %s", timestamp, level, message);
    }

    public void writeToFile(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFilePath, true))) {
            writer.write(message);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Failed to write to log file: " + e.getMessage());
        }
    }
}