package client;

import helpers.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    private final String host;
    private final int port;

    private Socket socket;
    private BufferedReader inReader;
    private PrintWriter outWriter;
    private BufferedReader inReaderConsole;
    private String clientName;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
        try {
            this.socket = new Socket(host, port);
            inReaderConsole = new BufferedReader(new InputStreamReader(System.in));
            outWriter = new PrintWriter(socket.getOutputStream(), true);
            inReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.addName();
            new Thread(new MessageFromServer()).start();
            new Thread(new MessageToServer()).start();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            Client.this.clientExit();
        }
    }

    private void addName() throws IOException {
        Logger.instance.setMessage("Введите ваше имя: ");
        clientName = inReaderConsole.readLine();
        outWriter.write("Добро пожаловать " + clientName + "!" + "\n");
        outWriter.flush();
    }

    public void clientExit() {
        try {
            if (!socket.isClosed()) {
                socket.close();
                inReader.close();
                outWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class MessageFromServer implements Runnable {

        @Override
        public void run() {
            String message;
            try {
                while (true) {
                    message = inReader.readLine();
                    if (message.equals("exit")) {
                        Client.this.clientExit();
                        break;
                    }
                    Logger.instance.setMessage(message);
                }
            } catch (IOException e) {
                Client.this.clientExit();
            }
        }
    }

    public class MessageToServer implements Runnable {

        @Override
        public void run() {
            while (true) {
                String clientMessage;
                try {
                    clientMessage = inReaderConsole.readLine();
                    if (clientMessage.equals("exit")) {
                        outWriter.write("exit" + "\n");
                        Client.this.clientExit();
                        break;
                    } else {
                        outWriter.write((clientName + ": " + clientMessage + "\n"));
                    }
                    outWriter.flush();
                } catch (Exception e) {
                    Client.this.clientExit();
                }
            }
        }
    }
}
