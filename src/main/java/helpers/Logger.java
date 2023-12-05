package helpers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

    File file = new File("/Users/imshcheglov/Desktop/JD-51 HW/7. Многопоточное и функциональное программирование/6. Курсовой проект \"Сетевой чат\"/HW_Network_chat", "file.log");

    public static final Logger instance = new Logger();

    private Logger() {}

    public void setMessage(String message) {
        System.out.println("[ " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")) + " ] " + message);

        try (FileWriter writer = new FileWriter("file.log", true)) {
            writer.write("[ " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")) + " ] " + message + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
