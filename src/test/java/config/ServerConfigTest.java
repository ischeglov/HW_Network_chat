package config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServerConfigTest {

    @Test
    @DisplayName("Тест получения хоста")
    void getHost() {
        ServerConfig serverConfig = new ServerConfig();
        String host = "localhost";

        String result = serverConfig.getHost();
        Assertions.assertEquals(result, host);
    }

    @Test
    @DisplayName("Тест получения порта")
    void getPort() {
        ServerConfig serverConfig = new ServerConfig();
        int port = 49081;

        int result = serverConfig.getPort();
        Assertions.assertEquals(result, port);
    }
}