package server;

import java.io.*;
import java.net.Socket;

public class ClientThread implements Runnable {

    private final Server server;
    private final Socket socket;
    private final BufferedWriter outWriter;
    private final BufferedReader inReader;

    private static int clientCount = 0;

    public ClientThread(Server server, Socket clientSocket) throws IOException {
        clientCount++;
        this.server = server;
        this.socket = clientSocket;
        this.outWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.inReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        while (true) {
            server.sendMessageAll("Новый участник в чате");
            server.sendMessageAll("В чате " + clientCount + " человек(а)");

            break;
        }

        String clientMessage;
        try {
            clientMessage = inReader.readLine();
            try {
                outWriter.write(clientMessage + "\n");
                outWriter.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                while (true) {
                    if (clientMessage.equals(null)) {
                        this.clientExit();
                        break;
                    }
                    System.out.println(clientMessage);
                    server.sendMessageAll(clientMessage);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessage(String message) {
        try {
            outWriter.write(message + "\n");
            outWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void clientExit() throws IOException {
        if (!socket.isClosed()) {
            socket.close();
            outWriter.close();
            inReader.close();
            server.deleteClient(this);
            clientCount--;
            server.sendMessageAll("Клиентов в чате: " + clientCount);
        }
    }
}
