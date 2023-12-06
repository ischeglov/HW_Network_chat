package client;

import config.ServerConfig;

public class Launcher {

    public static void main(String[] args) {
        ServerConfig serverConfig  = new ServerConfig();
        final String host = serverConfig.getHost();
        final int port = serverConfig.getPort();
        Client client = new Client(host, port);
    }
}
