package server;

import helpers.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    public static final Integer LOCALHOST_PORT = 49081;

    private ArrayList<ClientThread> clients = new ArrayList<>();

    public Server() {
        ServerSocket serverSocket = null;
        Socket clientSocket = null;

        try {
            serverSocket = new ServerSocket(LOCALHOST_PORT);
            System.out.println("Чат запущен");
            Logger.instance.setMessage("Добро пожаловать в чат!");
            while (true) {
                clientSocket = serverSocket.accept();
                ClientThread clientThread = new ClientThread(this, clientSocket);
                clients.add(clientThread);
                new Thread(clientThread).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                clientSocket.close();
                Logger.instance.setMessage("Сервер остановлен");
                serverSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void sendMessageAll(String message) {
        for (ClientThread client:
             clients) {
            client.sendMessage(message);
        }
    }

    public void deleteClient(ClientThread clientThread) {
        clients.remove(clientThread);
    }
}
