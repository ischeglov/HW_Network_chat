package config;

import java.io.*;

public class ServerConfig {

    private static final File SETTING = new File("settings.txt");
    private final String[] settings;

    public ServerConfig() {
        this.settings = fileRead();
    }

    public String[] fileRead() {
        String result = "";
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(SETTING))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result = line;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result.split(":");
    }

    public String getHost() {
        return settings[0];
    }

    public int getPort() {
        return Integer.parseInt(settings[1]);
    }
}
